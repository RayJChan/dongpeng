package com.dongpeng.entity.system;

import java.util.Date;

public class WriteoffRequest {

    private Long personCouponId;

    private String writeoffCode;

    private String dealAmount;

    private String dealBillUrl;

    private String remark;

    private String personName;

    private Long personPhone;

    private Date startDate;

    private Date endDate;

    private Long personCouponStatusId;

    private Long userId;

    private Long limitIndex;

    private Long limitCount;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(Long personPhone) {
        this.personPhone = personPhone;
    }

    public Long getPersonCouponId() {
        return personCouponId;
    }

    public void setPersonCouponId(Long personCouponId) {
        this.personCouponId = personCouponId;
    }

    public Long getLimitIndex() {
        return limitIndex;
    }

    public void setLimitIndex(Long limitIndex) {
        this.limitIndex = limitIndex;
    }

    public Long getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Long limitCount) {
        this.limitCount = limitCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPersonCouponStatusId() {
        return personCouponStatusId;
    }

    public void setPersonCouponStatusId(Long personCouponStatusId) {
        this.personCouponStatusId = personCouponStatusId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWriteoffCode() {
        return writeoffCode;
    }

    public void setWriteoffCode(String writeoffCode) {
        this.writeoffCode = writeoffCode;
    }

    public String getDealBillUrl() {
        return dealBillUrl;
    }

    public void setDealBillUrl(String dealBillUrl) {
        this.dealBillUrl = dealBillUrl;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }
}
