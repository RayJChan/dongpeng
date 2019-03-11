package com.dpmall.model.in;

import java.io.Serializable;

/**
 * @author caoligai
 */
public class OrderEditStatusModelIn implements Serializable {
	
	/**导购员ID**/
	private String acceptorId;
	
	/**发货单Id**/
	private String consignmentCode;
	
	/**订单状态**/
	private String status;
	
	/**预约时间**/
	private String date;
	
	/**预约发货时间**/
	private String shippedDate;
	
	/**是否等通知发货**/
	private String isWaited;
	
	/**发货凭证**/
	private String certificate;
	
	/**签收单**/
	private String signUp;
	
	/**手机型号**/
	private String moblieModel;
	
	/**门店id**/
	private String storeId;
	
	/**经销商备注**/
	private String distributorCom;
	
	/**店员备注**/
	private String acceptorRemark;


	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	public String getConsignmentCode() {
		return consignmentCode;
	}

	public void setConsignmentCode(String consignmentCode) {
		this.consignmentCode = consignmentCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(String shippedDate) {
		this.shippedDate = shippedDate;
	}

	public String getIsWaited() {
		return isWaited;
	}

	public void setIsWaited(String isWaited) {
		this.isWaited = isWaited;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getSignUp() {
		return signUp;
	}

	public void setSignUp(String signUp) {
		this.signUp = signUp;
	}

	public String getMoblieModel() {
		return moblieModel;
	}

	public void setMoblieModel(String moblieModel) {
		this.moblieModel = moblieModel;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getDistributorCom() {
		return distributorCom;
	}

	public void setDistributorCom(String distributorCom) {
		this.distributorCom = distributorCom;
	}

	public String getAcceptorRemark() {
		return acceptorRemark;
	}

	public void setAcceptorRemark(String acceptorRemark) {
		this.acceptorRemark = acceptorRemark;
	}
}
