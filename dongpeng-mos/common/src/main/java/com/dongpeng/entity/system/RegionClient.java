package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 区和客户关系实体类
 */
public class RegionClient extends DataEntity<RegionClient> {

    @DataName("id")
    private Long id;

    @DataName("区编号")
    private Long districtId;

    @DataName("客户编号")
    private Long clientId;

    private Integer orderType;

    @DataName("接单比例")
    private Integer receiptProportion;

    @DataName("省")
    private String province ;

    @DataName("市")
    private String city;

    @DataName("区")
    private String district;

    /**
     * 导出使用
     */
    @DataName("订单类型")
    private String orderTypeExport;

    private Long districtParentId;

    public Long getDistrictParentId() {
        return districtParentId;
    }

    public void setDistrictParentId(Long districtParentId) {
        this.districtParentId = districtParentId;
    }

    /** 导出总表 **/
    private static final int GROUPS_INDEX_ALL=1;
    public RegionClient(){
        super();
        this.dataTableName="现货派单客户";
    }

    public Integer getReceiptProportion() {
        return receiptProportion;
    }

    public void setReceiptProportion(Integer receiptProportion) {
        this.receiptProportion = receiptProportion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @ExcelField(title = "省",sort = 0,groups = {GROUPS_INDEX_ALL})
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @ExcelField(title = "市",sort = 10,groups = {GROUPS_INDEX_ALL})
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ExcelField(title = "区",sort = 20,groups = {GROUPS_INDEX_ALL})
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @ExcelField(title = "客户编号",sort = 30,groups = {GROUPS_INDEX_ALL})
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @ExcelField(title = "订单类型",sort = 40,groups = {GROUPS_INDEX_ALL})
    public String getOrderTypeExport() {
        return orderTypeExport;
    }

    public void setOrderTypeExport(String orderTypeExport) {
        this.orderTypeExport = orderTypeExport;
    }

    @Override
    public void preInsert() {
        super.preInsert();
    }

}