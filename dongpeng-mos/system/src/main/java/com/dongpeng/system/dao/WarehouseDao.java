package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.UserWarehouse;
import com.dongpeng.entity.system.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface WarehouseDao extends BaseCrudDao<Warehouse> {

    /**
     * 根据上级id获得仓库
     * @param upId 上级id
     * @return
     */
    public List<Warehouse> getByUp(Long upId);

    /**
     * 根据根据仓库编码获得仓库
     * @param warehouseCode 仓库编码
     * @return
     */
    public Warehouse getByWarehouseCode(String warehouseCode);

    /**
     * 删除用户仓库
     * @param userWarehouse 封装用户id 和其他删除条件参数
     * @return
     */
    public int deleteUserWarehouse(UserWarehouse userWarehouse);

    /**
     * 插入用户仓库
     * @param userWarehouseList 用户仓库列表
     * @return
     */
    public int insertUserWarehouse(@Param(value = "userWarehouseList")List<UserWarehouse> userWarehouseList);

    /**
     * 根据用户id查找仓库信息
     * @param userWarehouse 封装用户id
     * @return
     */
    public List<Warehouse> findListByUserId(UserWarehouse userWarehouse);

    /**
     * 根据用户id查找该用户未有的仓库信息
     * @param userWarehouse 封装用户id
     * @return
     */
    public List<Warehouse> findListNotInUserId(UserWarehouse userWarehouse);

    /**
     * 模糊查询对应部门未添加的仓库
     * @param departmentId
     * @param warehouseName
     * @return
     */
    List<Warehouse> findIsNotAll(@Param(value = "departmentId") String departmentId,@Param(value = "warehouseName") String warehouseName);
}
