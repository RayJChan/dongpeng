package com.dpmall.common;

import java.io.Serializable;

/**
 * 留资单操作状态的时间记录

 */
public class SalOrderOperationDetailModel implements Serializable{

	/**记录id**/
	private String operationId;

	/**状态名称**/
	private String status;

	/**该状态操作时间**/
	private String time;

	/**操作人**/
	private String operatorBy;

	/**操作备注
	 *
	 */
	private String remark;

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

	@Override
	public String toString() {
		return "SalOrderOperationDetailModel{" +
				"operationId='" + operationId + '\'' +
				", status='" + status + '\'' +
				", time='" + time + '\'' +
				", operatorBy='" + operatorBy + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
