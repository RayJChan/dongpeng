package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface RegionDao extends BaseCrudDao<Region> {
    /**
     * 根据名称查找唯一
     * @param name 名称
     * @return
     */
    public Region getByName(@Param("name") String name);

    /**
     * 根据名称和上级名称查找唯一
     * @param name 名称
     * @param parentName 上级名称
     * @return
     */
    public Region getByNameAndParentName(@Param("name") String name,@Param("parentName") String parentName);

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
}
