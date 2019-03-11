package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Breed;
import com.dongpeng.entity.system.FreightProject;
import com.dongpeng.system.dao.FreightProjectDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FreightProjectService extends BaseCrudService<FreightProjectDao,FreightProject> {
    @Autowired
    private BreedService breedService;

    @Override
    public String createDataScopeSql(FreightProject entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(FreightProject freightProject) throws OptimisticLockException {
        //如果品类id不为空，品类名称为空，自动查找补全品类名称
        if(null!=freightProject.getCategoryId() && StringUtils.isBlank(freightProject.getCategoryName())){
            Breed breed=breedService.get(freightProject.getCategoryId());
            freightProject.setCategoryName(null==breed?"":breed.getBreedName());
        }
        return super.save(freightProject);
    }
}
