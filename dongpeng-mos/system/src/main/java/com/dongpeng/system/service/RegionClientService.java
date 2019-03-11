package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.entity.system.ClientRecord;
import com.dongpeng.entity.system.RegionClient;
import com.dongpeng.system.dao.RegionClientDao;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wuhongda
 */
@Service
public class RegionClientService extends BaseCrudService<RegionClientDao, RegionClient> {
    @Override
    public String createDataScopeSql(RegionClient entity) {
        return null;
    }

    /**
     * 批量更新或插入区域与客户关联表
     *
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateList(List<RegionClient> list) {
        //遍历list
        for (RegionClient regionClient : list) {
            // 插入
            if (null == regionClient.getId()) {
               int count =  dao.getCountByClientAndDistrict(regionClient);
               // 防止绑定重复
               if(count == 0){
                   regionClient.setOrderType(1);
                   try {
                       save(regionClient);
                   } catch (OptimisticLockException e) {
                       e.printStackTrace();
                       logger.info("列ID:"+regionClient.getId()+"添加失败"+ e.getMessage());
                       return ResponseResult.failByParam("添加失败");
                   }
               }

            } else {     //更新
                RegionClient temp = (RegionClient) dao.get(regionClient.getId());
                if (null == temp) {
                    return ResponseResult.failByParam("id:" + regionClient.getId() + "无效,更新失败");
                }
                regionClient.preUpdate();
                try {
                    dao.updateByPrimaryKeySelective(regionClient);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("列ID:"+regionClient.getId()+"更新失败"+ e.getMessage());
                    return ResponseResult.failByParam("更新失败");
                }
            }
        }

        // 更新
        return ResponseResult.ok();
    }

    /**
     * 根据区ID和客户ID查询客户信息
     * @param regionClient
     * @return
     */
    public List<ClientRecord> findByRegion(RegionClient regionClient) {
        return dao.findByRegion(regionClient);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(RegionClient regionClient) throws OptimisticLockException {
        int rs=super.save(regionClient);

        //更新缓存
        Map<String, RegionClient> dictMap = (Map<String, RegionClient>) J2CacheUtils.get(Global.DICT_REGION, regionClient.getId().toString());
        if (dictMap != null){
            dictMap.put(regionClient.getId().toString(), regionClient);
        }else{
            dictMap=Maps.newHashMap();
            dictMap.put(regionClient.getId().toString(), regionClient );
            J2CacheUtils.put(Global.DICT_REGION, regionClient.getId().toString(), dictMap);
        }
        return rs;
    }

    /**
     * 查询导出数据
     * @param regionClient
     * @return
     */
    public List<RegionClient> findExportFile(RegionClient regionClient) {
        List<RegionClient> list = new ArrayList<RegionClient>();
        List<RegionClient> regionClientList = dao.findExportFile();
        for (RegionClient regionClientExPort: regionClientList) {
            // 根据区的父ID查出省的信息
            if(regionClientExPort.getDistrictParentId() != null){
                String Province = dao.getProvinceByregionClient(regionClientExPort.getDistrictParentId());
                regionClientExPort.setProvince(Province);
                // 后期可能会有多种订单类型
                switch (regionClientExPort.getOrderType()) {
                    case 1:
                        regionClientExPort.setOrderTypeExport("现货派单");
                        break;
                }
            }
            list.add(regionClientExPort);
        }
        return list;
    }
}
