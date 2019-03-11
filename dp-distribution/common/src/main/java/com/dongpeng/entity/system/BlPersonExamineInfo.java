package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class BlPersonExamineInfo extends DataEntity<BlPersonExamineInfo> {

    private  String personName; //姓名

    private  String phone; //手机号码

    private  Long orgId; //组织id

    private  String orgName; //组织名称

    private  String province; //省

    private  String city; //市

    private  String district; //区

    private  String address; //详细地址

    private  Long applyId; //申请状态

    private  String applyName; //申请名称

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

    private Long examineId ;

    private Date examineCreateTime;

    private Date createBeginTime;

    private Date createEndTime;

    private List<Long> examineResultIdList;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
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

    public Long getExamineId() {
        return examineId;
    }

    public void setExamineId(Long examineId) {
        this.examineId = examineId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getExamineCreateTime() {
        return examineCreateTime;
    }

    public void setExamineCreateTime(Date examineCreateTime) {
        this.examineCreateTime = examineCreateTime;
    }


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
}
