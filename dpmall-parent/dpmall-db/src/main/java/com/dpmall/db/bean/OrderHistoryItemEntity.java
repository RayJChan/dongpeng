package com.dpmall.db.bean;

import java.util.Date;

/**
 * @author cwj
 * o2o订单
 *
 */
public class OrderHistoryItemEntity {

	/**ID**/
	public Long id;
		
	/**推荐门店**/
	public 	String recommendStore;
	
	/**客户备注**/
	public String cusComment;
	
	/**客服备注**/
	public String serverComment;
	
	/**客户退货备注**/
	public String cusRefuseComment;
	
	/**经销商备注**/
	public String agencyComment;
	
	/**经销商拒单类型**/
	public String refuseType;
	
	/**经销商拒单备注**/
	public String refuseComment;
	
	/**门店接单人拒单备注**/
	public String acceptedRefuseComment;
	
	/**发货凭证**/
	public String deliverPic;
	
	/**门店接单人**/
	public String acceptedBy;
	
	/**门店接单人备注**/
	public String acceptedComment;
	
	/**门店接单人时间**/
	public Date acceptedTime;
	
	/**是否自己仓库发货**/
	public String isDeliverySelf;
	
	/**发货时间**/
	public Date deliveryTime;
	
	/**完成时间*/
	public Date finishTime;
	
	/**缺货产品列*/
	public String stockout;
	
	/**返回状态*/
	public String returnStatus;
	
	/**发货备注*/
	public String deliveryRemark;
	
	/**约定发货时间*/
	public Date orderShipmentsDate;
	
	/**签收单**/
	public String signPic;
	
	/**是否等待客户通知**/
	public String waitShipments;
	
	/**O2O门店状态**/
	public String storeO2OStatus;
	
	/**O2O经销商状态**/
	public String agencyO2OStatus;
	
	/**预约时间**/
	public Date appointmentDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecommendStore() {
		return recommendStore;
	}

	public void setRecommendStore(String recommendStore) {
		this.recommendStore = recommendStore;
	}

	public String getCusComment() {
		return cusComment;
	}

	public void setCusComment(String cusComment) {
		this.cusComment = cusComment;
	}

	public String getServerComment() {
		return serverComment;
	}

	public void setServerComment(String serverComment) {
		this.serverComment = serverComment;
	}

	public String getCusRefuseComment() {
		return cusRefuseComment;
	}

	public void setCusRefuseComment(String cusRefuseComment) {
		this.cusRefuseComment = cusRefuseComment;
	}

	public String getAgencyComment() {
		return agencyComment;
	}

	public void setAgencyComment(String agencyComment) {
		this.agencyComment = agencyComment;
	}

	public String getRefuseType() {
		return refuseType;
	}

	public void setRefuseType(String refuseType) {
		this.refuseType = refuseType;
	}

	public String getRefuseComment() {
		return refuseComment;
	}

	public void setRefuseComment(String refuseComment) {
		this.refuseComment = refuseComment;
	}

	public String getAcceptedRefuseComment() {
		return acceptedRefuseComment;
	}

	public void setAcceptedRefuseComment(String acceptedRefuseComment) {
		this.acceptedRefuseComment = acceptedRefuseComment;
	}

	public String getDeliverPic() {
		return deliverPic;
	}

	public void setDeliverPic(String deliverPic) {
		this.deliverPic = deliverPic;
	}

	public String getAcceptedBy() {
		return acceptedBy;
	}

	public void setAcceptedBy(String acceptedBy) {
		this.acceptedBy = acceptedBy;
	}

	public String getAcceptedComment() {
		return acceptedComment;
	}

	public void setAcceptedComment(String acceptedComment) {
		this.acceptedComment = acceptedComment;
	}

	public Date getAcceptedTime() {
		return acceptedTime;
	}

	public void setAcceptedTime(Date acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	public String getIsDeliverySelf() {
		return isDeliverySelf;
	}

	public void setIsDeliverySelf(String isDeliverySelf) {
		this.isDeliverySelf = isDeliverySelf;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getStockout() {
		return stockout;
	}

	public void setStockout(String stockout) {
		this.stockout = stockout;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getDeliveryRemark() {
		return deliveryRemark;
	}

	public void setDeliveryRemark(String deliveryRemark) {
		this.deliveryRemark = deliveryRemark;
	}

	public Date getOrderShipmentsDate() {
		return orderShipmentsDate;
	}

	public void setOrderShipmentsDate(Date orderShipmentsDate) {
		this.orderShipmentsDate = orderShipmentsDate;
	}

	public String getSignPic() {
		return signPic;
	}

	public void setSignPic(String signPic) {
		this.signPic = signPic;
	}

	public String getWaitShipments() {
		return waitShipments;
	}

	public void setWaitShipments(String waitShipments) {
		this.waitShipments = waitShipments;
	}

	public String getStoreO2OStatus() {
		return storeO2OStatus;
	}

	public void setStoreO2OStatus(String storeO2OStatus) {
		this.storeO2OStatus = storeO2OStatus;
	}

	public String getAgencyO2OStatus() {
		return agencyO2OStatus;
	}

	public void setAgencyO2OStatus(String agencyO2OStatus) {
		this.agencyO2OStatus = agencyO2OStatus;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}
