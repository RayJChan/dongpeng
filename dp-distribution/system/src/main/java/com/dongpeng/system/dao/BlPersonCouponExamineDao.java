package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.BlPersonCouponExamine;
import com.dongpeng.entity.system.BlPersonCouponExamineInfo;
import com.dongpeng.entity.system.MyApplyCoupon;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BlPersonCouponExamineDao extends BaseCrudDao<BlPersonCouponExamine> {

    /**
     * 更改状态和更新优惠券ID
     * @param blPersonCouponExamine
     * @return
     */
    public int updateStatus(BlPersonCouponExamine blPersonCouponExamine);

    public List<BlPersonCouponExamineInfo> findPersonAndExamineInfoPage(BlPersonCouponExamineInfo blPersonCouponExamineInfo);

    public List<MyApplyCoupon> findMyApplyCouponPage(MyApplyCoupon myApplyCoupon);

    public BlPersonCouponExamine getPersonCouponExamineByCouponId(Long couponId);

    public List<Map<String,Object>> findMyApplyCouponUnfinished(Map<String,Object> map);

    public Map<String,Object> getPersonCouponExamineMap(Long id);

}
