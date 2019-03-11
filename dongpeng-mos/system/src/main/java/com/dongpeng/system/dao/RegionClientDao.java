package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.ClientRecord;
import com.dongpeng.entity.system.RegionClient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface RegionClientDao extends BaseCrudDao<RegionClient> {
    /**
     * 更新、设置无效单条数据
     * @param regionClient
     * @return
     */
    public int updateByPrimaryKeySelective(RegionClient regionClient);

    /**
     * 插入一条数据
     * @param newRegionClient
     * @return
     */
    public int insertSelective(RegionClient newRegionClient);

    public int getCountByClientAndDistrict(RegionClient regionClient );

    /**
     * 根据区ID和客户ID查询客户信息
     * @param regionClient
     * @return
     */
    List<ClientRecord> findByRegion(RegionClient regionClient);

    /**
     * 关联表信息导出
     * @return
     */
    List<RegionClient> findExportFile();

    /**
     * 根据区的父级ID查出父级名称
     * @param districtParentId
     * @return
     */
    String getProvinceByregionClient(@Param("districtParentId") Long districtParentId);
}
