package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 实物订单信息
 * @author river
 * @since 2017-07-10
 */
public class OrderModel implements Serializable {

	public static final long serialVersionUID = -7230929100050324381L;
	
	/**订单编号*/
	public String orderCode;
	
	/**订单参考编号*/
	public String orderRefCode;
	
	/**客户名称*/
	public String clientName;
	
	/**客户电话*/
	public String clientTel;
	
	/**是否自己仓库发货**/
	public String isDeliverySelf = "Y";
	
	/**发票信息*/
	public String receiptInfo;
	
	/**客户备注*/
	public String clientRemark;
	
	/**送货方式*/
	public String deliverMode;
	
	/**付费方式*/
	public String payMode;
	
	/**订单金额*/
	public BigDecimal orderTotal;
	
	/**订单列表*/
	public List<OrderDetailsModel> orderItemList = new ArrayList<OrderDetailsModel>();
	
	/**经销商ID**/
	public String allocatCode;
	
	/**收货地址**/
	public String shippingAddress;
	
	/**客户姓名**/
	public String buyerNick;
	
	/**商品数量**/
	public String productQuantity;
	
	/**商品单价**/
	public BigDecimal productBaseprice=BigDecimal.ZERO;
	
	/**发货时间**/
	public String  deliveryTime;
	
	/**商品汇总价格**/
	public BigDecimal productTotal=BigDecimal.ZERO;
	
	/**收货人名字**/
	public String firstName;
	
	/**收货人电话号码**/
	public String phone1;
	
	/**送货地址**/
	public String address;
	
	/**服务门店**/
	public String deliveryPointOfService;
	
	/**服务门店id**/
	public String deliveryPointOfServiceId;
	
	/**订单状态**/
	public String status;
	
	/**ID**/
	public Long id;
	
	/**订单编号**/
	public String consignmentCode;
	
	/**包邮**/
	public String logisticsInfo;
	
	/**物流单号**/
	public String trackingId;
	
	/**运费**/
	public BigDecimal deliveryCost;
	
	/**订单来源*/
	public String salesApplication;
	
	/**均摊金额*/
	public BigDecimal juntanPrice;
	
	/**付款数量*/
	public BigDecimal payAmount;
	
	/**服务金额*/
	public BigDecimal serviceAmount;
	
	/**送货方式*/
	public String deliveryMethods;
	
	/**物流公司名称*/
	public String name;
	
	/**客户备注**/
	public String cusComment;
	
	/**客服备注**/
	public String serverComment;
	
	/**客户退货备注**/
	public String cusRefuseComment;
	
	/**门店接单人拒单备注**/
	public String acceptedRefuseComment;
	
	/**门店接单人**/
	public String acceptedBy;
	
	/**发货凭证**/
	public String deliverPic;
	
	/**签收凭证**/
	public String signPic;
		
	/**经销商备注**/
	public String agencyComment;
	
	/**门店接单人备注**/
	public String acceptedComment;
	
	/**发货方式**/
	public String deliveryMode;
	
	/**汇总金额*/
	public BigDecimal totalSum;
	
	/**物流公司名称*/
	public String logisticsCompany;
	
	/**操作状态*/
	public String operateStatus;
	
	/**返回状态*/
	public String returnStatus;
	
	/**省*/
	public String RegionName;

	/**市*/
	public String CityName;

	/**区*/
	public String DistrictName;
	
	/**发货备注*/
	public String deliveryRemark;
	
	/**交易状态*/
	public String OrderStatus;
	
	/**是否等待客户通知**/
	public String waitShipments;
	
	/**退货单号**/
	public List<String> returnOderCodes=new ArrayList<String>();
	
	/**门店接单人时间**/
	public String acceptedTime;
	
	/**约定发货时间**/
	public String orderShipmentsDate;
	
	/**预约时间**/
	public String appointmentDate;
	
	/**O2O门店状态**/
	public String storeO2OStatus;
	
	/**O2O经销商状态**/
	public String agencyO2OStatus;
	
	/**退货单号**/
	public String returnOderCode;
	
	/**是否退货**/
	public String returnCheck;
	
	/**退货总金额**/
	public BigDecimal returnTotalPrice;
	
	/**发货单号**/
	public String referencedCode;
	
	/**Item的映射**/
	public List<OrderItemModel> items=new ArrayList<OrderItemModel>();
	
	/**签收凭证**/
	public List<String> signPics = new ArrayList<String>();
	
	/**上传凭证**/
	public List<String> deliverPics = new ArrayList<String>();

//	/**历史记录**/
//	public OrderHistoryItemModel historyItems ;
	
	/**部分退货订单详情**/
	public  List<OrderReturnModel> returnDetails = new ArrayList<OrderReturnModel>();
	
	/**退货订单总金额(每一张退货单的总金额)**/
	public String returnPriceSum  ;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderRefCode() {
		return orderRefCode;
	}

	public void setOrderRefCode(String orderRefCode) {
		this.orderRefCode = orderRefCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientTel() {
		return clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}

	public String getIsDeliverySelf() {
		return isDeliverySelf;
	}

	public void setIsDeliverySelf(String isDeliverySelf) {
		this.isDeliverySelf = isDeliverySelf;
	}

	public String getReceiptInfo() {
		return receiptInfo;
	}

	public void setReceiptInfo(String receiptInfo) {
		this.receiptInfo = receiptInfo;
	}

	public String getClientRemark() {
		return clientRemark;
	}

	public void setClientRemark(String clientRemark) {
		this.clientRemark = clientRemark;
	}

	public String getDeliverMode() {
		return deliverMode;
	}

	public void setDeliverMode(String deliverMode) {
		this.deliverMode = deliverMode;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {

		this.orderTotal =orderTotal;
	}

	public List<OrderDetailsModel> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderDetailsModel> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getAllocatCode() {
		return allocatCode;
	}

	public void setAllocatCode(String allocatCode) {
		this.allocatCode = allocatCode;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getProductBaseprice() {
		return productBaseprice;
	}

	public void setProductBaseprice(BigDecimal productBaseprice) {
		this.productBaseprice = productBaseprice;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public BigDecimal getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(BigDecimal productTotal) {
		this.productTotal = productTotal;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliveryPointOfService() {
		return deliveryPointOfService;
	}

	public void setDeliveryPointOfService(String deliveryPointOfService) {
		this.deliveryPointOfService = deliveryPointOfService;
	}

	public String getDeliveryPointOfServiceId() {
		return deliveryPointOfServiceId;
	}

	public void setDeliveryPointOfServiceId(String deliveryPointOfServiceId) {
		this.deliveryPointOfServiceId = deliveryPointOfServiceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsignmentCode() {
		return consignmentCode;
	}

	public void setConsignmentCode(String consignmentCode) {
		this.consignmentCode = consignmentCode;
	}

	public String getLogisticsInfo() {
		return logisticsInfo;
	}

	public void setLogisticsInfo(String logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getSalesApplication() {
		return salesApplication;
	}

	public void setSalesApplication(String salesApplication) {
		this.salesApplication = salesApplication;
	}

	public BigDecimal getJuntanPrice() {
		return juntanPrice;
	}

	public void setJuntanPrice(BigDecimal juntanPrice) {
		this.juntanPrice = juntanPrice;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public String getDeliveryMethods() {
		return deliveryMethods;
	}

	public void setDeliveryMethods(String deliveryMethods) {
		this.deliveryMethods = deliveryMethods;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAcceptedRefuseComment() {
		return acceptedRefuseComment;
	}

	public void setAcceptedRefuseComment(String acceptedRefuseComment) {
		this.acceptedRefuseComment = acceptedRefuseComment;
	}

	public String getAcceptedBy() {
		return acceptedBy;
	}

	public void setAcceptedBy(String acceptedBy) {
		this.acceptedBy = acceptedBy;
	}

	public String getDeliverPic() {
		return deliverPic;
	}

	public void setDeliverPic(String deliverPic) {
		this.deliverPic = deliverPic;
	}

	public String getSignPic() {
		return signPic;
	}

	public void setSignPic(String signPic) {
		this.signPic = signPic;
	}

	public String getAgencyComment() {
		return agencyComment;
	}

	public void setAgencyComment(String agencyComment) {
		this.agencyComment = agencyComment;
	}

	public String getAcceptedComment() {
		return acceptedComment;
	}

	public void setAcceptedComment(String acceptedComment) {
		this.acceptedComment = acceptedComment;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getRegionName() {
		return RegionName;
	}

	public void setRegionName(String regionName) {
		RegionName = regionName;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}

	public String getDeliveryRemark() {
		return deliveryRemark;
	}

	public void setDeliveryRemark(String deliveryRemark) {
		this.deliveryRemark = deliveryRemark;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getWaitShipments() {
		return waitShipments;
	}

	public void setWaitShipments(String waitShipments) {
		this.waitShipments = waitShipments;
	}

	public List<String> getReturnOderCodes() {
		return returnOderCodes;
	}

	public void setReturnOderCodes(List<String> returnOderCodes) {
		this.returnOderCodes = returnOderCodes;
	}

	public String getAcceptedTime() {
		return acceptedTime;
	}

	public void setAcceptedTime(String acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	public String getOrderShipmentsDate() {
		return orderShipmentsDate;
	}

	public void setOrderShipmentsDate(String orderShipmentsDate) {
		this.orderShipmentsDate = orderShipmentsDate;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
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

	public String getReturnOderCode() {
		return returnOderCode;
	}

	public void setReturnOderCode(String returnOderCode) {
		this.returnOderCode = returnOderCode;
	}

	public String getReturnCheck() {
		return returnCheck;
	}

	public void setReturnCheck(String returnCheck) {
		this.returnCheck = returnCheck;
	}

	public BigDecimal getReturnTotalPrice() {
		return returnTotalPrice;
	}

	public void setReturnTotalPrice(BigDecimal returnTotalPrice) {
		this.returnTotalPrice = returnTotalPrice;
	}

	public String getReferencedCode() {
		return referencedCode;
	}

	public void setReferencedCode(String referencedCode) {
		this.referencedCode = referencedCode;
	}

	public List<OrderItemModel> getItems() {
		return items;
	}

	public void setItems(List<OrderItemModel> items) {
		this.items = items;
	}

	public List<String> getSignPics() {
		return signPics;
	}

	public void setSignPics(List<String> signPics) {
		this.signPics = signPics;
	}

	public List<String> getDeliverPics() {
		return deliverPics;
	}

	public void setDeliverPics(List<String> deliverPics) {
		this.deliverPics = deliverPics;
	}

	public List<OrderReturnModel> getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(List<OrderReturnModel> returnDetails) {
		this.returnDetails = returnDetails;
	}

	public String getReturnPriceSum() {
		return returnPriceSum;
	}

	public void setReturnPriceSum(String returnPriceSum) {
		this.returnPriceSum = returnPriceSum;
	}
}
