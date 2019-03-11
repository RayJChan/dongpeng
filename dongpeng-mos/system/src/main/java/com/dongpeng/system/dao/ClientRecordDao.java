package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.*;

import java.util.List;
@MyBatisDao
public interface ClientRecordDao extends BaseCrudDao<ClientRecord> {

    List<ClientRecord> findByRegion(RegionClient regionClient);

    /**
     * 根据客编查询
     * @param clientCode
     * @return
     */
    ClientRecord getByClientCode(String clientCode);

    /**
     * 根据部门ID名称查询部门
     * @param id
     * @return
     */
    Department getByDepartment(Long id);

    /**
     * 根据公司id查询公司
     * @param id
     * @return
     */
    Company getCompany(Long id);

    /**
     * 查询字典项
     * @param id
     * @return
     */
    Dictionary getByDictionary(Long id);

    /**
     * 根据客编查询无效数据
     * @param clientCode
     * @return
     */
    ClientRecord getByClientIsDelete(String clientCode);

    /**
     * 根据id查询客户
     * @param id
     * @return
     */
    ClientRecord getById(Long id);

    /**
     *财务数据维护
     * @param clientRecord
     * @return
     */
    ResponseResult maintain(ClientRecord clientRecord);

    /**
     * 根据部门名称查询部门
     * @param departmentName
     * @return
     */
    Department getByDepartmentName(String departmentName);

    /**
     * 根据公司名称查询公司
     * @param companyName
     * @return
     */
    Company getCompanyName(String companyName);

    /**
     * 查询字典项客户类型
     * @param name
     * @return
     */
    Dictionary getByDictionaryName(String name);

    /**
     * 根据id查询省市区
     * @param id
     * @return
     */
    Region getByProvinceId(Long id);

    /**
     * 根据名称查询省市区
     * @param name
     * @return
     */
    Region getByProvinceName(String name);
}
