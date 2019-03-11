package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

/**
 * 我的申请优惠券实体
 */
public class MyApplyCoupon extends DataEntity<Map<String,Object>> {

    private  Long personId; //申请人id

    private  String personName; //申请人名称

    private  Long applyOrgId; //申请人所在组织id

    private  String applyOrgName; //申请人所在组织名称

    private  String phone; //申请人电话

    private  Long useOrgId; //使用的组织id

    private  String useOrgName; //使用的组织名称

    private  String applyProductName; //申请的商品名称

    private  double discount; //申请的折扣

    private  Long useTypeId; //使用类型id

    private  String useTypeName; //使用类型名称

    private  String useReason; //申请原因

    private  Long couponId; //生成的优惠券id

    private  String couponName; //生成的优惠券名称

    private  String applyStatusName; //申请状态名称

    private  Long applyStatusId; //申请状态

    private  Long examineUserId; //提交审核人ID

    private  String examineUserName; //审核人名称

    private Integer applyNum; //申请数量

    private  String cpnName; //优惠券名称

    private  String cpnNo; //优惠券编码

    private  String cpnIntro; //优惠券描述

    private  int cpnNum; //优惠券数量

    private  Long orgId; //组织id

    private  String orgName; //组织名称

    private  Long typeId; //优惠类型id

    private  String typeName; //优惠类型名称

    private  Double useCondition; //使用门槛

    private  String faceValue; //面值

    private  Date receiveBeginTime; //领取开始时间

    private  Date receiveEndTime; //领取结束时间

    private  Date useBeginTime; //使用开始时间

    private  Date useEndTime; //使用结束时间

    private  Long rankId; //可领职级id

    private  String rankName; //职级名称

    private  int isShare; //是否可分享

    private  Long statusId; //状态id

    private  String statusName; //状态名称

    private  Long couponExamineStatusId; //审核状态id

    private  String couponExamineStatusName; //审核状态名称

    private  String createrCouponName; //创建人名称

    private Date createCouponTime; //创建时间

    private  String  modifierCouponName; //修改人名称

    private  Date modifyCouponTime; //修改时间

    private Date createBeginTime;

    private Date createEndTime;

    public String getCpnName() {
        return cpnName;
    }

    public void setCpnName(String cpnName) {
        this.cpnName = cpnName;
    }

    public String getCpnNo() {
        return cpnNo;
    }

    public void setCpnNo(String cpnNo) {
        this.cpnNo = cpnNo;
    }

    public String getCpnIntro() {
        return cpnIntro;
    }

    public void setCpnIntro(String cpnIntro) {
        this.cpnIntro = cpnIntro;
    }

    public int getCpnNum() {
        return cpnNum;
    }

    public void setCpnNum(int cpnNum) {
        this.cpnNum = cpnNum;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(Double useCondition) {
        this.useCondition = useCondition;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }


    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getCouponExamineStatusId() {
        return couponExamineStatusId;
    }

    public void setCouponExamineStatusId(Long couponExamineStatusId) {
        this.couponExamineStatusId = couponExamineStatusId;
    }

    public String getCouponExamineStatusName() {
        return couponExamineStatusName;
    }

    public void setCouponExamineStatusName(String couponExamineStatusName) {
        this.couponExamineStatusName = couponExamineStatusName;
    }

    public String getCreaterCouponName() {
        return createrCouponName;
    }

    public void setCreaterCouponName(String createrCouponName) {
        this.createrCouponName = createrCouponName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateCouponTime() {
        return createCouponTime;
    }

    public void setCreateCouponTime(Date createCouponTime) {
        this.createCouponTime = createCouponTime;
    }

    public String getModifierCouponName() {
        return modifierCouponName;
    }

    public void setModifierCouponName(String modifierCouponName) {
        this.modifierCouponName = modifierCouponName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getModifyCouponTime() {
        return modifyCouponTime;
    }

    public void setModifyCouponTime(Date modifyCouponTime) {
        this.modifyCouponTime = modifyCouponTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(Date createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getApplyOrgId() {
        return applyOrgId;
    }

    public void setApplyOrgId(Long applyOrgId) {
        this.applyOrgId = applyOrgId;
    }

    public String getApplyOrgName() {
        return applyOrgName;
    }

    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUseOrgId() {
        return useOrgId;
    }

    public void setUseOrgId(Long useOrgId) {
        this.useOrgId = useOrgId;
    }

    public String getUseOrgName() {
        return useOrgName;
    }

    public void setUseOrgName(String useOrgName) {
        this.useOrgName = useOrgName;
    }

    public String getApplyProductName() {
        return applyProductName;
    }

    public void setApplyProductName(String applyProductName) {
        this.applyProductName = applyProductName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Long getUseTypeId() {
        return useTypeId;
    }

    public void setUseTypeId(Long useTypeId) {
        this.useTypeId = useTypeId;
    }

    public String getUseTypeName() {
        return useTypeName;
    }

    public void setUseTypeName(String useTypeName) {
        this.useTypeName = useTypeName;
    }

    public String getUseReason() {
        return useReason;
    }

    public void setUseReason(String useReason) {
        this.useReason = useReason;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getApplyStatusName() {
        return applyStatusName;
    }

    public void setApplyStatusName(String applyStatusName) {
        this.applyStatusName = applyStatusName;
    }

    public Long getApplyStatusId() {
        return applyStatusId;
    }

    public void setApplyStatusId(Long applyStatusId) {
        this.applyStatusId = applyStatusId;
    }

    public Long getExamineUserId() {
        return examineUserId;
    }

    public void setExamineUserId(Long examineUserId) {
        this.examineUserId = examineUserId;
    }

    public String getExamineUserName() {
        return examineUserName;
    }

    public void setExamineUserName(String examineUserName) {
        this.examineUserName = examineUserName;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Date getReceiveBeginTime() {
        return receiveBeginTime;
    }

    public void setReceiveBeginTime(Date receiveBeginTime) {
        this.receiveBeginTime = receiveBeginTime;
    }

    public Date getReceiveEndTime() {
        return receiveEndTime;
    }

    public void setReceiveEndTime(Date receiveEndTime) {
        this.receiveEndTime = receiveEndTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUseBeginTime() {
        return useBeginTime;
    }

    public void setUseBeginTime(Date useBeginTime) {
        this.useBeginTime = useBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }
}
