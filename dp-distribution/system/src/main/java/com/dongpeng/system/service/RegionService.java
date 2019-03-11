package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.mapper.JsonMapper;
import com.dongpeng.common.utils.PinyinUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.entity.system.Region;
import com.dongpeng.system.dao.RegionDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RegionService extends BaseCrudService<RegionDao,Region> {
    @Override
    public String createDataScopeSql(Region entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(Region region) throws OptimisticLockException {
        //如果父id不为空，则获取其名称、parentids
        if (null != region.getParentId() && 0 != region.getParentId()) {
            Region parent = dao.get(region.getParentId());
            region.setParentName(parent.getName());
            region.setParentIds(
                    StringUtils.isNotBlank(parent.getParentIds()) ?
                            parent.getParentIds() + "," + parent.getId()
                            : parent.getId().toString());
        }
        int rs = super.save(region);
        if(0<rs && !region.getIsNewRecord()){
            //更新其子节点parentids
            dao.updateParentids(region);
        }
        return rs;
    }

    /**
     * 根据名称查找唯一
     * @param region 封装 省、市、区 数据
     * @return
     */
    public Region getByName(Region region){
        return dao.getByName(region.getName());
    }

    /**
     * 根据名称查找唯一
     * @param name 地区名称
     * @return
     */
    public Region getByName(String name){
        return dao.getByName(name);
    }

    /**
     * 查找城市列表
     * @param region
     * @return
     */
    public List<Region> findCityList(Region region){
        region.setLevel(2);//只选择城市
        List<Region> regions=findList(region);
        return regions;
    }

    /**
     * 查找服务范围
     * @return
     */
    public List<Region> findServiceArea(){
        List<Region> regions=dao.findAllProvinceAndCity(new Region());

        Map<Long,Region> countrys=Maps.newHashMap();
        Map<Long,Region> provinces=Maps.newHashMap();

        for (Region re:regions){
            re.setLabel(re.getName());
            re.setChildren(null);//避免缓存导致重复子集

            switch (re.getLevel()){
                case 0:
                    countrys.put(re.getId(), re);
                    break;
                case 1:
                    provinces.put(re.getId(), re);
                    if(countrys.containsKey(re.getParentId())){
                        if(StringPlusUtils.isBlankList(countrys.get(re.getParentId()).getChildren())){
                            countrys.get(re.getParentId()).setChildren(Lists.newArrayList());
                        }
                        countrys.get(re.getParentId()).getChildren().add(re);
                    }
                    break;
                case 2:
                    if(provinces.containsKey(re.getParentId())){
                        if(StringPlusUtils.isBlankList(provinces.get(re.getParentId()).getChildren())){
                            provinces.get(re.getParentId()).setChildren(Lists.newArrayList());
                        }
                        provinces.get(re.getParentId()).getChildren().add(re);
                    }
                    break;
            }
        }
        return Lists.newArrayList(countrys.values());

        /*for (Region re:regions) {
            if(1==re.getLevel()){
                re.setLabel(re.getName());
                re.setChildren(null);//避免缓存导致重复子集
                serviceAreas.add(re);
            }else if(2==re.getLevel()){
                for (Region province:serviceAreas) {
                    if(StringPlusUtils.isNotBlank(province.getProvince()) && province.getProvince().equals(re.getProvince())){
                        if(StringPlusUtils.isBlankList(province.getChildren())){
                            province.setChildren(Lists.newArrayList());
                        }
                        re.setLabel(re.getCity());
                        province.getChildren().add(re);
                        break;
                    }
                }
            }
        }*/
        //logger.warn(JsonMapper.toJsonString(serviceAreas));
        //return serviceAreas;
    }

    /**
     * 从excel导入插入数据
     * @param region
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public int saveForExcel(Region region) throws OptimisticLockException {
        if(StringUtils.isNotBlank(region.getParentName())){
            Region parent=dao.getByName(region.getParentName());
            if(null!=parent){
                region.setParentId(parent.getId());
            }else{
                return 0;//该条数据插入失败
            }
        }
        return save(region);
    }

    /**
     * 逻辑删除/恢复，更新delete_flag字段为1/0,在表包含字段del_flag时，可以调用此方法，将数据隐藏/显示）
     *
     * @param region
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteToggle(Region region){
        Region regionTemp=get(region.getId());//根据id查找region，加载出parentId
        if(false==region.getDeleteFlag() && null!=regionTemp.getParentId()){//如果启用，且父节点不为空，则要判断父节点是否被禁用，父节点被禁用时不能执行启用操作
            Region parent=get(regionTemp.getParentId());
            if(true==parent.getDeleteFlag()){
                return 0;
            }
        }
        return dao.deleteToggle(region);
    };

    /**
     * 根据名称和上级名称查找唯一
     * @param name 名称
     * @param parentName 上级名称
     * @return
     */
    public Region getByNameAndParentName(String name,String parentName){
        return dao.getByNameAndParentName(name, parentName);
    }
}
