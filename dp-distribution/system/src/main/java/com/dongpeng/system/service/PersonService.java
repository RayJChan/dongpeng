package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Person;
import com.dongpeng.system.dao.PersonDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonService extends BaseCrudService<PersonDao,Person> {
    @Override
    public String createDataScopeSql(Person entity) {
        return null;
    }
}
