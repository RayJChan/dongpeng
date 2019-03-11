package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Coupon extends DataEntity<Coupon> {

    @ExcelField(title = "优惠券名称",groups = {1,2})
    private String cpnName;

    @ExcelField(title = "优惠券编码",groups = {1,2})
    private String cpnNo;

    @ExcelField(title = "优惠券描述",groups = {1,2})
    private String cpnIntro;

    @ExcelField(title = "可领取数量",groups = {1,2})
    private Integer cpnNum;

    @ExcelField(title = "已领取数量",groups = {1,2})
    private Integer cpnReceivedNum;

    private Long orgId;

    @ExcelField(title = "所属组织",groups = {1,2})
    private String orgName;

    private Long typeId;

    @ExcelField(title = "优惠券类型",groups = {1,2})
    private String typeName;

    @ExcelField(title = "使用门槛",groups = {1,2})
    private BigDecimal useCondition;

    @ExcelField(title = "面值",groups = {1,2})
    private String faceValue;

    @ExcelField(title = "积分比例",groups = {1,2})
    private String scorePercent;

    @ExcelField(title = "领用开始时间",groups = {1,2})
    private Date receiveBeginTime;

    @ExcelField(title = "领用结束时间",groups = {1,2})
    private Date receiveEndTime;

    @ExcelField(title = "使用开始时间",groups = {1,2})
    private Date useBeginTime;

    @ExcelField(title = "使用结束时间",groups = {1,2})
    private Date useEndTime;

    private Long rankId;

    @ExcelField(title = "职级名称",groups = {1,2})
    private String rankName;

    @ExcelField(title = "是否可分享",groups = {1,2})
    private Boolean isShare;

    private Long receiveTypeId;

    @ExcelField(title = "领用类型",groups = {1,2})
    private String receiveTypeName;

    private Long statusId;

    @ExcelField(title = "优惠券状态",groups = {1,2})
    private String statusName;

    private Long examineStatusId;

    @ExcelField(title = "审核状态",groups = {2})
    private String examineStatusName;

    @ExcelField(title = "领取率",groups = {1})
    private BigDecimal receivePercent;

    @ExcelField(title = "核销数量",groups = {1})
    private Integer writeoffNum;

    @ExcelField(title = "核销率",groups = {1})
    private BigDecimal writeoffPercent;

    @ExcelField(title = "核销金额",groups = {1})
    private BigDecimal writeoffAmount;

    @ExcelField(title = "产生积分",groups = {1})
    private Integer score;

    @ExcelField(title = "实付金额",groups = {1})
    private BigDecimal payAmount;

    @ExcelField(title = "标签")
    private String tag;

    private Long sourceId;

    @ExcelField(title = "来源名")
    private String sourceName;

    @ExcelField(title = "服务区域")
    private String serviceArea;

    @ExcelField(title = "使用须知",groups = {2})
    private String useNote;


    List<PersonCoupon> personCoupons;

    public List<PersonCoupon> getPersonCoupons() {
        return personCoupons;
    }

    public void setPersonCoupons(List<PersonCoupon> personCoupons) {
        this.personCoupons = personCoupons;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public Coupon(){
        super();
        this.dataTableName="优惠券";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BigDecimal getWriteoffAmount() {
        return writeoffAmount;
    }

    public void setWriteoffAmount(BigDecimal writeoffAmount) {
        this.writeoffAmount = writeoffAmount;
    }

    public String getCpnName() {
        return cpnName;
    }

    public void setCpnName(String cpnName) {
        this.cpnName = cpnName == null ? null : cpnName.trim();
    }

    public String getCpnNo() {
        return cpnNo;
    }

    public void setCpnNo(String cpnNo) {
        this.cpnNo = cpnNo == null ? null : cpnNo.trim();
    }

    public String getCpnIntro() {
        return cpnIntro;
    }

    public void setCpnIntro(String cpnIntro) {
        this.cpnIntro = cpnIntro == null ? null : cpnIntro.trim();
    }

    public Integer getCpnNum() {
        return cpnNum;
    }

    public void setCpnNum(Integer cpnNum) {
        this.cpnNum = cpnNum;
    }

    public Integer getCpnReceivedNum() {
        return cpnReceivedNum;
    }

    public void setCpnReceivedNum(Integer cpnReceivedNum) {
        this.cpnReceivedNum = cpnReceivedNum;
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
        this.orgName = orgName == null ? null : orgName.trim();
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
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public BigDecimal getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(BigDecimal useCondition) {
        this.useCondition = useCondition;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue == null ? null : faceValue.trim();
    }

    public String getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(String scorePercent) {
        this.scorePercent = scorePercent == null ? null : scorePercent.trim();
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

    public Date getUseBeginTime() {
        return useBeginTime;
    }

    public void setUseBeginTime(Date useBeginTime) {
        this.useBeginTime = useBeginTime;
    }

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
        this.rankName = rankName == null ? null : rankName.trim();
    }

    public Boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(Boolean isShare) {
        this.isShare = isShare;
    }

    public Long getReceiveTypeId() {
        return receiveTypeId;
    }

    public void setReceiveTypeId(Long receiveTypeId) {
        this.receiveTypeId = receiveTypeId;
    }

    public String getReceiveTypeName() {
        return receiveTypeName;
    }

    public void setReceiveTypeName(String receiveTypeName) {
        this.receiveTypeName = receiveTypeName == null ? null : receiveTypeName.trim();
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
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public Long getExamineStatusId() {
        return examineStatusId;
    }

    public void setExamineStatusId(Long examineStatusId) {
        this.examineStatusId = examineStatusId;
    }

    public String getExamineStatusName() {
        return examineStatusName;
    }

    public void setExamineStatusName(String examineStatusName) {
        this.examineStatusName = examineStatusName == null ? null : examineStatusName.trim();
    }

    public BigDecimal getReceivePercent() {
        return receivePercent;
    }

    public void setReceivePercent(BigDecimal receivePercent) {
        this.receivePercent = receivePercent;
    }

    public Integer getWriteoffNum() {
        return writeoffNum;
    }

    public void setWriteoffNum(Integer writeoffNum) {
        this.writeoffNum = writeoffNum;
    }

    public BigDecimal getWriteoffPercent() {
        return writeoffPercent;
    }

    public void setWriteoffPercent(BigDecimal writeoffPercent) {
        this.writeoffPercent = writeoffPercent;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    public String getUseNote() {
        return useNote;
    }

    public void setUseNote(String useNote) {
        this.useNote = useNote;
    }
}