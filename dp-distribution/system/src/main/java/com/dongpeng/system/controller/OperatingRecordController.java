package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.OperatingRecordService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.OperatingRecord;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 操作记录接口controller
 */
@RestController
@RequestMapping("/operatingRecord")
public class OperatingRecordController extends BaseDataController<OperatingRecordService,OperatingRecord> {

    /*@Autowired
    private OperatingRecordService service;*/

    /**
     * 分页查找操作记录列表
     * @param operatingRecord 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(OperatingRecord operatingRecord, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(1==pageNo){
            return ResponseResult.ok(service.findPage(new Page<OperatingRecord>(pageNo, pageSize), operatingRecord));
        }else {
            return ResponseResult.ok(service.findPage(new Page<OperatingRecord>(pageNo, pageSize,-1), operatingRecord));
        }
    }

    /**
     * 获取单个操作记录，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个操作记录
     * @param operatingRecord 封装操作记录数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody OperatingRecord operatingRecord, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, operatingRecord)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        int rs=service.save(operatingRecord);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存操作记录失败");
        }
    }

    /**
     * 更新一个操作记录信息
     * @param operatingRecord 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody OperatingRecord operatingRecord, HttpServletRequest request, Model model) throws Exception {
        if(null==operatingRecord.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        OperatingRecord operatingRecordTemp=service.get(operatingRecord.getId());

        if(null==operatingRecordTemp){
            return ResponseResult.failByParam("id 无效");
        }

        BeanUtils.copyBeanNotNull2Bean(operatingRecord, operatingRecordTemp);//将operatingRecord非NULL值覆盖operatingRecordTemp中的值

        int rs=service.save(operatingRecordTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新操作记录失败");
            return checkVersion(operatingRecordTemp);
        }*/
    }


    /**
     * 停用/启用 一个操作记录
     * <p>实际为逻辑删除</p>
     * @param id 操作记录id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new OperatingRecord(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用操作记录失败":"停用操作记录失败");
        }
    }

    /**
     * 物理删除一个操作记录
     * @param id 操作记录id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new OperatingRecord(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除操作记录失败");
        }
    }*/

    /**
     * 批量物理删除操作记录
     * @param ids 操作记录id集合，多个用逗号分隔
     * @return
     */
   /* @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new OperatingRecord(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

}
