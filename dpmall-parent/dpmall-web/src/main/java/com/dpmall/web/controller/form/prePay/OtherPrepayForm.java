package com.dpmall.web.controller.form.prePay;

import java.util.List;

public class OtherPrepayForm {


    /**
     * 特权订金id
     */
    private String prePayId;

    /**
     * 状态名称
     */
    private String statusName;


    /**
     * 操作人
     */
    private String operatorBy;

    /**
     * 门店id
     */
    private String storeId ;

    /**
     * 备注
     */
    private String remark ;

    /**
     * 未成交原因id
     */
    private String failTypeId ;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFailTypeId() {
        return failTypeId;
    }

    public void setFailTypeId(String failTypeId) {
        this.failTypeId = failTypeId;
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

    public String getOperatorBy() {
        return operatorBy;
    }

    public void setOperatorBy(String operatorBy) {
        this.operatorBy = operatorBy;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
