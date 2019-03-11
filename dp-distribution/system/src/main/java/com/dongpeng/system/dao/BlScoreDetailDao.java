package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.BlScoreDetail;
import com.dongpeng.entity.system.Dictionary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BlScoreDetailDao  extends BaseCrudDao<BlScoreDetail> {

    /**
     * 获取用户积分总额
     * @param userId
     * @return
     */
    public BigDecimal getScoreByUserId(Long userId);


    public List<BlScoreDetail> findListPage(Map<String,Object> map);


    /**
     * 优惠券积分总计
     * @param pcId
     * @return
     */
    public BigDecimal getScoreByPersonCouponId(Long pcId);

}
