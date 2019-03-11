package com.dpmall.db.bean.po;


import java.util.Date;

public class SalOrderItemsPo {

    /**留资订单id**/
    private String salesLeadsOrderId;

    /**订单ID*/
    private String orderItemId;

    /**商品编码*/
    private String productCode;

    /**商品名称*/
    private String productName;

    /**商品品类**/
    private String category;

    /**单位**/
    private String unit;

    /**单位**/
    private String quantity;

    /**商品金额**/
    private String amount;

    /**是否作废**/
    private String isDelete;

    /**单位**/
    private String unitPrice;

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getSalesLeadsOrderId() {
        return salesLeadsOrderId;
    }

    public void setSalesLeadsOrderId(String salesLeadsOrderId) {
        this.salesLeadsOrderId = salesLeadsOrderId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}