package com.dpmall.model.prePay;

import java.io.Serializable;

/**
 * 特权订金操作状态的时间记录

 */
public class OtherPrePayOperationDetailModel implements Serializable{

	/**记录id**/
	private String operationId;

	/**状态名称**/
	private String status;

	/**该状态操作时间**/
	private String time;

	/**操作人**/
	private String operator;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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
