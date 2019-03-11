package com.dongpeng.system.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.FreightItem;
import com.dongpeng.system.service.FreightItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * 费用项管理接口controller
 */
@RestController
@RequestMapping("/freightItem")
public class FreightItemController extends BaseDataController<FreightItemService,FreightItem> {

    @Override
    public ResponseResult add(@RequestBody FreightItem freightItem, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, freightItem)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        FreightItem freightItemTemp=service.getByName(freightItem.getItemName());
        if(null!=freightItemTemp){
            return ResponseResult.failByParam("费用项名称已存在");
        }

        int rs=service.save(freightItem);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存费用项失败");
        }
    }

    @Override
    @EnableDetailLog(serviceclass = FreightItemService.class,handleName = "更新一个费用项")
    public ResponseResult update(@RequestBody FreightItem freightItem, HttpServletRequest request, Model model) throws Exception {
        if(null==freightItem.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        FreightItem freightItemTemp=service.get(freightItem.getId());

        if(null==freightItemTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新名称和旧名称不相等情况下，要判断新名称是否会重复
        if(StringUtils.isNotBlank(freightItem.getItemName()) && !freightItemTemp.getItemName().equals(freightItem.getItemName())){
            FreightItem freightItemByName=service.getByName(freightItem.getItemName());
            if(null!=freightItemByName){
                return ResponseResult.failByParam("费用项已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(freightItem, freightItemTemp);//将非NULL值覆盖companyTemp中的值

        int rs=service.save(freightItemTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return checkVersion(freightItemTemp);
        }*/
    }
}
