package com.dpmall.db.bean;


public class SalesLeadsOrderEntity {


    /**
     * 留资订单id
     **/
    private Long id;

    /**
     * 留资单号
     **/
    private String salesLeadsOrderCode;


    /**
     * 经销商
     **/
    private String agencyId;

    /**
     * 门店
     **/
    private String storeId;

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
     * 客户姓名
     */
    private String clientName;

    /**
     * 客户昵称
     */
    private String nickName;

    /**
     * 客户电话
     **/
    private String clientTel;

    /**
     * 客户类型ID
     **/
    private String clientTypeId;

    /**
     * 客户类型
     **/
    private String clientType;

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
     * app经销商列表状态
     **/
    private String appStatusOfAgency;

    /**
     * app门店列表状态
     **/
    private String appStatusOfStore;


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
     * 经销商备注
     **/
    private String agencyRemark;

    /**
     * 门店备注
     **/
    private String storeRemark;


    /**
     * 留资来源平台
     **/
    private String source;

    /**
     * 留资线索创建时间
     **/
    private String saleLeadsTime;

    /**
     * 留资线索id
     **/
    private String saleLeadsId;


    /**
     * 派单人
     **/
    private String distributer;

    /**
     * 接单经销商
     **/
    private String agencyAccept;

    /**
     * 接单门店
     **/
    private String storeAccept;

    /**
     * 接单导购
     */
    private String orderGuide;

    /**
     * 留资订单创建时间
     **/
    private String createdTime;


    /**
     * 留资订单下派时间
     **/
    private String ordersTime;


    /**
     * 经销商接单时间
     */

    private String agencyAcceptTime;

    /**
     * 门店接单时间
     **/
    private String storeAcceptTime;

    /**
     * 订单总金额
     **/
    private Double orderPrice;


    /**
     * 支付金额(=orderPrice-promotionRules)
     **/
    private String payPrice;

    /**
     * 订单总金额
     **/
    private String discountAmount;


    /**
     * 图片
     */
    private String pictureNames;

    /**
     * 门店名称
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


    /**
     * 门店列表状态
     */
    private String amount;



    /**
     *来源平台
     */
    private String sourcePlatform;

    /**
     *修改时间
     */
    private String updateTime;

    /**
     * 订单评价
     */
    private String orderEvaluation;

    /**
     * 回访时间
     */
    private String revisitTime;

    /**
     * 流失情况
     */
    private String isLoss;

    public String getOrderEvaluation() {
        return orderEvaluation;
    }

    public void setOrderEvaluation(String orderEvaluation) {
        this.orderEvaluation = orderEvaluation;
    }

    public String getRevisitTime() {
        return revisitTime;
    }

    public void setRevisitTime(String revisitTime) {
        this.revisitTime = revisitTime;
    }

    public String getIsLoss() {
        return isLoss;
    }

    public void setIsLoss(String isLoss) {
        this.isLoss = isLoss;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(String sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

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

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
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

    public String getPictureNames() {
        return pictureNames;
    }

    public void setPictureNames(String pictureNames) {
        this.pictureNames = pictureNames;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAgencyAcceptTime() {
        return agencyAcceptTime;
    }

    public void setAgencyAcceptTime(String agencyAcceptTime) {
        this.agencyAcceptTime = agencyAcceptTime;
    }

    public String getDistributer() {
        return distributer;
    }

    public void setDistributer(String distributer) {
        this.distributer = distributer;
    }

    public String getAgencyAccept() {
        return agencyAccept;
    }

    public void setAgencyAccept(String agencyAccept) {
        this.agencyAccept = agencyAccept;
    }

    public String getStoreAccept() {
        return storeAccept;
    }

    public void setStoreAccept(String storeAccept) {
        this.storeAccept = storeAccept;
    }

    public String getOrderGuide() {
        return orderGuide;
    }

    public void setOrderGuide(String orderGuide) {
        this.orderGuide = orderGuide;
    }

    public String getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(String ordersTime) {
        this.ordersTime = ordersTime;
    }

    public String getStoreAcceptTime() {
        return storeAcceptTime;
    }

    public void setStoreAcceptTime(String storeAcceptTime) {
        this.storeAcceptTime = storeAcceptTime;
    }

    public String getAgencyRemark() {
        return agencyRemark;
    }

    public void setAgencyRemark(String agencyRemark) {
        this.agencyRemark = agencyRemark;
    }

    public String getStoreRemark() {
        return storeRemark;
    }

    public void setStoreRemark(String storeRemark) {
        this.storeRemark = storeRemark;
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

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
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

    public String getAppStatusOfAgency() {
        return appStatusOfAgency;
    }

    public void setAppStatusOfAgency(String appStatusOfAgency) {
        this.appStatusOfAgency = appStatusOfAgency;
    }

    public String getAppStatusOfStore() {
        return appStatusOfStore;
    }

    public void setAppStatusOfStore(String appStatusOfStore) {
        this.appStatusOfStore = appStatusOfStore;
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

    public String getSaleLeadsId() {
        return saleLeadsId;
    }

    public void setSaleLeadsId(String saleLeadsId) {
        this.saleLeadsId = saleLeadsId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }


}