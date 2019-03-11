package com.dpmall.db.bean.po;


import java.util.Date;

public class SalesLeadsOrderPo {
    /**
     * 留资订单ID
     */
    private Long id;

    /**
     * 留资单状态
     **/
    private String saleLeadsOrderStatus;

    /**
     * 留资单状态中文描述
     **/
    private String saleLeadsOrderStatusName;

    /**
     * 拒单时间
     **/
    private Date refuseTime;

    /**
     * 拒单类型
     **/
    private String refuseType;

    /**
     * 拒单备注
     **/
    private String refuseRemark;

    /**
     * 接单时间
     **/
    private Date acceptTime;

    /**
     * 接单经销商
     **/
    private String agencyAccept;

    /**
     * 经销商接单时间
     **/
    private Date agencyAcceptTime;

    /**
     * 推荐门店
     **/
    private String suggestStore;

    /**
     * 是否经销商
     **/
    private String isAgency;

    /**
     * 门店列表状态
     **/
    private String appStatusofStore;

    /**
     * 经销商列表状态
     **/
    private String appStatusofAgency;

    /**
     * 经销商备注
     **/
    private String agencyRemark;

    /**
     * 接单员
     **/
    private String orderGuide;

    /**
     * 店员备注
     **/
    private String storeAcceptRemark;

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
     * 上一个状态
     */
    private String lastStatus;

    /**
     * 图片
     */
    private String pictureNames;

    /**
     * 支付金额
     */
    private String payPrice;

    /**
     * 优惠金额
     */
    private String discountAmount;

    /**
     * 未完成的类型
     */
    private String failType;

    /**
     * 未完成原因
     */
    private String failReason;

    /**
     * 完成时间
     */
    private Date finishedTime;

    /**
     * 总金额
     */
    private String amount;
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

    public String getRevisitTime() {
        return revisitTime;
    }

    public void setRevisitTime(String revisitTime) {
        this.revisitTime = revisitTime;
    }

    public String getOrderEvaluation() {
        return orderEvaluation;
    }

    public void setOrderEvaluation(String orderEvaluation) {
        this.orderEvaluation = orderEvaluation;
    }


    public String getIsLoss() {
        return isLoss;
    }

    public void setIsLoss(String isLoss) {
        this.isLoss = isLoss;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getFailType() {
        return failType;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPictureNames() {
        return pictureNames;
    }

    public void setPictureNames(String pictureNames) {
        this.pictureNames = pictureNames;
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
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

    public String getAgencyAccept() {
        return agencyAccept;
    }

    public void setAgencyAccept(String agencyAccept) {
        this.agencyAccept = agencyAccept;
    }

    public String getOrderGuide() {
        return orderGuide;
    }

    public void setOrderGuide(String orderGuide) {
        this.orderGuide = orderGuide;
    }

    public String getStoreAcceptRemark() {
        return storeAcceptRemark;
    }

    public void setStoreAcceptRemark(String storeAcceptRemark) {
        this.storeAcceptRemark = storeAcceptRemark;
    }

    public String getAgencyRemark() {
        return agencyRemark;
    }

    public void setAgencyRemark(String agencyRemark) {
        this.agencyRemark = agencyRemark;
    }

    public String getAppStatusofAgency() {
        return appStatusofAgency;
    }

    public void setAppStatusofAgency(String appStatusofAgency) {
        this.appStatusofAgency = appStatusofAgency;
    }

    public String getAppStatusofStore() {
        return appStatusofStore;
    }

    public void setAppStatusofStore(String appStatusofStore) {
        this.appStatusofStore = appStatusofStore;
    }

    public String getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(String isAgency) {
        this.isAgency = isAgency;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getAgencyAcceptTime() {
        return agencyAcceptTime;
    }

    public void setAgencyAcceptTime(Date agencyAcceptTime) {
        this.agencyAcceptTime = agencyAcceptTime;
    }

    public String getSuggestStore() {
        return suggestStore;
    }

    public void setSuggestStore(String suggestStore) {
        this.suggestStore = suggestStore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaleLeadsOrderStatus() {
        return saleLeadsOrderStatus;
    }

    public String getSaleLeadsOrderStatusName() {
        return saleLeadsOrderStatusName;
    }

    public void setSaleLeadsOrderStatusName(String saleLeadsOrderStatusName) {
        this.saleLeadsOrderStatusName = saleLeadsOrderStatusName;
    }

    public void setSaleLeadsOrderStatus(String saleLeadsOrderStatus) {
        this.saleLeadsOrderStatus = saleLeadsOrderStatus;
    }

    public Date getRefuseTime() {
        return refuseTime;
    }

    public void setRefuseTime(Date refuseTime) {
        this.refuseTime = refuseTime;
    }

    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }
}