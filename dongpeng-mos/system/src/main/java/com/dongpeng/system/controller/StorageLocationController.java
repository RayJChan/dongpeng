package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.StorageLocation;
import com.dongpeng.system.service.StorageLocationService;
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
 * 库位接口controller
 */
@RestController
@RequestMapping("/storageLocation")
public class StorageLocationController extends BaseDataController<StorageLocationService,StorageLocation> {


    /**
     * 分页查找库位列表
     * @param storageLocation 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(StorageLocation storageLocation, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<StorageLocation>(pageNo, pageSize,-1), storageLocation));
    }

    /**
     * 获取单个库位，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个库位
     * @param storageLocation 封装库位数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody StorageLocation storageLocation, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, storageLocation)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        int rs=service.save(storageLocation);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存库位失败");
        }
    }

    /**
     * 更新一个库位信息
     * @param storageLocation 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = StorageLocationService.class,handleName = "更新一个库位信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody StorageLocation storageLocation, HttpServletRequest request, Model model) throws Exception {
        if(null==storageLocation.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        StorageLocation storageLocationTemp=service.get(storageLocation.getId());

        if(null==storageLocationTemp){
            return ResponseResult.failByParam("id 无效");
        }

        BeanUtils.copyBeanNotNull2Bean(storageLocation, storageLocationTemp);//将storageLocation非NULL值覆盖storageLocationTemp中的值

        int rs=service.save(storageLocationTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新库位失败");
            return checkVersion(storageLocationTemp);
        }*/
    }


    /**
     * 停用/启用 一个库位
     * <p>实际为逻辑删除</p>
     * @param id 库位id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new StorageLocation(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用库位失败":"停用库位失败");
        }
    }

    /**
     * 物理删除一个库位
     * @param id 库位id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new StorageLocation(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除库位失败");
        }
    }*/

    /**
     * 批量物理删除库位
     * @param ids 库位id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new StorageLocation(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

}
