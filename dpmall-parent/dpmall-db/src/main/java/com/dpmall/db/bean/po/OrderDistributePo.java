package com.dpmall.db.bean.po;

import java.util.Date;

public class OrderDistributePo {


    /**销售单id**/
    private String id;

    /**实物类订单Id**/
    private String orderCode;

    /**商店Id**/
    private String storeId;

    /**拒单类型**/
    private String rejectType;

    /**拒单备注**/
    private String rejectRemark;

    /**订单下派时间**/
    private String distributeTime;

    /**客户姓名**/
    private String clientName;

    /**客户电话**/
    private String clientTel;

    /**导购员ID**/
    private String acceptorId;

    /**开始页码**/
    private Integer startNum;

    private Integer pageSize;

    /**导购员接单备注**/
    private String acceptComment;

    /**订单状态**/
    private String status;

    /**经销商备注**/
    private String remark;

    /**发货单号**/
    private String consignmentId;

    /**查询数据**/
    private String search;

    /**登录人id**/
    private String operatorBy;

    /**手机型号**/
    private String moblieModel;

    /**数据列表**/
//    private List<AppOrderForm> appOrderFormList;

    /**商品类型**/
    private String productType;

    /**经销商状态**/
    private String agencyO2OStatus;


    /**门店状态**/
    private String storeO2OStatus;

    /**oms状态**/
    private String omsStatus;

    /**接单时间**/
    private Date acceptTime;

    /**是否经销商操作**/
    private String isDeliverSelf;


    public String getIsDeliverSelf() {
        return isDeliverSelf;
    }

    public void setIsDeliverSelf(String isDeliverSelf) {
        this.isDeliverSelf = isDeliverSelf;
    }

    public String getAgencyO2OStatus() {
        return agencyO2OStatus;
    }

    public void setAgencyO2OStatus(String agencyO2OStatus) {
        this.agencyO2OStatus = agencyO2OStatus;
    }

    public String getStoreO2OStatus() {
        return storeO2OStatus;
    }

    public void setStoreO2OStatus(String storeO2OStatus) {
        this.storeO2OStatus = storeO2OStatus;
    }

    public String getOmsStatus() {
        return omsStatus;
    }

    public void setOmsStatus(String omsStatus) {
        this.omsStatus = omsStatus;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(String distributeTime) {
        this.distributeTime = distributeTime;
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

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getAcceptComment() {
        return acceptComment;
    }

    public void setAcceptComment(String acceptComment) {
        this.acceptComment = acceptComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOperatorBy() {
        return operatorBy;
    }

    public void setOperatorBy(String operatorBy) {
        this.operatorBy = operatorBy;
    }

    public String getMoblieModel() {
        return moblieModel;
    }

    public void setMoblieModel(String moblieModel) {
        this.moblieModel = moblieModel;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
