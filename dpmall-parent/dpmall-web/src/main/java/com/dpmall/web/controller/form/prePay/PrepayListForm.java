package com.dpmall.web.controller.form.prePay;

public class PrepayListForm {


    /**
     * 经销商id
     */
    private String agencyId ;

    /**
     * 门店id
     */
    private String storeId ;

    /**状态**/
    private String status;

    /**搜索**/
    private String search;

    /**特权订金id**/
    private String prePayId;

    private int pageNum;

    private int pageSize;

    /**订单号**/
    public String orderId;

    /**客户姓名**/
    public String clientName;

    /**客户电话**/
    public String clientTel;

    /*** 回访时间**/
    public String staRevisitTime;

    /*** 至**/
    public String endRevisitTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getStaRevisitTime() {
        return staRevisitTime;
    }

    public void setStaRevisitTime(String staRevisitTime) {
        this.staRevisitTime = staRevisitTime;
    }

    public String getEndRevisitTime() {
        return endRevisitTime;
    }

    public void setEndRevisitTime(String endRevisitTime) {
        this.endRevisitTime = endRevisitTime;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
