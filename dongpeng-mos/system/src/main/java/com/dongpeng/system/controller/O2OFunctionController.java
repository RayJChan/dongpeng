package com.dongpeng.system.controller;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.service.O2OFunctionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/o2oFunction")
public class O2OFunctionController extends BaseDataController<O2OFunctionService, O2OFunction> {

    /**
     * 新增一个entity
     * @param o2oFunction 封装entity数据
     * @param model
     * @return
     */
    @Override
    public ResponseResult add(@RequestBody O2OFunction o2oFunction, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, o2oFunction)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        int rs=service.save(o2oFunction);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

}
