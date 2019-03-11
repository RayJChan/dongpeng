package com.dpmall.db.bean;

import java.util.Date;

/**
 * 留资单操作状态的时间记录

 */
public class SalOrderOperationDetailEntity {

	/**状态id**/
	private String operationId;

	/**状态名称**/
	private String status;

	/**该状态操作时间**/
	private String time;

	/**操作人**/
	private String operatorBy;

	/**
	 * 操作备注
	 */
	private String remark;

	/***
	 * 留资单号
	 */
	private String salOrderCode;

	public String getSalOrderCode() {
		return salOrderCode;
	}

	public void setSalOrderCode(String salOrderCode) {
		this.salOrderCode = salOrderCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperatorBy() {
		return operatorBy;
	}

	public void setOperatorBy(String operatorBy) {
		this.operatorBy = operatorBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
}
