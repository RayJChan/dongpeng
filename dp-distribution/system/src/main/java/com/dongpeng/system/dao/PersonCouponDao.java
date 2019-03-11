package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface PersonCouponDao extends BaseCrudDao<PersonCoupon> {
    List<PersonCoupon> findListByCouponId(Long id);

    int statusChange(PersonCoupon personCoupon);

    List<PersonCoupon> findListByCouponIdAndUserId(@Param("couponId") Long couponId, @Param("userId") Long userId);

    List<PersonCoupon> findMyCoupons(CouponRequest couponRequest);

    int writeoff(PersonCoupon personCoupon);

    WriteoffInfoVo getWriteoffInfo(WriteoffRequest request);

    List<PersonCoupon> listWriteOff(WriteoffRequest request);

    PersonCoupon getWriteoffInfoDetail(WriteoffRequest request);

}
