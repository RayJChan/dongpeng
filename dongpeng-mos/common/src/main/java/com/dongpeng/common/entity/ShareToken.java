package com.dongpeng.common.entity;

import java.util.List;

public class ShareToken {
    private Long CouponId; //优惠券ID

    private Long fromId; // 分享者ID

    private List<String> phones; //可领取用户的手机号码

    private String sign; //签名

    private Long orgId;//组织id

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getCouponId() {
        return CouponId;
    }

    public void setCouponId(Long couponId) {
        CouponId = couponId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
