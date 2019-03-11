package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.FreightDiscount;
import com.dongpeng.system.dao.FreightDiscountDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FreightDiscountService extends BaseCrudService<FreightDiscountDao,FreightDiscount> {
    @Override
    public String createDataScopeSql(FreightDiscount entity) {
        return null;
    }

}
