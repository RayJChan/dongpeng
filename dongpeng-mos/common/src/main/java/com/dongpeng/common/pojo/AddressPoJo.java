package com.dongpeng.common.pojo;


import com.dongpeng.common.entity.DataEntity;

import java.io.Serializable;

/**
 * 经销商负责客户显示信息
 */
public class AddressPoJo  extends DataEntity<AddressPoJo> implements Serializable {
    private  Long id;
    /**
     * 省编号
     */
    private String regionId;
    /**
     * 省
     */
    private String region;
    /**
     * 市编号
     */
    private String cityId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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
}
