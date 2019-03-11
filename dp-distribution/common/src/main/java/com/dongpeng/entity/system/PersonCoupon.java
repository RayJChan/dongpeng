package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;

public class PersonCoupon extends DataEntity<PersonCoupon> {
    @ExcelField(title = "流水码")
    private String pcNo;

    @ExcelField(title = "优惠券名称")
    private String cpnName;

    private Long cpnId;

    @ExcelField(title = "优惠券编码")
    private String cpnNo;

    private Long personId;

    @ExcelField(title = "用户名称")
    private String personName;

    @ExcelField(title = "用户电话号码")
    private Long personPhone;

    public Long getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(Long personPhone) {
        this.personPhone = personPhone;
    }

    private Long fromId;

    private String fromName;

    private Long statusId;

    @ExcelField(title = "状态名称")
    private String statusName;

    private Long orgId;

    @ExcelField(title = "核销组织名称")
    private String orgName;

    private Long writeoffId;

    @ExcelField(title = "核销用户名称")
    private String writeoffName;

    private Long writeoffPhone;

    @ExcelField(title = "核销时间")
    private Date writeoffTime;

    @ExcelField(title = "核销备注")
    private String writeoffRemark;

    @ExcelField(title = "核销金额")
    private BigDecimal dealAmount;

    @ExcelField(title = "实付金额")
    private BigDecimal payAmount;

    @ExcelField(title = "产生积分")
    private Integer score;

    @JsonIgnore
    @ExcelField(title = "优惠券类型")
    private String couponType;

    public String getCouponType() {
        return coupon.getTypeName();
    }

    @ExcelField(title = "优惠券描述")
    @JsonIgnore
    private String cpnIntro;

    public String getCpnIntro() {
        return coupon.getCpnIntro();
    }

    @JsonIgnore
    @ExcelField(title = "所属组织")
    private String cpnOrgName;

    public String getCpnOrgName() {
        return coupon.getOrgName();
    }

    @JsonIgnore
    @ExcelField(title = "优惠内容")
    private String faceValue;

    public String getFaceValue() {
        return coupon.getFaceValue();
    }

    private String dealBillUrl;

    private Coupon coupon;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public PersonCoupon(){
        super();
        this.dataTableName="优惠券领取明细表";
    }

    public String getPcNo() {
        return pcNo;
    }

    public void setPcNo(String pcNo) {
        this.pcNo = pcNo == null ? null : pcNo.trim();
    }

    public String getCpnName() {
        return cpnName;
    }

    public void setCpnName(String cpnName) {
        this.cpnName = cpnName == null ? null : cpnName.trim();
    }

    public Long getCpnId() {
        return cpnId;
    }

    public void setCpnId(Long cpnId) {
        this.cpnId = cpnId;
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
        this.personName = personName == null ? null : personName.trim();
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName == null ? null : fromName.trim();
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

    public Long getWriteoffId() {
        return writeoffId;
    }

    public void setWriteoffId(Long writeoffId) {
        this.writeoffId = writeoffId;
    }

    public String getWriteoffName() {
        return writeoffName;
    }

    public void setWriteoffName(String writeoffName) {
        this.writeoffName = writeoffName == null ? null : writeoffName.trim();
    }

    public Date getWriteoffTime() {
        return writeoffTime;
    }

    public void setWriteoffTime(Date writeoffTime) {
        this.writeoffTime = writeoffTime;
    }

    public String getWriteoffRemark() {
        return writeoffRemark;
    }

    public void setWriteoffRemark(String writeoffRemark) {
        this.writeoffRemark = writeoffRemark == null ? null : writeoffRemark.trim();
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDealBillUrl() {
        return dealBillUrl;
    }

    public void setDealBillUrl(String dealBillUrl) {
        this.dealBillUrl = dealBillUrl == null ? null : dealBillUrl.trim();
    }
    public String getCpnNo() {
        return cpnNo;
    }

    public void setCpnNo(String cpnNo) {
        this.cpnNo = cpnNo;
    }

    public Long getWriteoffPhone() {
        return writeoffPhone;
    }

    public void setWriteoffPhone(Long writeoffPhone) {
        this.writeoffPhone = writeoffPhone;
    }
}