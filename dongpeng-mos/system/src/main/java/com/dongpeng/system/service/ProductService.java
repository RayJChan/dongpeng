package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.db.service.BaseService;
import com.dongpeng.entity.system.Product;
import com.dongpeng.system.dao.ProductDao;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseCrudService<ProductDao,Product> {
    @Override
    public String createDataScopeSql(Product entity) {
        return null;
    }
}
