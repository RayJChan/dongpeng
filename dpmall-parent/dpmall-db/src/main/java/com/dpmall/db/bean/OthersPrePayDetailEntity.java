package com.dpmall.db.bean;

/***
 * 天猫的特权订金  详情
 */
public class OthersPrePayDetailEntity {

    /**
     * 特权订金id
     **/
    private String prePayId;

    /**
     * 特权订金号
     **/
    private String prePayCode;

    /**
     * 特权订金名称
     **/
    private String prePayName;

//    /**
//     * 抵扣金额
//     **/
//    private String discountPrice;

    /**
     * 条件
     **/
    private String condition;

    /**
     * 客户姓名
     **/
    private String customerName;

    /**
     * 客户电话
     **/
    private String phone;

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
     * 服务地址
     **/
    private String serviceAddress;

    /**
     * 有效时间
     **/
    private String effectiveTime;

    /**
     * 推荐门店
     **/
    private String suggestStoreName;

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
     * 特权定金状态
     **/
    private String prepayStatus;

    /**
     * 订单状态（原状态）
     **/
    private String orderStatus;


    /**
     * 接单时间
     **/
    private String acceptTime;

    /**
     * 下单时间
     **/
    private String orderTime;

    /**
     * 下单用户名
     **/
    private String buyernick;

    /**
     * 订单平台
     **/
    private String orderPlatform;

    /**
     * 是否核销
     **/
    private String isWriteOff;

    /**特权订金购买金额（下单金额）**/
    private String prePayPrice;

    /**
     * 经销商列表状态
     */
    private String listStatusOfAgency;

    /**
     * 门店列表状态
     */
    private String listStatusOfStore;


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
     * 图片
     */
    private String pictureNames;

    /**
     * 订单总金额
     **/
    private Double orderPrice;


    /**
     * 支付金额(=orderPrice-promotionRules)
     **/
    private String payPrice;

    /**
     * 优惠金额
     **/
    private String discountPrice;

    /**
     * 上一个订单状态
     **/
    private String lastPrePayStatus;


    /**
     * 是否经销商发货
     **/
    private String isAgency;



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

    public String getPrePayPrice() {
        return prePayPrice;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(String ordersTime) {
        this.ordersTime = ordersTime;
    }

    public String getAgencyAcceptTime() {
        return agencyAcceptTime;
    }

    public void setAgencyAcceptTime(String agencyAcceptTime) {
        this.agencyAcceptTime = agencyAcceptTime;
    }

    public String getStoreAcceptTime() {
        return storeAcceptTime;
    }

    public void setStoreAcceptTime(String storeAcceptTime) {
        this.storeAcceptTime = storeAcceptTime;
    }

    public String getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(String isAgency) {
        this.isAgency = isAgency;
    }

    public String getLastPrePayStatus() {
        return lastPrePayStatus;
    }

    public void setLastPrePayStatus(String lastPrePayStatus) {
        this.lastPrePayStatus = lastPrePayStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public void setPrePayPrice(String prePayPrice) {
        this.prePayPrice = prePayPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
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

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public String getPrePayCode() {
        return prePayCode;
    }

    public void setPrePayCode(String prePayCode) {
        this.prePayCode = prePayCode;
    }

    public String getPrePayName() {
        return prePayName;
    }

    public void setPrePayName(String prePayName) {
        this.prePayName = prePayName;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getSuggestStoreName() {
        return suggestStoreName;
    }

    public void setSuggestStoreName(String suggestStoreName) {
        this.suggestStoreName = suggestStoreName;
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

    public String getPrepayStatus() {
        return prepayStatus;
    }

    public void setPrepayStatus(String prepayStatus) {
        this.prepayStatus = prepayStatus;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getBuyernick() {
        return buyernick;
    }

    public void setBuyernick(String buyernick) {
        this.buyernick = buyernick;
    }

    public String getOrderPlatform() {
        return orderPlatform;
    }

    public void setOrderPlatform(String orderPlatform) {
        this.orderPlatform = orderPlatform;
    }

    public String getIsWriteOff() {
        return isWriteOff;
    }

    public void setIsWriteOff(String isWriteOff) {
        this.isWriteOff = isWriteOff;
    }

}
