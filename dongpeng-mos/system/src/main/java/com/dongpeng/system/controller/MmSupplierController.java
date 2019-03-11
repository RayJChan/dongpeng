package com.dongpeng.system.controller;

import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.Company;
import com.dongpeng.entity.system.MmSupplier;
import com.dongpeng.system.service.CompanyService;
import com.dongpeng.system.service.MmSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuhongda
 * 供应商
 */

@RestController
@RequestMapping("/mmSupplier")
public class MmSupplierController extends BaseDataController<MmSupplierService,MmSupplier> {
    /**
     * 添加供应商
     * @param mmSupplier
     * @param request
     * @param model
     * @return
     * @throws OptimisticLockException
     */
    @RequestMapping(value = "/addAndUpdate",method = RequestMethod.POST)
    public ResponseResult addAndUpdate(@RequestBody  MmSupplier mmSupplier, HttpServletRequest request, Model model) throws OptimisticLockException {
        if (!beanValidator(model, mmSupplier)) {
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        return service.addAndUpdate(mmSupplier,request,model);
    }

}
