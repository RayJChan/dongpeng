package com.dongpeng.system.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.entity.system.FreightDetails;
import com.dongpeng.system.service.FreightDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 运费价格明细 管理接口 controller
 */
@RestController
@RequestMapping("/freightDetails")
public class FreightDetailsContorller extends BaseDataController<FreightDetailsService,FreightDetails> {

    @Override
    public ResponseResult add(@RequestBody FreightDetails freightDetails, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, freightDetails)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        List<FreightDetails> freightDetailsList=service.findList(freightDetails);
        if(!StringPlusUtils.isBlankList(freightDetailsList)){
            return ResponseResult.failByParam("该运费项目和地区已有运费价格明细");
        }

        int rs=service.save(freightDetails);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存运费价格明细失败");
        }
    }

    @Override
    @EnableDetailLog(serviceclass = FreightDetailsService.class,handleName = "更新运费价格明细")
    public ResponseResult update(@RequestBody FreightDetails freightDetails, HttpServletRequest request, Model model) throws Exception {
        if(null==freightDetails.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        FreightDetails freightDetailsTemp=service.get(freightDetails.getId());

        if(null==freightDetailsTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新projectId或areaId和旧数据不相等情况下，要判断是否会重复
        if( (null!=freightDetails.getProjectId() && null!=freightDetails.getAreaId()) &&
                 (freightDetails.getProjectId().longValue()!=freightDetailsTemp.getProjectId().longValue()
                 || freightDetails.getAreaId().longValue()!=freightDetailsTemp.getAreaId().longValue()) ){

            List<FreightDetails> freightDetailsList=service.findList(freightDetails);
            if(!StringPlusUtils.isBlankList(freightDetailsList)){
                return ResponseResult.failByParam("该运费项目和地区已有运费价格明细");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(freightDetails, freightDetailsTemp);//将company非NULL值覆盖companyTemp中的值

        int rs=service.save(freightDetailsTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return checkVersion(freightDetailsTemp);
        }*/
    }
}
