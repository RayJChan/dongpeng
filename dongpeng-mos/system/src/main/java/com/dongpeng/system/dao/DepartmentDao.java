package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Department;
import com.dongpeng.entity.system.DepartmentWarehouse;
import com.dongpeng.entity.system.UserDepartment;
import com.dongpeng.entity.system.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface DepartmentDao extends BaseCrudDao<Department> {

    /**
     * 根据上级id获得部门
     * @param upId 上级id
     * @return
     */
    public List<Department> getByUp(Long upId);

    /**
     * 根据根据部门编码获得部门
     * @param departmentCode 部门编码
     * @return
     */
    public Department getByDepartmentCode(String departmentCode);

    /**
     * 删除用户部门
     * @param userDepartment 封装用户id 和其他删除条件参数
     * @return
     */
    public int deleteUserDepartment(UserDepartment userDepartment);

    /**
     * 插入用户部门
     * @param userDepartmentList 用户部门列表
     * @return
     */
    public int insertUserDepartment(@Param(value = "userDepartmentList")List<UserDepartment> userDepartmentList);

    /**
     * 根据用户id查找部门信息
     * @param userDepartment 封装用户id
     * @return
     */
    public List<Department> findListByUserId(UserDepartment userDepartment);

    /**
     * 根据用户id查找该用户未有的部门信息
     * @param userDepartment 封装用户id
     * @return
     */
    public List<Department> findListNotInUserId(UserDepartment userDepartment);

    /**
     * 根据部门编号长度查询最近一个添加的部门编号
     * @return
     */
    String getLastDeparment(int num);

    /**
     * 根据票号字母或者部门名称查询是否存在部门数据
     * @param department
     * @return
     */
    public int getCountBy(Department department);

    List<Warehouse> getWarehouseById(@Param(value = "departmentId") Long departmentId);

    /**
     *
     * @param departmentWarehouse
     * @return
     */
    int getCountByDoubleId(DepartmentWarehouse departmentWarehouse);

    /**
     * 批量添加部门与仓库关联信息
     * @param departmentWarehouse
     * @return
     */
    int insertDepartmentWarehouse(DepartmentWarehouse departmentWarehouse);


    /**
     * 批量删除部门与仓库关联信息
     * @param id
     */
    void deleteDepartmentWarehouse(@Param(value = "id") String id);
}
