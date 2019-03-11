package com.dpmall.db.bean.po;

public class PrePayOperationPo {

	/**操作id*/
	private String id;
	
	/**特权订金ID**/
	private String prePayId;
	
	/**操作时间**/
	private String operationTime;
	
	/**操作人*/
	private String operator;
	
	/**操作人姓名*/
	private String operatorName;
	
	/**操作类型*/
	private String operationType;
	
	/**操作类型中文名称**/
	private String operationTypeName;

	/**操作类型中文描述**/
	private String operationTypeDesc;

	/**操作备注**/
	private String operatorRemark;

	/**经销商编号**/
	private String agencyCode;

	/**门店编号**/
	private String acceptedCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrePayId() {
		return prePayId;
	}

	public void setPrePayId(String prePayId) {
		this.prePayId = prePayId;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public String getOperationTypeDesc() {
		return operationTypeDesc;
	}

	public void setOperationTypeDesc(String operationTypeDesc) {
		this.operationTypeDesc = operationTypeDesc;
	}

	public String getOperatorRemark() {
		return operatorRemark;
	}

	public void setOperatorRemark(String operatorRemark) {
		this.operatorRemark = operatorRemark;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAcceptedCode() {
		return acceptedCode;
	}

	public void setAcceptedCode(String acceptedCode) {
		this.acceptedCode = acceptedCode;
	}
}
