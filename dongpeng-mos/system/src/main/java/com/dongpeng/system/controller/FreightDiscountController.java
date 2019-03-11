package com.dongpeng.system.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.FreightDiscount;
import com.dongpeng.system.service.FreightDiscountService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * 费用折扣管理接口controller
 */
@RestController
@RequestMapping("/freightDiscount")
public class FreightDiscountController extends BaseDataController<FreightDiscountService,FreightDiscount> {
    @Override
    @EnableDetailLog(serviceclass = FreightDiscountService.class,handleName = "更新一个费用折扣")
    public ResponseResult update(@RequestBody FreightDiscount freightDiscount, HttpServletRequest request, Model model) throws Exception {
        return super.update(freightDiscount, request,model);
    }
}
