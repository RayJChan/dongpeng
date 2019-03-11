package com.dongpeng.system.listener;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.system.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CouponCaculateListener {

    @Autowired
    private CouponService couponService;

    private Logger logger = LoggerFactory.getLogger(CouponCaculateListener.class);

    @RabbitListener(queues = "CouponCalculateDeadQueue")
    public void process(Coupon coupon)  {
        logger.info("start CouponCalculate"+new Date());
        try {
            couponService.cpnCalculate(coupon);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
