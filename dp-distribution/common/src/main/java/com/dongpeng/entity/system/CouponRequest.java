package com.dongpeng.entity.system;

import com.dongpeng.common.entity.BaseEntity;

import java.util.Date;

public class CouponRequest extends BaseEntity<CouponRequest> {
    private Long orgId;

    private String orgParentIds;

    private Long personCouponStatusId;

    private Long userId;

    private Long regionId;

    private Long statusId;

    private Date date;

    private Long receiveTypeId;

    private Long sourceId;

    private Long limitIndex; //自定义分页

    private Long limitCount; //自定义分页大小;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
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

    public String getOrgParentIds() {
        return orgParentIds;
    }

    public void setOrgParentIds(String orgParentIds) {
        this.orgParentIds = orgParentIds;
    }

    public Long getReceiveTypeId() {
        return receiveTypeId;
    }

    public void setReceiveTypeId(Long receiveTypeId) {
        this.receiveTypeId = receiveTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getPersonCouponStatusId() {
        return personCouponStatusId;
    }

    public void setPersonCouponStatusId(Long personCouponStatusId) {
        this.personCouponStatusId = personCouponStatusId;
    }

    @Override
    public void preInsert() {

    }

    @Override
    public void preUpdate() {

    }
}
