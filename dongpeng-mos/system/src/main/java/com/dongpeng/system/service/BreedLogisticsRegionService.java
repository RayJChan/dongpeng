package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.BreedLogisticsRegion;
import com.dongpeng.entity.system.Region;
import com.dongpeng.system.dao.BreedLogisticsRegionDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BreedLogisticsRegionService extends BaseCrudService<BreedLogisticsRegionDao,BreedLogisticsRegion> {
    @Autowired
    private RegionService regionService;

    @Override
    public String createDataScopeSql(BreedLogisticsRegion entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(BreedLogisticsRegion breedLogisticsRegion) throws OptimisticLockException {
        //如果地区id不为空，省市区名称存在空，自动查找补全省市区名称
        if(null!=breedLogisticsRegion.getRegionId() &&
                (StringUtils.isBlank(breedLogisticsRegion.getProvince()) || StringUtils.isBlank(breedLogisticsRegion.getCity()) || StringUtils.isBlank(breedLogisticsRegion.getDistrict())) ){
            Region region=regionService.get(breedLogisticsRegion.getRegionId());
            if(null!=region){
                /*breedLogisticsRegion.setProvince(region.getProvince());
                breedLogisticsRegion.setCity(region.getCity());
                breedLogisticsRegion.setDistrict(region.getDistrict());*/
                //TODO 设置省市区
            }
        }
        return super.save(breedLogisticsRegion);
    }
}
