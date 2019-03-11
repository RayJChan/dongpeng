package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Breed;
import com.dongpeng.system.dao.BreedDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BreedService extends BaseCrudService<BreedDao,Breed> {
    @Override
    public String createDataScopeSql(Breed entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(Breed breed) throws OptimisticLockException {
        //如果父id不为空，则获取其parentids
        if(null!=breed.getParentId() && 0!=breed.getParentId()){
            Breed parentBreed=dao.get(breed.getParentId());
            breed.setParentIds(
                    StringUtils.isNotBlank(parentBreed.getParentIds())?
                            parentBreed.getParentIds()+","+parentBreed.getId()
                            :parentBreed.getId().toString());
            breed.setParentName(parentBreed.getBreedName());
        }
        return super.save(breed);
    }

    /**
     * 根据上级id获得部门
     * @param upId 上级id
     * @return
     */
    public List<Breed> getByUp(Long upId){
        return dao.getByUp(upId);
    }

    /**
     * 根据根据部门编码获得部门
     * @param breedCode 部门编码
     * @return
     */
    public Breed getByBreedCode(String breedCode){
        return dao.getByBreedCode(breedCode);
    }
}
