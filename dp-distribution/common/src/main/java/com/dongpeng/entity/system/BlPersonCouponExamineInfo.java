package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class BlPersonCouponExamineInfo extends DataEntity<BlPersonCouponExamineInfo> {

    private  Long personId; //申请人id

    private  String personName; //申请人名称

    private  Long applyOrgId; //申请人所在组织id

    private  String applyOrgName; //申请人所在组织名称

    private  String phone; //申请人电话

    private  Long useOrgId; //使用的组织id

    private  String useOrgName; //使用的组织名称

    private  String applyProductName; //申请的商品名称

    private  Double discount; //申请的折扣

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

    private Long examineInfoId;//审核记录表ID

    private  Long businessId; //所属业务ID

    private  String businessType; //所属业务类型

    private Long businessTypeId;

    private  String businessDesc; //业务描述

    private  Long userId; //审核人id

    private  String userName; //审核人名称

    private  Long nextUserId; //下级审核人id

    private  String nextUserName; //下级审核人名称

    private  Long examineResultId; //审核结果id

    private  String examineResultName; //审核结果名称

    private  String examineRemark; //审核备注

    private Boolean processEnd; //是否结束

    private  String createrExamineName; //创建人名称

    private Date createExamineTime; //创建时间

    private  String  modifierExamineName; //修改人名称

    private  Date modifyExamineTime; //修改时间

    private Date createBeginTime;

    private Date createEndTime;

    private String serviceArea;//使用区域

    private List<Long> examineResultIdList;

    private List<Long> notExamineResultId;

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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
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

    public Long getExamineInfoId() {
        return examineInfoId;
    }

    public void setExamineInfoId(Long examineInfoId) {
        this.examineInfoId = examineInfoId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
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

    public Boolean getProcessEnd() {
        return processEnd;
    }

    public void setProcessEnd(Boolean processEnd) {
        this.processEnd = processEnd;
    }

    public String getCreaterExamineName() {
        return createrExamineName;
    }

    public void setCreaterExamineName(String createrExamineName) {
        this.createrExamineName = createrExamineName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateExamineTime() {
        return createExamineTime;
    }

    public void setCreateExamineTime(Date createExamineTime) {
        this.createExamineTime = createExamineTime;
    }

    public String getModifierExamineName() {
        return modifierExamineName;
    }

    public void setModifierExamineName(String modifierExamineName) {
        this.modifierExamineName = modifierExamineName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getModifyExamineTime() {
        return modifyExamineTime;
    }

    public void setModifyExamineTime(Date modifyExamineTime) {
        this.modifyExamineTime = modifyExamineTime;
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

    public List<Long> getNotExamineResultId() {
        return notExamineResultId;
    }

    public void setNotExamineResultId(List<Long> notExamineResultId) {
        this.notExamineResultId = notExamineResultId;
    }
}
