package com.dongpeng.schedulejob.service;

import com.dongpeng.common.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "system",fallback = CouponServiceFallback.class)
public interface CouponService {
    @GetMapping("/coupon/expireCoupon")
    ResponseResult expireCoupon();
}

class CouponServiceFallback implements CouponService{

    @Override
    public ResponseResult expireCoupon() {
        return ResponseResult.failByBusiness("system服务不可用，无法过期优惠券");
    }
}
