package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 员工申请审核表
 */
public class BlPersonExamine extends DataEntity<BlPersonExamine> {

    @DataName("姓名")
    private  String personName; //姓名

    @DataName("手机号码")
    private  String phone; //手机号码

    @DataName("组织id")
    private  Long orgId; //组织id

    @DataName("组织名称")
    private  String orgName; //组织名称

    @DataName("省")
    private  String province; //省

    @DataName("市")
    private  String city; //市

    @DataName("区")
    private  String district; //区

    @DataName("详细地址")
    private  String address; //详细地址

    @DataName("申请状态")
    private  Long applyId; //申请状态

    @DataName("申请状态名称")
    private  String applyName; //申请名称

    public BlPersonExamine(){}

    public BlPersonExamine(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }


    @NotNull
    @Length(min = 1, max = 10, message = "名字长度必须介于 1 和 10 之间")
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @NotNull
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$", message = "不是完整的11位手机号或者正确的手机号前七位")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotNull
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

    @NotNull
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @NotNull
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @NotNull
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
}
