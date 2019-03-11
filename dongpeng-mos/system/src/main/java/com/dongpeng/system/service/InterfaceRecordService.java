package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.InterfaceRecord;
import com.dongpeng.system.dao.InterfaceRecordDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InterfaceRecordService extends BaseCrudService<InterfaceRecordDao,InterfaceRecord> {
    @Override
    public String createDataScopeSql(InterfaceRecord entity) {
        return null;
    }
}
