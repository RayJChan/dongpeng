package com.dpmall.model;

import java.io.Serializable;

/**
 * 留资订单
 *
 * @author cwj
 * @since 2018-05-05
 */
public class SaleLeadsOrderModel implements Serializable {

    /**
     * 留资订单id
     **/
    private Long id;

    /**
     * 留资单号
     **/
    private String salesLeadsOrderCode;

    /**
     * 留资线索id
     */
    private String salesLeads;

    /**
     * 订单状态
     **/
    private String status;

    /**
     * 订单状态中文
     **/
    private String statusDescription;

    /**
     * 上一个订单状态
     **/
    private String lastStatus;

    /**
     * 上一个订单状态
     **/
    private String lastStatusDescription;

    /**
     * 客户姓名
     */
    private String clientName;

    /**
     * 客户电话
     **/
    private String clientTel;


    /**
     * 服务省
     **/
    private String serverRegion;

    /**
     * 服务市
     **/
    private String serverCity;

    /**
     * 服务区
     **/
    private String serverDistrict;

    /**
     * 服务地址（包含省市区）
     **/
    private String serverAddress;

    /**
     * 是否经销商
     **/
    private String isAgency;

    /**
     * 门店
     **/
    private String storeId;

    /**
     * 经销商
     **/
    private String agencyId;

    /**
     * 客户类型ID
     **/
    private String clientTypeId;

    /**
     * 客户类型
     **/
    private String clientType;


    /**
     * 装修空间
     **/
    private String decorateSpace;


    /**
     * 装修时间
     **/
    private String fitmentTime;


    /**
     * 意向风格
     **/
    private String stylePreference;

    /**
     * 预算
     **/
    private String budget;

    /**
     * 装修面积
     **/
    private String area;

    /**
     * 小区名称
     **/
    private String village;

    /**
     * 意向产品
     **/
    private String productPreference;

    /**
     * 客户利益点
     **/
    private String custBenefit;

    /**
     * 客服备注
     **/
    private String serviceRemark;


    /**
     * 留资来源平台
     **/
    private String source;

    /**
     * 留资时间
     **/
    private String saleLeadsTime;

    /**
     * 支付金额
     **/
    private String payPrice;

    /**
     * 当前列表总数量
     */
    private String totalCount;

    /**
     * 服务门店
     */
    private String storeName;

    /**
     * 经销商名称
     */
    private String agencyName;

    /**
     * 经销商列表状态
     */
    private String listStatusOfAgency;

    /**
     * 门店列表状态
     */
    private String listStatusOfStore;

    public String getListStatusOfAgency() {
        return listStatusOfAgency;
    }

    public void setListStatusOfAgency(String listStatusOfAgency) {
        this.listStatusOfAgency = listStatusOfAgency;
    }

    public String getListStatusOfStore() {
        return listStatusOfStore;
    }

    public void setListStatusOfStore(String listStatusOfStore) {
        this.listStatusOfStore = listStatusOfStore;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getDecorateSpace() {
        return decorateSpace;
    }

    public void setDecorateSpace(String decorateSpace) {
        this.decorateSpace = decorateSpace;
    }

    public String getFitmentTime() {
        return fitmentTime;
    }

    public void setFitmentTime(String fitmentTime) {
        this.fitmentTime = fitmentTime;
    }

    public String getStylePreference() {
        return stylePreference;
    }

    public void setStylePreference(String stylePreference) {
        this.stylePreference = stylePreference;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getProductPreference() {
        return productPreference;
    }

    public void setProductPreference(String productPreference) {
        this.productPreference = productPreference;
    }

    public String getCustBenefit() {
        return custBenefit;
    }

    public void setCustBenefit(String custBenefit) {
        this.custBenefit = custBenefit;
    }

    public String getServiceRemark() {
        return serviceRemark;
    }

    public void setServiceRemark(String serviceRemark) {
        this.serviceRemark = serviceRemark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSaleLeadsTime() {
        return saleLeadsTime;
    }

    public void setSaleLeadsTime(String saleLeadsTime) {
        this.saleLeadsTime = saleLeadsTime;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesLeadsOrderCode() {
        return salesLeadsOrderCode;
    }

    public void setSalesLeadsOrderCode(String salesLeadsOrderCode) {
        this.salesLeadsOrderCode = salesLeadsOrderCode;
    }

    public String getSalesLeads() {
        return salesLeads;
    }

    public void setSalesLeads(String salesLeads) {
        this.salesLeads = salesLeads;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getLastStatusDescription() {
        return lastStatusDescription;
    }

    public void setLastStatusDescription(String lastStatusDescription) {
        this.lastStatusDescription = lastStatusDescription;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientTel() {
        return clientTel;
    }

    public void setClientTel(String clientTel) {
        this.clientTel = clientTel;
    }

    public String getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(String clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getServerRegion() {
        return serverRegion;
    }

    public void setServerRegion(String serverRegion) {
        this.serverRegion = serverRegion;
    }

    public String getServerCity() {
        return serverCity;
    }

    public void setServerCity(String serverCity) {
        this.serverCity = serverCity;
    }

    public String getServerDistrict() {
        return serverDistrict;
    }

    public void setServerDistrict(String serverDistrict) {
        this.serverDistrict = serverDistrict;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(String isAgency) {
        this.isAgency = isAgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
