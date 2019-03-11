package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.Warehouse;
import com.dongpeng.entity.system.UserWarehouse;
import com.dongpeng.system.service.WarehouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * 仓库管理接口controller
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController extends BaseDataController<WarehouseService, Warehouse> {

    /**
     * 分页查找仓库列表
     *
     * @param warehouse 封装查询参数
     * @param pageNo    页码
     * @param pageSize  每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{" + Global.PAGE_SIZE + "}/{" + Global.PAGE_NO + "}", method = RequestMethod.GET)
    public ResponseResult findPage(Warehouse warehouse, @PathVariable(Global.PAGE_NO) Integer pageNo, @PathVariable(Global.PAGE_SIZE) Integer pageSize) {
        return ResponseResult.ok(service.findPage(new Page<Warehouse>(pageNo, pageSize, -1), warehouse));
    }

    /**
     * 不分页查找仓库列表
     *
     * @param warehouse 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(Warehouse warehouse) {
        return ResponseResult.ok(service.findList(warehouse));
    }

    /**
     * 获取单个仓库，根据id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 模糊查询对应部门未添加的仓库
     * @param departmentId  部门编号
     * @param warehouseName   仓库编号
     * @return
     */
    @RequestMapping(value = "/findIsNotAll", method = RequestMethod.GET)
    public ResponseResult findIsNotAll(String departmentId,String warehouseName) {
        return ResponseResult.ok(service.findIsNotAll(departmentId,warehouseName));
    }

    /**
     * 新增一个仓库
     *
     * @param warehouse 封装仓库数据
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Warehouse warehouse, HttpServletRequest request, Model model) throws OptimisticLockException {
        if (!beanValidator(model, warehouse)) {
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Warehouse warehouseTemp = service.getByWarehouseCode(warehouse.getWarehouseCode());
        if (null != warehouseTemp) {
            return ResponseResult.failByParam("仓库编码已存在");
        }

        int rs = service.save(warehouse);
        if (1 == rs) {
            return ResponseResult.ok(null);
        } else {
            return ResponseResult.failByBusiness("保存仓库失败");
        }
    }

    /**
     * 更新一个仓库信息
     *
     * @param warehouse 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @Override
    @EnableDetailLog(serviceclass = WarehouseService.class, handleName = "更新一个仓库信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Warehouse warehouse, HttpServletRequest request, Model model) throws Exception {
        if (null == warehouse.getId()) {
            return ResponseResult.failByParam("id不能为空");
        }
        Warehouse warehouseTemp = service.get(warehouse.getId());

        if (null == warehouseTemp) {
            return ResponseResult.failByParam("id 无效");
        }

        //新编号和旧编号不相等情况下，要判断新编号是否会重复
        if (StringUtils.isNotBlank(warehouse.getWarehouseCode()) && !warehouseTemp.getWarehouseCode().equals(warehouse.getWarehouseCode())) {
            Warehouse warehouseByCode = service.getByWarehouseCode(warehouse.getWarehouseCode());
            if (null != warehouseByCode) {
                return ResponseResult.failByParam("仓库编码已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(warehouse, warehouseTemp);//将warehouse非NULL值覆盖warehouseTemp中的值

        int rs = service.save(warehouseTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新仓库失败");
            return checkVersion(warehouseTemp);
        }*/
    }


    /**
     * 停用/启用 一个仓库
     * <p>实际为逻辑删除</p>
     *
     * @param id         仓库id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @Override
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id, @PathVariable("deleteFlag") Boolean deleteFlag) {
        int rs = service.deleteToggle(new Warehouse(id, deleteFlag));
        if (1 == rs) {
            return ResponseResult.ok(null);
        } else {
            return ResponseResult.failByBusiness(deleteFlag ? "启用仓库失败" : "停用仓库失败");
        }
    }

    /**
     * 物理删除一个仓库
     * @param id 仓库id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Warehouse(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除仓库失败");
        }
    }*/

    /**
     * 批量物理删除仓库
     * @param ids 仓库id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Warehouse(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

    /**
     * 删除用户仓库
     *
     * @param userId       用户id
     * @param warehouseIds 仓库id集合，多个用逗号分隔
     * @return
     */
    @RequestMapping(value = "/deleteUserWarehouse/{userId}", method = RequestMethod.POST)
    public ResponseResult deleteUserWarehouse(@PathVariable("userId") Long userId, @RequestParam(required = true) String warehouseIds) {
        String[] idArray = warehouseIds.split(",");
        for (String warehouseId : idArray) {
            service.deleteUserWarehouse(new UserWarehouse(userId, Long.valueOf(warehouseId)));
        }
        return ResponseResult.ok(null);
    }

    /**
     * 插入用户仓库
     *
     * @param userId       用户id
     * @param warehouseIds 仓库id集合，多个用逗号分隔
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertUserWarehouse/{userId}", method = RequestMethod.POST)
    public ResponseResult insertUserWarehouse(@PathVariable("userId") Long userId
            , @RequestParam(required = true) String warehouseIds
            , HttpServletRequest request) {
        int rs = service.insertUserWarehouse(userId, warehouseIds);
        if (1 <= rs) {
            return ResponseResult.ok(null);
        } else {
            return ResponseResult.failByBusiness("插入用户仓库失败");
        }
    }

    /**
     * 根据用户id查找仓库信息
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListByUserId(@PathVariable("userId") Long userId) {
        return ResponseResult.ok(service.findListByUserId(new UserWarehouse(userId)));
    }

    /**
     * 根据用户id查找该用户 未有的 仓库信息
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listNotInUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListNotInUserId(@PathVariable("userId") Long userId) {
        return ResponseResult.ok(service.findListNotInUserId(new UserWarehouse(userId)));
    }

}
