package com.dpmall.web.controller.form.prePay;

import java.util.Date;
import java.util.List;

public class TmallPrepayForm {

    /**
     * 特权订金ids
     */
    private List<String> prePayIdList;

    /**
     * 特权订金id
     */
    private String prePayId;

    /**
     * 门店id
     */
    private String storeId ;


    /**
     * 经销商备注
     */
    private String agencyRemark ;


    /**
     * 门店备注
     */
    private String storeRemark ;


    /**
     * 操作人
     */
    private String operatorBy ;


    /**
     * 状态名称
     */
    private String statusName ;


    /**
    * 未完成类型
    */
    private String failTypeId;

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

    public String getAgencyRemark() {
        return agencyRemark;
    }

    public String getStoreRemark() {
        return storeRemark;
    }

    public void setStoreRemark(String storeRemark) {
        this.storeRemark = storeRemark;
    }

    public String getOperatorBy() {
        return operatorBy;
    }

    public void setOperatorBy(String operatorBy) {
        this.operatorBy = operatorBy;
    }

    public void setAgencyRemark(String agencyRemark) {
        this.agencyRemark = agencyRemark;


    }

    public List<String> getPrePayIdList() {
        return prePayIdList;
    }

    public void setPrePayIdList(List<String> prePayIdList) {
        this.prePayIdList = prePayIdList;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getFailTypeId() {
        return failTypeId;
    }

    public void setFailTypeId(String failTypeId) {
        this.failTypeId = failTypeId;
    }
}
