package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.MathUtils;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.dao.DepartmentDao;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.BaseNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wuhongda
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class DepartmentService extends BaseCrudService<DepartmentDao, Department> {
    @Autowired
    private CompanyService companyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(Department department) throws OptimisticLockException {
        if (department.getId() == null) {
            //如果父id不为空，则获取其parentids
            if (null != department.getParentId() && 0 != department.getParentId()) {
                //  添加部门的时候判断部门名称、票号字母的唯一性，如有提示“系统已经有部门\票号字母”
                int count = dao.getCountBy(department);
                if (count != 0) {
                    return 2;
                }
                Department parentDepartment = dao.get(department.getParentId());
                department.setParentName(parentDepartment.getDepartmentName());
                department.setParentIds(
                        StringUtils.isNotBlank(parentDepartment.getParentIds()) ?
                                parentDepartment.getParentIds() + "," + parentDepartment.getId()
                                : parentDepartment.getId().toString());


                // 如果部门编号为空，则为新创建用户,自动生成部门编码
                if (null == department.getDepartmentCode() || "".equals(department.getDepartmentCode())) {
                    String departmentCode = null;
                    String parentDepartmentCode = parentDepartment.getDepartmentCode();
                    int parentDepartmentCodeLengh = parentDepartmentCode.length();
                    //  目前设置六层部门关系
                    switch (parentDepartmentCodeLengh) {
                        case 3:
                            departmentCode = dao.getLastDeparment(6);
                            // 自动生成部门编号
                            createCode(department, parentDepartmentCode, departmentCode);
                            break;
                        case 6:
                            departmentCode = dao.getLastDeparment(9);
                            createCode(department, parentDepartmentCode, departmentCode);
                            break;
                        case 9:
                            departmentCode = dao.getLastDeparment(12);
                            createCode(department, parentDepartmentCode, departmentCode);
                            break;
                        case 12:
                            departmentCode = dao.getLastDeparment(15);
                            createCode(department, parentDepartmentCode, departmentCode);
                            break;
                        case 15:
                            departmentCode = dao.getLastDeparment(18);
                            createCode(department, parentDepartmentCode, departmentCode);
                            break;
                    }
                }
            }

        }
        //如果公司id不为空，公司名称为空，自动查找补全公司名称
        if (null != department.getCompanyId() && StringUtils.isBlank(department.getCompanyName())) {
            Company company = companyService.get(department.getCompanyId());
            if (null != company) {
                department.setCompanyName(company.getCompanyName());
            }
        }
        return super.save(department);
    }

    /**
     * 自动生成部门编码
     *
     * @param department
     * @param parentDepartmentCode
     * @param departmentCode
     */
    public void createCode(Department department, String parentDepartmentCode, String departmentCode) {
        if (null != departmentCode) {
            int newCode = Integer.parseInt(departmentCode) + 1;
            department.setDepartmentCode("00" + String.valueOf(newCode));
        } else {
            department.setDepartmentCode(parentDepartmentCode + "001");
        }

    }

    /**
     * 根据上级id获得部门
     *
     * @param upId 上级id
     * @return
     */
    public List<Department> getByUp(Long upId) {
        return dao.getByUp(upId);
    }

    /**
     * 根据根据部门编码获得部门
     *
     * @param departmentCode 部门编码
     * @return
     */
    public Department getByDepartmentCode(String departmentCode) {
        return dao.getByDepartmentCode(departmentCode);
    }

    /**
     * 删除用户部门
     *
     * @param userDepartment 封装用户id 和其他删除条件参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserDepartment(UserDepartment userDepartment) {
        int rs = dao.deleteUserDepartment(userDepartment);
        return rs;
    }

    /**
     * 插入用户部门
     *
     * @param userId        用户id
     * @param departmentIds 部门id集合，多个用逗号分隔
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertUserDepartment(Long userId, String departmentIds) {
        int rs = 0;
        List<UserDepartment> userDepartments = Lists.newArrayList();
        String idArray[] = departmentIds.split(",");
        for (String departmentId : idArray) {
            UserDepartment userDepartment = new UserDepartment(userId, Long.valueOf(departmentId));
            //校验是否重复
            List<Department> companies = findListByUserId(userDepartment);
            if (null == companies || companies.isEmpty()) {
                //设置用户部门属性
                userDepartment.setCurrentUser();
                userDepartment.preInsert();
                userDepartments.add(userDepartment);
            }

        }
        if (0 < userDepartments.size()) {
            rs = dao.insertUserDepartment(userDepartments);
        }
        return rs;
    }

    /**
     * 根据用户id查找部门信息
     *
     * @param userDepartment 封装用户id
     * @return
     */
    public List<Department> findListByUserId(UserDepartment userDepartment) {
        //超级管理员拥有全部部门权限
        if (1 == userDepartment.getUserId()) {
            return dao.findAllList(new Department());
        } else {
            return dao.findListByUserId(userDepartment);
        }
    }

    /**
     * 根据用户id查找该用户未有的部门信息
     *
     * @param userDepartment 封装用户id
     * @return
     */
    public List<Department> findListNotInUserId(UserDepartment userDepartment) {
        //超级管理员不需要返回
        if (Global.ADMINISTRATOR_ID == userDepartment.getUserId()) {
            return null;
        } else {
            return dao.findListNotInUserId(userDepartment);
        }
    }

    @Override
    public String createDataScopeSql(Department entity) {
        return null;
    }

    /**
     * 根据部门编号查询已有销售库区
     * @param departmentId
     * @return
     */
    public List<Warehouse> getWarehouseById(Long departmentId) {
        return dao.getWarehouseById(departmentId);
    }

    /**
     * 批量添加与仓库关联信息
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertDepartmentWarehouse(List<DepartmentWarehouse> list) {
        for (DepartmentWarehouse departmentWarehouse:list) {
            // 防止重复绑定
            int count = dao.getCountByDoubleId(departmentWarehouse);
            if(count == 0) {
                try {
                    departmentWarehouse.setCurrentUser();
                    departmentWarehouse.preInsert();
                    dao.insertDepartmentWarehouse(departmentWarehouse);
                }catch (Exception e){
                    logger.error("列ID:"+departmentWarehouse.getId()+"添加失败:"+ e.getMessage());
                }
            }
        }
        return ResponseResult.ok();
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartmentWarehouse(String id) {
        dao.deleteDepartmentWarehouse(id);
    }
}
