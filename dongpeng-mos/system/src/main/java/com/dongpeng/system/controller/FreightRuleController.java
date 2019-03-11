package com.dongpeng.system.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.FreightRule;
import com.dongpeng.system.service.FreightRuleService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * 运费计算规则管理接口controller
 */
@RestController
@RequestMapping("/freightRule")
public class FreightRuleController extends BaseDataController<FreightRuleService,FreightRule> {

    @Override
    @EnableDetailLog(serviceclass = FreightRuleService.class,handleName ="更新一个运费计算规则")
    public ResponseResult update(@RequestBody FreightRule freightRule, HttpServletRequest request, Model model) throws Exception {
        return super.update(freightRule, request,model);
    }
}
