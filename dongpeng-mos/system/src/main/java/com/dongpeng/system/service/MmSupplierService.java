package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.Company;
import com.dongpeng.entity.system.MmSupplier;
import com.dongpeng.entity.system.Region;
import com.dongpeng.system.dao.MmSupplierDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuhongda
 */
@Service
public class MmSupplierService extends BaseCrudService<MmSupplierDao,MmSupplier> {
    @Override
    public String createDataScopeSql(MmSupplier entity) {
        return null;
    }
    @Autowired
    CompanyService companyService;

    @Autowired
    RegionService regionService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addAndUpdate(MmSupplier mmSupplier, HttpServletRequest request, Model model) throws OptimisticLockException {
        Company company = companyService.get(mmSupplier.getCompanyId());
        if(null == company){
            return ResponseResult.failByParam("公司不存在!");
        }
        Region province = regionService.get(mmSupplier.getProvinceId());
        if(null == province){
            return ResponseResult.failByParam("发货省不存在!");
        }
        Region city = regionService.get(mmSupplier.getCityId());
        if(null == city){
            return ResponseResult.failByParam("发货市不存在!");
        }
        Region  district = regionService.get(mmSupplier.getDistrictId());
        if(null == district){
            return ResponseResult.failByParam("发货区不存在!");
        }
        mmSupplier.setProvince(province.getName());
        mmSupplier.setCity(city.getName());
        mmSupplier.setDistrict(district.getName());
        mmSupplier.setCompanyName(company.getCompanyName());
        try {
            save(mmSupplier);
        }catch (Exception e){
            logger.info("保存失败："+e.getMessage());
            return ResponseResult.failByParam("保存失败");
        }
        return ResponseResult.ok();
    }
}
