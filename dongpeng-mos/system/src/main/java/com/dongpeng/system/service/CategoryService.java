package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Category;
import com.dongpeng.system.dao.CategoryDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService extends BaseCrudService<CategoryDao,Category> {
    @Override
    public String createDataScopeSql(Category entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(Category entity) throws OptimisticLockException {
        if (dao.findAllList(entity) != null) {


        }

        return super.save(entity);
    }
}
