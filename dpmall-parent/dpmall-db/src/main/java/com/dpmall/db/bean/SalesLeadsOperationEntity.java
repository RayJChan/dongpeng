package com.dpmall.db.bean;

import java.util.Date;

public class SalesLeadsOperationEntity {
	
	
	/**操作记录ID*/
	public Long operateId;
	
	/**操作人*/
	public String operatorBy;
	
	/**操作时间*/
	public Date operatorDate;
	
	/**操作描述*/
	public String operatorDesc;
	
	/**留资线索订单*/
	public String salesLeadsOrder;
	
	/**操作类型*/
	public String operatorType;

	/**操作备注*/
	public String operatorRemark;

	/**操作人姓名*/
	public String operatorByName;

	/**操作时间*/
	public String operatorTime;

	/**操作时间save*/
	public Date operatorTimeSave;

	/**经销商编码**/
	public String agencyCode;

	/**门店名称**/
	public String storeName;

	/**订单编号**/
	public String salOrderCode;

	public Date getOperatorTimeSave() {
		return operatorTimeSave;
	}

	public void setOperatorTimeSave(Date operatorTimeSave) {
		this.operatorTimeSave = operatorTimeSave;
	}

	public String getSalOrderCode() {
		return salOrderCode;
	}

	public void setSalOrderCode(String salOrderCode) {
		this.salOrderCode = salOrderCode;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getOperatorBy() {
		return operatorBy;
	}

	public void setOperatorBy(String operatorBy) {
		this.operatorBy = operatorBy;
	}

	public Date getOperatorDate() {
		return operatorDate;
	}

	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}

	public String getOperatorDesc() {
		return operatorDesc;
	}

	public void setOperatorDesc(String operatorDesc) {
		this.operatorDesc = operatorDesc;
	}

	public String getSalesLeadsOrder() {
		return salesLeadsOrder;
	}

	public void setSalesLeadsOrder(String salesLeadsOrder) {
		this.salesLeadsOrder = salesLeadsOrder;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorRemark() {
		return operatorRemark;
	}

	public void setOperatorRemark(String operatorRemark) {
		this.operatorRemark = operatorRemark;
	}

	public String getOperatorByName() {
		return operatorByName;
	}

	public void setOperatorByName(String operatorByName) {
		this.operatorByName = operatorByName;
	}
}
