package com.dongpeng.schedulejob.task;

import com.dongpeng.schedulejob.entity.BaseTask;
import com.dongpeng.schedulejob.service.CouponService;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class CouponExpiredTast extends BaseTask {

    private Logger logger = LoggerFactory.getLogger(CouponExpiredTast.class);

    @Autowired
    private CouponService couponService;
    @Override
    public void run() {
        logger.info("过期优惠券开始");
        couponService.expireCoupon();
        logger.info("过期优惠券结束");
    }
}
