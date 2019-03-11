package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.StorageLocation;
import com.dongpeng.entity.system.Warehouse;
import com.dongpeng.system.dao.StorageLocationDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StorageLocationService extends BaseCrudService<StorageLocationDao,StorageLocation> {
    @Autowired
    private WarehouseService warehouseService;

    @Override
    public String createDataScopeSql(StorageLocation entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(StorageLocation storageLocation) throws OptimisticLockException {
        //如果库区id不为空，库区名称为空，自动查找补全库区名称
        if(null!=storageLocation.getWarehouseId() && StringUtils.isBlank(storageLocation.getWarehouseName())){
            Warehouse warehouse=warehouseService.get(storageLocation.getWarehouseId());
            if(null!=warehouse){
                storageLocation.setWarehouseName(warehouse.getWarehouseName());
            }
        }
        return super.save(storageLocation);
    }
}
