package com.dpmall.web.controller.form.prePay;

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
}
