package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.BreedLogistics;
import com.dongpeng.system.dao.BreedLogisticsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BreedLogisticsService extends BaseCrudService<BreedLogisticsDao,BreedLogistics> {
    @Override
    public String createDataScopeSql(BreedLogistics entity) {
        return null;
    }
}
