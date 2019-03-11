package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.entity.DataEntity;

public class BlExamineInfo extends DataEntity<BlExamineInfo> {

    @DataName("所属业务ID")
    private  Long businessId; //所属业务ID

    @DataName("业务类型名称")
    private  String businessType; //所属业务类型

    @DataName("业务类型ID")
    private Long businessTypeId;

    @DataName("业务描述")
    private  String businessDesc; //业务描述

    @DataName("审核人id")
    private  Long userId; //审核人id

    @DataName("审核人名称")
    private  String userName; //审核人名称

    @DataName("下级审核人id")
    private  Long nextUserId; //下级审核人id

    @DataName("下级审核人名称")
    private  String nextUserName; //下级审核人名称

    @DataName("审核结果id")
    private  Long examineResultId; //审核结果id

    @DataName("审核结果名称")
    private  String examineResultName; //审核结果名称

    @DataName("审核备注")
    private  String examineRemark; //审核备注

    @DataName("是否结束")
    private Boolean processEnd; //是否结束

    private String rankName;//审核人职级名称

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

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }
}
