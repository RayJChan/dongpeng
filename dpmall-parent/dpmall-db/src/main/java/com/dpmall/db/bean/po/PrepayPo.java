package com.dpmall.db.bean.po;


import java.util.Date;

public class PrepayPo {

    /**
     * 特权订金id
     **/
    private String prePayId;

    /**
     * 经销商列表状态
     **/
    private String agencyListStatus;

    /**
     * 门店列表状态
     **/
    private String storeListStatus;

    /**
     * 订单状态（原状态）
     **/
    private String orderStatus;

    /**
     * 特权订金状态
     **/
    private String prepayStatus;

    /**
     * 特权订金状态(中文)
     */
    private String prepayStatusName;

    /**
     * 特权订金状态(上一个状态)
     **/
    private String lastPrepayStatus;


    /**
     * 推荐门店
     **/
    private String suggestStore;

    /**
     * 是否自己仓库发货
     **/
    private String isDeliverySelf;

    /**
     * 经销商接单人
     **/
    private String agencyAccept;

    /**
     * 经销商接单时间
     **/
    private Date agencyAcceptTime;

    /**
     * 门店接单时间
     **/
    private Date acceptTime;

    /**
     * 接单时间(天猫)
     **/
    private String tmallAcceptTime;

    /**
     * 经销商备注
     **/
    private String agencyRemark;

    /**
     * 门店备注
     **/
    private String storeRemark;

    /**
     * 客服备注
     **/
    private String serverRemark;


    /**
     * 客户姓名
     **/
    private String customerName;

    /**
     * 客户电话
     **/
    private String phone;

    /**
     * 客户类型
     **/
    private String clientType;

    /**
     * 装修空间
     **/
    private String fitmentSpace;


    /**
     * 装修时间
     **/
    private String fitmentTime;


    /**
     * 意向风格
     **/
    private String stylePreference;

    /**
     * 消费预算
     **/
    private String consumptionBudget;

    /**
     * 装修面积
     **/
    private String fitmentArea;

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
     * 地址id
     **/
    private String addressId;

    /**
     * 省
     **/
    private String region;

    /**
     * 市
     **/
    private String city;

    /**
     * 区
     **/
    private String district;

    /**
     * 详细地址
     **/
    private String streetName;



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
    private String discountPrice;

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
    private String finishedTime;

    /**
     * 总金额
     */
    private String amount;

    /**
     * 订单评价
     */
    private String orderEvaluation;

    /**
     * 流失情况
     */
    private String isLoss;

    /**
     * 回访时间
     */
    private String revisitTime;

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

    public String getRevisitTime() {
        return revisitTime;
    }

    public void setRevisitTime(String revisitTime) {
        this.revisitTime = revisitTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getPrepayStatusName() {
        return prepayStatusName;
    }

    public void setPrepayStatusName(String prepayStatusName) {
        this.prepayStatusName = prepayStatusName;
    }

    public String getTmallAcceptTime() {
        return tmallAcceptTime;
    }

    public void setTmallAcceptTime(String tmallAcceptTime) {
        this.tmallAcceptTime = tmallAcceptTime;
    }

    public String getAgencyAccept() {
        return agencyAccept;
    }

    public void setAgencyAccept(String agencyAccept) {
        this.agencyAccept = agencyAccept;
    }

    public Date getAgencyAcceptTime() {
        return agencyAcceptTime;
    }

    public void setAgencyAcceptTime(Date agencyAcceptTime) {
        this.agencyAcceptTime = agencyAcceptTime;
    }

    public String getLastPrepayStatus() {
        return lastPrepayStatus;
    }

    public void setLastPrepayStatus(String lastPrepayStatus) {
        this.lastPrepayStatus = lastPrepayStatus;
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

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
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

    public String getPrepayStatus() {
        return prepayStatus;
    }

    public void setPrepayStatus(String prepayStatus) {
        this.prepayStatus = prepayStatus;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getFitmentSpace() {
        return fitmentSpace;
    }

    public void setFitmentSpace(String fitmentSpace) {
        this.fitmentSpace = fitmentSpace;
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

    public String getConsumptionBudget() {
        return consumptionBudget;
    }

    public void setConsumptionBudget(String consumptionBudget) {
        this.consumptionBudget = consumptionBudget;
    }

    public String getFitmentArea() {
        return fitmentArea;
    }

    public void setFitmentArea(String fitmentArea) {
        this.fitmentArea = fitmentArea;
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

    public String getServerRemark() {
        return serverRemark;
    }

    public void setServerRemark(String serverRemark) {
        this.serverRemark = serverRemark;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getIsDeliverySelf() {
        return isDeliverySelf;
    }

    public void setIsDeliverySelf(String isDeliverySelf) {
        this.isDeliverySelf = isDeliverySelf;
    }

    public String getSuggestStore() {
        return suggestStore;
    }

    public void setSuggestStore(String suggestStore) {
        this.suggestStore = suggestStore;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public String getAgencyListStatus() {
        return agencyListStatus;
    }

    public void setAgencyListStatus(String agencyListStatus) {
        this.agencyListStatus = agencyListStatus;
    }

    public String getStoreListStatus() {
        return storeListStatus;
    }

    public void setStoreListStatus(String storeListStatus) {
        this.storeListStatus = storeListStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}