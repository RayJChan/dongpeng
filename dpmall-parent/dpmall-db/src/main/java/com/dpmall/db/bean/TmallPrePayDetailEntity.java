package com.dpmall.db.bean;

import java.io.Serializable;

/***
 * 天猫的特权订金  详情
 */
public class TmallPrePayDetailEntity  {

    /**特权订金id**/
    private String prePayId;

    /**特权订金号**/
    private String prePayCode;

    /**特权订金名称**/
    private String prePayName;

    /**抵扣金额**/
    private String discountPrice;

    /**条件**/
    private String condition;

    /**客户姓名**/
    private String customerName;

    /**客户电话**/
    private String phone;

    /**服务地址**/
    private String serviceAddress;

    /**有效时间**/
    private String effectiveTime;

    /**推荐门店**/
    private String suggestStoreName;

    /**经销商备注**/
    private String agencyRemark;

    /**门店备注**/
    private String storeRemark;

    /**状态**/
    private String prepayStatus;

    /**接单时间**/
    private String acceptTime;

    /**核销码**/
    private String writeoffCode;

    /**是否核销**/
    private String isWriteOff;

    /**
     * 经销商列表状态
     */
    private String listStatusOfAgency;

    /**
     * 门店列表状态
     */
    private String listStatusOfStore;

    /**
     * 是否经销商
     */
    private String isAgency;
    /**
     * 经销商id
     */
    private String agencyId;

    /**
     * 第三方订单状态
     */
    private String sfStatus;

    /***
     * 客服备注
     */
    private String serviceRemark;

    public String getServiceRemark() {
        return serviceRemark;
    }

    public void setServiceRemark(String serviceRemark) {
        this.serviceRemark = serviceRemark;
    }

    public String getSfStatus() {
        return sfStatus;
    }

    public void setSfStatus(String sfStatus) {
        this.sfStatus = sfStatus;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(String isAgency) {
        this.isAgency = isAgency;
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

    public String getWriteoffCode() {
        return writeoffCode;
    }

    public void setWriteoffCode(String writeoffCode) {
        this.writeoffCode = writeoffCode;
    }

    public String getIsWriteOff() {
        return isWriteOff;
    }

    public void setIsWriteOff(String isWriteOff) {
        this.isWriteOff = isWriteOff;
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

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
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
}
