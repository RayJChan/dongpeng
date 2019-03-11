package com.dongpeng.common.vo;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 省市区地址返回信息
 */
public class AddressVo extends DataEntity<AddressVo> implements Serializable {

    private  Long id;
    /**
     * 省编号
     */
    private Long provinceId;
    /**
     * 省
     */
    private String province;
    /**
     * 市编号
     */
    private Long cityId;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;

    /**
     * 导出总表
     **/
    private static final int GROUPS_INDEX_ALL = 1;
    public AddressVo(){
        super();
        this.dataTableName="省市区导出";
    }


    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @ExcelField(title = "省", sort = 10, groups = {GROUPS_INDEX_ALL})
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @ExcelField(title = "市", sort = 20, groups = {GROUPS_INDEX_ALL})
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ExcelField(title = "区", sort = 30, groups = {GROUPS_INDEX_ALL})
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public void preInsert() {
        super.preInsert();

    }

    @Override
    public void preUpdate() {
        super.preUpdate();

    }
}
