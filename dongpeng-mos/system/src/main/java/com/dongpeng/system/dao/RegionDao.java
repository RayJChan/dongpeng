package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Region;

import java.util.List;

@MyBatisDao
public interface RegionDao extends BaseCrudDao<Region> {
    /**
     * 根据名称查找唯一
     * @param region 封装 省、市、区 数据
     * @return
     */
    public Region getByName(Region region);

    /**
     * 查询全部省和市
     * @return
     */
    public List<Region> findAllProvinceAndCity(Region region);

    /**
     * 根据上级id查找
     * @param upId 上级id
     * @return
     */
    public List<Region> getByUp(Long upId);

    /**
     * 更新子节点parentids
     * @param region 封装id，parentIds
     * @return
     */
    public int updateParentids(Region region);

    /**
     * 查询所有县/区
     * @param region
     * @return
     */
    List<Region> findDistrictList(Region region);

    /**
     * 根据名称查找ID
     * @param region
     * @return
     */
    String getIdByName(String region);

    /**
     * 根据ID查找父级名称
     * @param parentId
     * @return
     */
    Region getParentNameById(Long parentId);

    /**
     * 条件更新省市区
     * @param region
     * @return
     */
    int updateByPrimaryKeySelective(Region region);

    /**
     * 根据省名称查询
     * @param province
     * @return
     */
    //Region getByProvince(Long province);
}
