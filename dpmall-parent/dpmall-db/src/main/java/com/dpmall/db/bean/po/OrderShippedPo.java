package com.dpmall.db.bean.po;

import java.io.Serializable;
import java.util.List;

public class OrderShippedPo implements Serializable {
    /**经销商Id**/
    private String distributorId;

    /**发货单Id**/
    private String consignmentCode;

    /**门店Id**/
    private String storeId;

    /**拒单类型**/
    private String rejectType;

    /**拒单备注**/
    private String rejectRemark;

    /**缺货列表**/
    private List<String> stockoutList;

    /**导购员ID**/
    private String acceptorId;

    /**导购员接单备注**/
    private String acceptComment;

    /**经销商备注**/
    private String agencyRemark;

    /**订单状态**/
    private String status;

    /**发货单Id列表**/
    private List<String> consignmentCodeList;

    /**退货单Id列表**/
    private List<String> returnRequestCode;

    /**发货方式**/
    private Integer consignmentType;

    /**发货备注**/
    private String shippedRemark;

    /**物流公司编号**/
    private String logisticsCode;

    /**物流公司名称**/
    private String logisticsName;

    /**运单号**/
    private String logisticsNumber;

    /**手机型号**/
    private String moblieModel;

    /**约定发货时间**/
    private String shippedData;

    /**发货凭证**/
    private String certificate;

    /**商品类型（特权订金M2）**/
    private String productType;


    /**发货方式**/
    private String shipmentsMethod;

    public String getShipmentsMethod() {
        return shipmentsMethod;
    }

    public void setShipmentsMethod(String shipmentsMethod) {
        this.shipmentsMethod = shipmentsMethod;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getConsignmentCode() {
        return consignmentCode;
    }

    public void setConsignmentCode(String consignmentCode) {
        this.consignmentCode = consignmentCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRejectType() {
        return rejectType;
    }

    public void setRejectType(String rejectType) {
        this.rejectType = rejectType;
    }

    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public List<String> getStockoutList() {
        return stockoutList;
    }

    public void setStockoutList(List<String> stockoutList) {
        this.stockoutList = stockoutList;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getAcceptComment() {
        return acceptComment;
    }

    public void setAcceptComment(String acceptComment) {
        this.acceptComment = acceptComment;
    }

    public String getAgencyRemark() {
        return agencyRemark;
    }

    public void setAgencyRemark(String agencyRemark) {
        this.agencyRemark = agencyRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getConsignmentCodeList() {
        return consignmentCodeList;
    }

    public void setConsignmentCodeList(List<String> consignmentCodeList) {
        this.consignmentCodeList = consignmentCodeList;
    }

    public List<String> getReturnRequestCode() {
        return returnRequestCode;
    }

    public void setReturnRequestCode(List<String> returnRequestCode) {
        this.returnRequestCode = returnRequestCode;
    }

    public Integer getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(Integer consignmentType) {
        this.consignmentType = consignmentType;
    }

    public String getShippedRemark() {
        return shippedRemark;
    }

    public void setShippedRemark(String shippedRemark) {
        this.shippedRemark = shippedRemark;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getMoblieModel() {
        return moblieModel;
    }

    public void setMoblieModel(String moblieModel) {
        this.moblieModel = moblieModel;
    }

    public String getShippedData() {
        return shippedData;
    }

    public void setShippedData(String shippedData) {
        this.shippedData = shippedData;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
