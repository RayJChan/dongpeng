package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 优惠券使用人
 */
public class BlCouponUsePerson extends DataEntity<BlCouponUsePerson> {

    private Long examineCouponId;

    private String userName;

    private String phone;

    public Long getExamineCouponId() {
        return examineCouponId;
    }

    public void setExamineCouponId(Long examineCouponId) {
        this.examineCouponId = examineCouponId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
