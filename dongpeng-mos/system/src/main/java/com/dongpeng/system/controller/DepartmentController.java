package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Department;
import com.dongpeng.entity.system.DepartmentWarehouse;
import com.dongpeng.entity.system.UserDepartment;
import com.dongpeng.system.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 部门管理接口controller
 */
@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseDataController<DepartmentService, Department> {
    /*@Autowired
    private DepartmentService service;*/

    /**
     * 分页查找部门列表
     *
     * @param department 封装查询参数
     * @param pageNo     页码
     * @param pageSize   每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{" + Global.PAGE_SIZE + "}/{" + Global.PAGE_NO + "}", method = RequestMethod.GET)
    public ResponseResult findPage(Department department, @PathVariable(Global.PAGE_NO) Integer pageNo, @PathVariable(Global.PAGE_SIZE) Integer pageSize) {
        return ResponseResult.ok(service.findPage(new Page<Department>(pageNo, pageSize), department));
    }

    /**
     * 不分页查找部门列表
     *
     * @param department 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(Department department) {
        return ResponseResult.ok(service.findList(department));
    }

    /**
     * 获取单个部门，根据id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个部门
     *
     * @param department 封装部门数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Department department, HttpServletRequest request, Model model) throws OptimisticLockException {
        if (!beanValidator(model, department)) {
            logger.info(department.getDepartmentTypeName());
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        /*Department departmentTemp=service.getByDepartmentCode(department.getDepartmentCode());
        if(null!=departmentTemp){
            return ResponseResult.failByParam("部门编码已存在");
        }*/

        int rs = service.save(department);
        if (1 == rs) {
            return ResponseResult.ok(null);
        } else if (2 == rs) {
            return ResponseResult.failByParam("系统已经有部门/票号字母");
        } else {
            return ResponseResult.failByBusiness("保存部门失败");
        }
    }

    /**
     * 更新一个部门信息
     *
     * @param department 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = DepartmentService.class, handleName = "更新一个部门信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Department department, HttpServletRequest request, Model model) throws Exception {
        logger.info("--------------" + department.getDepartmentTypeName());
        if (null == department.getId()) {
            return ResponseResult.failByParam("id不能为空");
        }
        Department departmentTemp = service.get(department.getId());

        if (null == departmentTemp) {
            return ResponseResult.failByParam("id 无效");
        }

        //新编号和旧编号不相等情况下，要判断新编号是否会重复
        if (StringUtils.isNotBlank(department.getDepartmentCode()) && !departmentTemp.getDepartmentCode().equals(department.getDepartmentCode())) {
            Department departmentByCode = service.getByDepartmentCode(department.getDepartmentCode());
            if (null != departmentByCode) {
                return ResponseResult.failByParam("部门编码已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(department, departmentTemp);//将department非NULL值覆盖departmentTemp中的值

        int rs = service.save(departmentTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新部门失败");
            return checkVersion(departmentTemp);
        }*/
    }


    /**
     * 停用/启用 一个部门
     * <p>实际为逻辑删除</p>
     *
     * @param id         部门id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id, @PathVariable("deleteFlag") Boolean deleteFlag) {
        int rs = service.deleteToggle(new Department(id, deleteFlag));
        if (1 == rs) {
            return ResponseResult.ok(null);
        } else {
            return ResponseResult.failByBusiness(deleteFlag ? "启用部门失败" : "停用部门失败");
        }
    }

    /**
     * 物理删除一个部门
     * @param id 部门id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Department(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除部门失败");
        }
    }*/

    /**
     * 批量物理删除部门
     * @param ids 部门id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Department(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

    /**
     * 删除用户部门
     *
     * @param userId        用户id
     * @param departmentIds 部门id集合，多个用逗号分隔
     * @return
     */
    @RequestMapping(value = "/deleteUserDepartment/{userId}", method = RequestMethod.POST)
    public ResponseResult deleteUserDepartment(@PathVariable("userId") Long userId, @RequestParam(required = true) String departmentIds) {
        String idArray[] = departmentIds.split(",");
        for (String departmentId : idArray) {
            service.deleteUserDepartment(new UserDepartment(userId, Long.valueOf(departmentId)));
        }
        return ResponseResult.ok(null);
    }

    /**
     * 插入用户部门
     *
     * @param userId        用户id
     * @param departmentIds 部门id集合，多个用逗号分隔
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertUserDepartment/{userId}", method = RequestMethod.POST)
    public ResponseResult insertUserDepartment(@PathVariable("userId") Long userId
            , @RequestParam(required = true) String departmentIds
            , HttpServletRequest request) {
        int rs = service.insertUserDepartment(userId, departmentIds);
        if (1 <= rs) {
            return ResponseResult.ok(null);
        } else {
            return ResponseResult.failByBusiness("插入用户部门失败");
        }
    }

    /**
     * 根据用户id查找部门信息
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListByUserId(@PathVariable("userId") Long userId) {
        return ResponseResult.ok(service.findListByUserId(new UserDepartment(userId)));
    }

    /**
     * 根据用户id查找该用户 未有的 部门信息
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listNotInUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListNotInUserId(@PathVariable("userId") Long userId) {
        return ResponseResult.ok(service.findListNotInUserId(new UserDepartment(userId)));
    }

    /**
     * 根据部门编号查询已有销售库区
     *
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/getWarehouseById/{departmentId}", method = RequestMethod.GET)
    public ResponseResult getWarehouseById(@PathVariable("departmentId") Long departmentId) {
        return ResponseResult.ok(service.getWarehouseById(departmentId));
    }


    /**
     * 批量添加部门与仓库关联信息
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/addDepartmentWarehouse", method = RequestMethod.POST)
    public ResponseResult insertDepartmentWarehouse(@RequestBody List<DepartmentWarehouse> list, HttpServletRequest request, Model model) throws Exception {
        return service.insertDepartmentWarehouse(list);
    }

    /**
     * 批量停用部门与仓库关联
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteDepartmentWarehouse", method = RequestMethod.POST)
    public ResponseResult deleteDepartmentWarehouse(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.deleteDepartmentWarehouse(id);
        }
        return ResponseResult.ok();
    }



}
