package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.BlCouponUsePerson;
import com.dongpeng.system.dao.BlCouponUsePersonDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BlCouponUsePersonService extends BaseCrudService<BlCouponUsePersonDao,BlCouponUsePerson> {

    @Override
    public String createDataScopeSql(BlCouponUsePerson entity) {
        return null;
    }

    public List<BlCouponUsePerson> getUsersByCouponExamineId(Long id){

        return dao.getUsers(id);
    }

    @Transactional
    public int deleteUsers(Long examineCouponId){
        if(null==examineCouponId){
            return 1;
        }
        return dao.deleteUsers(examineCouponId);
    }


}
