package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.FreightDetails;
import com.dongpeng.entity.system.Region;
import com.dongpeng.system.dao.FreightDetailsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FreightDetailsService extends BaseCrudService<FreightDetailsDao,FreightDetails> {
    @Autowired
    private RegionService regionService;
    @Override
    public String createDataScopeSql(FreightDetails entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(FreightDetails entity) throws OptimisticLockException {
        //如果地区id不为空，省市区名称存在空，自动查找补全省市区名称
        if(null!=entity.getAreaId() &&
                (StringUtils.isBlank(entity.getProvince()) || StringUtils.isBlank(entity.getCity()) || StringUtils.isBlank(entity.getDistrict())) ){
            Region region=regionService.get(entity.getAreaId());
            if(null!=region){
                /*entity.setProvince(region.getProvince());
                entity.setCity(region.getCity());
                entity.setDistrict(region.getDistrict());*/
                //TODO 设置省市区
            }
        }
        return super.save(entity);
    }
}
