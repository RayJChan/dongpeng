package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.BlCouponUsePerson;

import java.util.List;

@MyBatisDao
public interface BlCouponUsePersonDao extends BaseCrudDao<BlCouponUsePerson> {

    public List<BlCouponUsePerson> getUsers(Long examineCouponId);

    public int deleteUsers(Long examineCouponId);

}
