package com.dpmall.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售线索操作记录
 * @author crown
 * @since 2017-07-21
 */
public class SalesLeadsOperationModel implements Serializable{
	
	private static final long serialVersionUID = -4699869236557727902L;

	/**操作记录ID*/
	public Long operateId;

	/**操作人*/
	public String operatorBy;

	/**操作描述*/
	public String operatorDesc;

	/**操作类型*/
	public String operatorType;

	/**操作人姓名*/
	public String operatorByName;

	/**操作时间*/
	public String operatorTime;
	/**
	 * 经销商编号
	 **/
	public String agencyCode;

	/**门店名称**/
	public String storeName;

	/**订单编号**/
	public String salOrderCode;

	/**数量**/
	public String count;


	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getOperatorDesc() {
		return operatorDesc;
	}

	public void setOperatorDesc(String operatorDesc) {
		this.operatorDesc = operatorDesc;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorByName() {
		return operatorByName;
	}

	public void setOperatorByName(String operatorByName) {
		this.operatorByName = operatorByName;
	}

	public String getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
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

	public String getSalOrderCode() {
		return salOrderCode;
	}

	public void setSalOrderCode(String salOrderCode) {
		this.salOrderCode = salOrderCode;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
