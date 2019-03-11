package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class BlExamineCouponInfo extends DataEntity<BlExamineCouponInfo>  {

    private  Long businessId; //所属业务ID

    private  String businessType; //所属业务类型

    private  Long  businessTypeId;//业务类型ID

    private  String businessDesc; //业务描述

    private  Long userId; //审核人id

    private  String userName; //审核人名称

    private  Long nextUserId; //下级审核人id

    private  String nextUserName; //下级审核人名称

    private  Long examineResultId; //审核结果id

    private  List<Long> notExamineResultId ; //不包含某个审核结果条件

    private  String examineResultName; //审核结果名称

    private  String examineRemark; //审核备注

    private Boolean processEnd;

    private  String cpnName; //优惠券名称

    private Long couponId;//优惠券ID

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

    private  Date createCouponTime; //创建时间

    private  String  modifierCouponName; //修改人名称

    private  Date modifyCouponTime; //修改时间

    private Date createBeginTime;

    private Date createEndTime;

    private String serviceArea;//适用区域

    private List<Long> examineResultIdList;//查询用 状态列表

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(Long nextUserId) {
        this.nextUserId = nextUserId;
    }

    public String getNextUserName() {
        return nextUserName;
    }

    public void setNextUserName(String nextUserName) {
        this.nextUserName = nextUserName;
    }

    public Long getExamineResultId() {
        return examineResultId;
    }

    public void setExamineResultId(Long examineResultId) {
        this.examineResultId = examineResultId;
    }

    public String getExamineResultName() {
        return examineResultName;
    }

    public void setExamineResultName(String examineResultName) {
        this.examineResultName = examineResultName;
    }

    public String getExamineRemark() {
        return examineRemark;
    }

    public void setExamineRemark(String examineRemark) {
        this.examineRemark = examineRemark;
    }


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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getReceiveBeginTime() {
        return receiveBeginTime;
    }

    public void setReceiveBeginTime(Date receiveBeginTime) {
        this.receiveBeginTime = receiveBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public Boolean getProcessEnd() {
        return processEnd;
    }

    public void setProcessEnd(Boolean processEnd) {
        this.processEnd = processEnd;
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

    public List<Long> getExamineResultIdList() {
        return examineResultIdList;
    }

    public void setExamineResultIdList(List<Long> examineResultIdList) {
        this.examineResultIdList = examineResultIdList;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public List<Long> getNotExamineResultId() {
        return notExamineResultId;
    }

    public void setNotExamineResultId(List<Long> notExamineResultId) {
        this.notExamineResultId = notExamineResultId;
    }
}
