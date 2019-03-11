package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.dao.O2OFunctionDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class O2OFunctionService extends BaseCrudService<O2OFunctionDao, O2OFunction> {


    @Override
    public String createDataScopeSql(O2OFunction entity) {
        return null;
    }


}
