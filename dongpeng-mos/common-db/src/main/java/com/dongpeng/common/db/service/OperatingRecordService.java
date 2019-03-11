package com.dongpeng.common.db.service;

import com.dongpeng.common.db.dao.OperatingRecordDao;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.entity.system.OperatingRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 操作记录service
 * <p>因各分支项目都需要日志记录，故把该service放到 common-db 项目中</p>
 */
@Service
@Transactional(readOnly = true)
public class OperatingRecordService extends BaseCrudService<OperatingRecordDao,OperatingRecord> {
    @Override
    public String createDataScopeSql(OperatingRecord entity) {
        return null;
    }
}
