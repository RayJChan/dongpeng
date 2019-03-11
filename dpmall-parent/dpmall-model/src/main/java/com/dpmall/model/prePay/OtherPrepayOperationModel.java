package com.dpmall.model.prePay;

import com.dpmall.model.SalOrderOperationDetailModel;

import java.io.Serializable;
import java.util.List;


/**
 * 特权订金操作记录
 *
 * @author cwj
 * @since 2018-07-21
 */
public class OtherPrepayOperationModel implements Serializable {


    /**
     * 部分操作记录
     **/
    private List<String> operationRecord;

    /**
     * 状态与更新时间
     */
    private List<OtherPrePayOperationDetailModel> statusAndUpStringTime;


    public List<String> getOperationRecord() {
        return operationRecord;
    }

    public void setOperationRecord(List<String> operationRecord) {
        this.operationRecord = operationRecord;
    }

    public List<OtherPrePayOperationDetailModel> getStatusAndUpStringTime() {
        return statusAndUpStringTime;
    }

    public void setStatusAndUpStringTime(List<OtherPrePayOperationDetailModel> statusAndUpStringTime) {
        this.statusAndUpStringTime = statusAndUpStringTime;
    }
}
