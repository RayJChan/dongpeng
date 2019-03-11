package com.dpmall.db.bean;

/***
 * 其他特权订金  列表信息
 */
public class OthersPrePayEntity  {

    /**
     * 特权订金id
     **/
    private String prePayId;

    /**
     * 特权订金号
     **/
    private String prePayCode;

    /**
     * 特权订金名称(订金权益)
     **/
    private String prePayName;

    /**
     * 客户姓名
     **/
    private String customerName;

    /**
     * 客户电话
     **/
    private String phone;

    /**
     * 门店id
     **/
    private String storeId;

    /**
     * 推荐门店
     **/
    private String serviceStore;

    /**
     * 接单时间
     **/
    private String acceptTime;

    /**
     * 特权定金状态
     **/
    private String prepayStatus;

    /**
     * 订单状态（原状态）
     **/
    private String orderStatus;


    /**
     * 列表状态
     **/
    private String listStatus;

    /**
     * 订单平台
     **/
    private String orderPlatform;

    /**
     * 是否经销商自己发货
     **/
    private String isAgency;

    /**
     * 经销商名称
     **/
    private String agencyName;


    /**
     * 特权订金状态(上一个状态)
     **/
    private String lastPrepayStatus;

    /**
     * 订金金额
     **/
    private String prePayPrice;

    /**
     * 支付金额
     **/
    private String payPrice;

    /**
     * 核销码
     **/
    private String pridePositCode;

    /**
     * 参考单号
     **/
    private String orderCode;

    public String getPridePositCode() {
        return pridePositCode;
    }

    public void setPridePositCode(String pridePositCode) {
        this.pridePositCode = pridePositCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPrePayPrice() {
        return prePayPrice;
    }

    public void setPrePayPrice(String prePayPrice) {
        this.prePayPrice = prePayPrice;
    }

    public String getLastPrepayStatus() {
        return lastPrepayStatus;
    }

    public void setLastPrepayStatus(String lastPrepayStatus) {
        this.lastPrepayStatus = lastPrepayStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(String isAgency) {
        this.isAgency = isAgency;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
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

    public String getServiceStore() {
        return serviceStore;
    }

    public void setServiceStore(String serviceStore) {
        this.serviceStore = serviceStore;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }


    public String getPrepayStatus() {
        return prepayStatus;
    }

    public void setPrepayStatus(String prepayStatus) {
        this.prepayStatus = prepayStatus;
    }

    public String getListStatus() {
        return listStatus;
    }

    public void setListStatus(String listStatus) {
        this.listStatus = listStatus;
    }

    public String getOrderPlatform() {
        return orderPlatform;
    }

    public void setOrderPlatform(String orderPlatform) {
        this.orderPlatform = orderPlatform;
    }
}
