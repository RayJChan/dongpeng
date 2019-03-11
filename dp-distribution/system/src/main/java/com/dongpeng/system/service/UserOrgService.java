package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.UserOrg;
import com.dongpeng.system.dao.UserOrgDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserOrgService extends BaseCrudService<UserOrgDao,UserOrg> {
    @Override
    public String createDataScopeSql(UserOrg entity) {
        return null;
    }
}
