package com.dpmall.db.bean;


public class StoreOfSalEntity {


    /**
     * 门店名称
     **/
    private String storeName;

    /**
     *门店地址
     **/
    private String storeAddress;

    /**
     * 利益点
     **/
    private String custbenefit;

    /**
     * 电话号码
     **/
    private String storePhone;

    /**
     * 客户电话号码
     **/
    private String customerPhone;

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getCustbenefit() {
        return custbenefit;
    }

    public void setCustbenefit(String custbenefit) {
        this.custbenefit = custbenefit;
    }


    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }
}