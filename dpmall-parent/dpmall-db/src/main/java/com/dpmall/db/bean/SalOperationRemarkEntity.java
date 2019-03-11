package com.dpmall.db.bean;

import java.util.Date;

public class SalOperationRemarkEntity {
	
	
	/**操作记录ID*/
	public Long operateId;

	
	/**留资线索订单*/
	public String salesLeadsOrder;

	/**操作备注*/
	public String operatorRemark;

	/**未完成原因*/
	public String failReasons;

	/**操作类型*/
	public String operationType;

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getSalesLeadsOrder() {
		return salesLeadsOrder;
	}

	public void setSalesLeadsOrder(String salesLeadsOrder) {
		this.salesLeadsOrder = salesLeadsOrder;
	}

	public String getOperatorRemark() {
		return operatorRemark;
	}

	public void setOperatorRemark(String operatorRemark) {
		this.operatorRemark = operatorRemark;
	}

	public String getFailReasons() {
		return failReasons;
	}

	public void setFailReasons(String failReasons) {
		this.failReasons = failReasons;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
