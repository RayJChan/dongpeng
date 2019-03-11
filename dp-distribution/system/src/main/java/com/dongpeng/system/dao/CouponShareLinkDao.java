package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.CouponShareLink;

import java.util.List;

@MyBatisDao
public interface CouponShareLinkDao extends BaseCrudDao<CouponShareLink> {
    List<CouponShareLink> getParent(CouponShareLink couponShareLink);

    List<CouponShareLink> isExisted(CouponShareLink couponShareLink);
}
