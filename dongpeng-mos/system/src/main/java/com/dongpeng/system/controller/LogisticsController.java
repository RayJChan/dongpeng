package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.Logistics;
import com.dongpeng.entity.system.UserLogistics;
import com.dongpeng.system.service.LogisticsService;
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
 * 物流档案接口controller
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsController extends BaseDataController<LogisticsService,Logistics> {
    /**
     * 分页查找物流档案列表
     * @param logistics 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Logistics logistics, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<Logistics>(pageNo, pageSize,-1), logistics));
    }

    /**
     * 获取单个物流档案，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个物流档案
     * @param logistics 封装物流档案数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Logistics logistics, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, logistics)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        Logistics nameFlag =  service.getLogisticsName(logistics.getLogisticsName());
        if(nameFlag != null){
            return ResponseResult.failByBusiness("物流名称:["+logistics.getLogisticsName()+"]已存在,添加失败!");
        }
        int rs=service.save(logistics);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存物流档案失败");
        }
    }

    /**
     * 更新一个物流档案信息
     * @param logistics 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = LogisticsService.class,handleName = "更新一个物流档案信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Logistics logistics, HttpServletRequest request, Model model) throws Exception {
        if(null==logistics.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Logistics logisticsTemp=service.get(logistics.getId());

        if(null==logisticsTemp){
            return ResponseResult.failByParam("id 无效");
        }
        Logistics nameFlag =  service.getLogisticsName(logistics.getLogisticsName());
        if(nameFlag != null){
            return ResponseResult.failByBusiness("物流名称:["+logistics.getLogisticsName()+"]已存在,修改失败!");
        }

        BeanUtils.copyBeanNotNull2Bean(logistics, logisticsTemp);//将logistics非NULL值覆盖logisticsTemp中的值

        int rs=service.save(logisticsTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新物流档案失败");
            return checkVersion(logisticsTemp);
        }*/
    }


    /**
     * 停用/启用 一个物流档案
     * <p>实际为逻辑删除</p>
     * @param id 物流档案id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new Logistics(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用物流档案失败":"停用物流档案失败");
        }
    }

    /**
     * 物理删除一个物流档案
     * @param id 物流档案id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Logistics(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除物流档案失败");
        }
    }*/

    /**
     * 批量物理删除物流档案
     * @param ids 物流档案id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Logistics(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

    /**
     * 插入用户物流
     * @param userId 用户id
     * @param logisticsIds 物流id集合，多个用逗号分隔
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertUserLogistics/{userId}", method = RequestMethod.POST)
    public ResponseResult insertUserLogistics(@PathVariable("userId") Long userId
            ,@RequestParam(required = true) String logisticsIds
            , HttpServletRequest request){
        int rs=service.insertUserLogistics(userId,logisticsIds);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入用户物流失败");
        }
    }

    /**
     * 根据用户id查找物流信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListByUserId(@PathVariable("userId") Long userId){
        return ResponseResult.ok(service.findListByUserId(new UserLogistics(userId)));
    }

    /**
     * 根据用户id查找该用户 未有的 物流信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listNotInUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListNotInUserId(@PathVariable("userId") Long userId){
        return ResponseResult.ok(service.findListNotInUserId(new UserLogistics(userId)));
    }
}
