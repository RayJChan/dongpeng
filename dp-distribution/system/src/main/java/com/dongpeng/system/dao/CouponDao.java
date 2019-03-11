package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.CouponRequest;
import com.dongpeng.entity.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@MyBatisDao
public interface CouponDao extends BaseCrudDao<Coupon> {
    int statusChange(Coupon coupon);
    
    int examineStatusChange(Coupon coupon);

    List<Coupon> findPublicCoupons(CouponRequest couponRequest);

    List<Coupon> findTagCoupons(CouponRequest couponRequest);

    List<Coupon> findCouponsByOrgId(CouponRequest couponRequest);

    List<Coupon> findShareCoupons(@Param("userId") Long userId,@Param("couponStatusId") Long couponStatusId);

    List<Coupon> findAbleShareCoupons(CouponRequest couponRequest);

    List<Coupon> findExpiredCoupon(@Param("activeStatusId") Long activeStatusId);

    List<User> findReceivedUserFromShare(@Param("couponId") Long couponId,@Param("userId") Long userId);

}
