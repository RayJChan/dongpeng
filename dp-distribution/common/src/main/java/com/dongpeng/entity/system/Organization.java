package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 组织表 实体类
 */
public class Organization extends TreeEntity<Organization> {

    private static final long serialVersionUID = 1L;

    @DataName("组织名称")
    private String orgName;
    @DataName("组织编码")
    private String orgCode;
    @DataName("组织负责人")
    private String orgLeader;
    @DataName("审批负责人id")
    private Long approvalId;
    @DataName("审批负责人name")
    private String approvalName;
    @DataName("联系电话")
    private String phone;
    @DataName("组织类型id")
    private Long typeId;
    @DataName("组织类型名称")
    private String typeName;
    @DataName("省")
    private String province;
    @DataName("市")
    private String city;
    @DataName("区")
    private String district;
    @DataName("地址")
    private String address;
    @DataName("纬度")
    private BigDecimal lat;
    @DataName("经度")
    private BigDecimal lng;
    @DataName("图片")
    private String photo;
    @DataName("营业时间")
    private String businessHours;
    @DataName("可用区域")
    private String serviceArea;
    @DataName("省id")
    private Long provinceId;
    @DataName("市id")
    private Long cityId;
    @DataName("区id")
    private Long districtId;

    /*****  以下字段仅用作sql操作辅助，不跟数据库对应  ******/
    private BigDecimal beginLat;
    private BigDecimal endLat;
    private BigDecimal beginLng;
    private BigDecimal endLng;
    private Double distance;
    private List<Coupon> coupons;// 组织下的优惠券列表
    private int receiveNum; //门店优惠券总领取人数

    public Organization(){
        super();
        this.dataTableName="组织";
    }

    @NotNull
    @Length(min = 2,max = 20,message = "组织名称必须介于 2 和 20 之间")
    @ExcelField(title = "组织名称",sort = 10)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    @ExcelField(title = "组织编码",sort = 11,type = 1 )
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @NotNull
    @Length(min = 2,max = 20,message = "组织负责人必须介于 2 和 20 之间")
    @ExcelField(title = "组织负责人",sort = 20)
    public String getOrgLeader() {
        return orgLeader;
    }

    public void setOrgLeader(String orgLeader) {
        this.orgLeader = orgLeader == null ? null : orgLeader.trim();
    }

    @NotNull(message = "审批负责人id不能为空")
    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    @ExcelField(title = "审批负责人",sort = 40)
    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName == null ? null : approvalName.trim();
    }

    @NotNull
    @Length(min = 2,max = 11,message = "联系电话必须介于 2 和 11 之间")
    @ExcelField(title = "联系电话",sort = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    @NotNull(message = "组织类型id不能为空")
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @ExcelField(title = "组织类型名称",sort = 70)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    @Length(min = 2,max = 20,message = "省介于 2 和 20 之间")
    @ExcelField(title = "省",sort = 80)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    @Length(min = 2,max = 20,message = "市介于 2 和 20 之间")
    @ExcelField(title = "市",sort = 90)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Length(min = 2,max = 20,message = "区必须介于 2 和 20 之间")
    @ExcelField(title = "区",sort = 100)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @ExcelField(title = "地址",sort = 110)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ExcelField(title = "纬度",sort = 120)
    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @ExcelField(title = "经度",sort = 130)
    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    @ExcelField(title = "图片",sort = 140)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    @ExcelField(title = "营业时间",sort = 150)
    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours == null ? null : businessHours.trim();
    }

    public BigDecimal getBeginLat() {
        return beginLat;
    }

    public void setBeginLat(BigDecimal beginLat) {
        this.beginLat = beginLat;
    }

    public BigDecimal getEndLat() {
        return endLat;
    }

    public void setEndLat(BigDecimal endLat) {
        this.endLat = endLat;
    }

    public BigDecimal getBeginLng() {
        return beginLng;
    }

    public void setBeginLng(BigDecimal beginLng) {
        this.beginLng = beginLng;
    }

    public BigDecimal getEndLng() {
        return endLng;
    }

    public void setEndLng(BigDecimal endLng) {
        this.endLng = endLng;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    @NotNull
    @Length(min = 2,max = 64,message = "可用区域必须介于 2 和 64 之间")
    @ExcelField(title = "可用区域",sort = 160)
    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    @NotNull
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @NotNull
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @NotNull
    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
}
