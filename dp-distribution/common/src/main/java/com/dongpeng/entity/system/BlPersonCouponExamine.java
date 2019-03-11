package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户优惠券申请表
 */
public class BlPersonCouponExamine extends DataEntity<BlPersonCouponExamine> {

    @DataName("申请人id")
    private  Long personId; //申请人id

    @DataName("申请人名称")
    private  String personName; //申请人名称

    @DataName("申请人所在组织id")
    private  Long applyOrgId; //申请人所在组织id

    @DataName("申请人所在组织名称")
    private  String applyOrgName; //申请人所在组织名称

    @DataName("申请人电话")
    private  String phone; //申请人电话

    @DataName("使用的组织id")
    private  Long useOrgId; //使用的组织id

    @DataName("使用的组织名称")
    private  String useOrgName; //使用的组织名称

    @DataName("申请的商品名称")
    private  String applyProductName; //申请的商品名称

    @DataName("申请的折扣")
    private  double discount; //申请的折扣

    @DataName("使用类型id")
    private  Long useTypeId; //使用类型id

    @DataName("使用类型名称")
    private  String useTypeName; //使用类型名称

    @DataName("申请原因")
    private  String useReason; //申请原因

    @DataName("生成的优惠券id")
    private  Long couponId; //生成的优惠券id

    @DataName("生成的优惠券名称")
    private  String couponName; //生成的优惠券名称

    @DataName("申请状态名称")
    private  String applyStatusName; //申请状态名称

    @DataName("申请状态")
    private  Long applyStatusId; //申请状态

    @DataName("提交审核人ID")
    private  Long examineUserId; //提交审核人ID

    @DataName("审核人名称")
    private  String examineUserName; //审核人名称

    private Integer applyNum; //申请数量

    private String serviceArea;//使用范围

    private List<BlCouponUsePerson> blCouponUsePersonList;

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

    @NotNull
    public Long getUseOrgId() {
        return useOrgId;
    }

    public void setUseOrgId(Long useOrgId) {
        this.useOrgId = useOrgId;
    }

    @NotNull
    public String getUseOrgName() {
        return useOrgName;
    }

    public void setUseOrgName(String useOrgName) {
        this.useOrgName = useOrgName;
    }

    @NotNull
    @Length(min = 1, max = 20, message = "商品描述长度必须介于 1 和 20 之间")
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

    @NotNull
    @Length(min = 1, max = 200, message = "申请原因长度必须介于 1 和 200 之间")
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

    @NotNull
    public Long getExamineUserId() {
        return examineUserId;
    }

    public void setExamineUserId(Long examineUserId) {
        this.examineUserId = examineUserId;
    }

    @NotNull
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

    public List<BlCouponUsePerson> getBlCouponUsePersonList() {
        return blCouponUsePersonList;
    }

    public void setBlCouponUsePersonList(List<BlCouponUsePerson> blCouponUsePersonList) {
        this.blCouponUsePersonList = blCouponUsePersonList;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }
}
