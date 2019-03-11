package com.dpmall.db.bean;

import java.io.Serializable;

/**
 * 天猫订单商品
 */
public class TmallOrderProductModeIn implements Serializable {

    /**数据id**/
    private Long id;

    /**特权订单id**/
    private Long tmallOrderId;

    /**商品code**/
    private String productCode;

    /**商品类别**/
    private String productCatetory;

    /**数量**/
    private String quantity;

    /**总金额**/
    private String amount;

    /**商品名称**/
    private String productDescribe;

    /**单位**/
    private String unit;

    /**单价**/
    private String unitPrice;

    /**商品id**/
    private String shangpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTmallOrderId() {
        return tmallOrderId;
    }

    public void setTmallOrderId(Long tmallOrderId) {
        this.tmallOrderId = tmallOrderId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCatetory() {
        return productCatetory;
    }

    public void setProductCatetory(String productCatetory) {
        this.productCatetory = productCatetory;
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

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getShangpId() {
        return shangpId;
    }

    public void setShangpId(String shangpId) {
        this.shangpId = shangpId;
    }
}
