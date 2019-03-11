package com.dpmall.model;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 销售线索操作记录
 *
 * @author crown
 * @since 2017-07-21
 */
public class SalesLeadsOrderOperationModel implements Serializable {


    /**
     * 部分操作记录
     **/
    private List<String> operationRecord;

    /**
     * 状态与更新时间
     */
    private List<SalOrderOperationDetailModel> statusAndUpStringTime;


    public List<String> getOperationRecord() {
        return operationRecord;
    }

    public void setOperationRecord(List<String> operationRecord) {
        this.operationRecord = operationRecord;
    }

    public List<SalOrderOperationDetailModel> getStatusAndUpStringTime() {
        return statusAndUpStringTime;
    }

    public void setStatusAndUpStringTime(List<SalOrderOperationDetailModel> statusAndUpStringTime) {
        this.statusAndUpStringTime = statusAndUpStringTime;
    }
}
