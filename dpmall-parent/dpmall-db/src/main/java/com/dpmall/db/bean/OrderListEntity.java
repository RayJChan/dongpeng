package com.dpmall.db.bean;

import com.dpmall.db.bean.OrderHistoryItemEntity;
import com.dpmall.db.bean.OrderItemEntity;
import com.dpmall.db.bean.OrderReturnEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xiecong
 * o2o订单
 */
public class OrderListEntity {
    /**ID**/
    public Long id;

    /**发货单ID**/
    public 	String consignment;

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

    /**签收凭证**/
    public String signPic;

    /**订单状态**/
    public String status;

    /**商城订单状态**/
    public String webStatus;

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

    /**完成时间**/
    public Date finishTime;

    /**服务门店**/
    public String deliveryPoint;

    /**服务门店ID**/
    public String deliveryPointOfServiceId;

    /**发货方式**/
    public String deliveryMode;

    /**订单编号（发货单订单编号）**/
    public String orderCode;

    /**客户名称*/
    public String clientName;

    /**客户电话*/
    public String clientTel;

    /**订单金额*/
    public BigDecimal orderTotal;

    /**送货地址**/
    public String address;

    /**经销商ID**/
    public String allocatCode;

    /**收货地址**/
    public String shippingAddress;

    /**客户姓名**/
    public String buyerNick;

    /**商品数量**/
    public String productQuantity;

    /**商品单价**/
    public BigDecimal productBaseprice;

    /**商品汇总价格**/
    public BigDecimal productTotal;

    /**收货人名字**/
    public String firstName;

    /**收货人电话号码**/
    public String phone1;


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
    public String logisticsCompany;

    /**物流公司名称*/
    public String name;

    /**汇总金额*/
    public BigDecimal totalSum;

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

    /**实物类型**/
    public String splitOrderType;

    /**产品长度**/
    public Double length;

    /**产品宽度**/
    public Double width;

    /**约定发货时间**/
    public Date orderShipmentsDate;

    /**预约时间**/
    public Date appointmentDate;

    /**O2O门店状态**/
    public String storeO2OStatus;

    /**O2O经销商状态**/
    public String agencyO2OStatus;


    /**买家备注（拼接前）**/
    public String buyerMessage;

    /**客服备注（拼接前）**/
    public String sellerMessage;

    /**退货单号**/
    public String returnOderCode;

    /**是否退货**/
    public String returnCheck;

    /**参考单号**/
    public String referencedCode;

    /**orderEntry**/
    public List<OrderItemEntity> items;

    /**历史服务记录**/
    public OrderHistoryItemEntity historyItems;

    /**部分退货订单详情**/
    public  List<OrderReturnEntity> returnDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignment() {
        return consignment;
    }

    public void setConsignment(String consignment) {
        this.consignment = consignment;
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

    public String getSignPic() {
        return signPic;
    }

    public void setSignPic(String signPic) {
        this.signPic = signPic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(String webStatus) {
        this.webStatus = webStatus;
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

    public String getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(String deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public String getDeliveryPointOfServiceId() {
        return deliveryPointOfServiceId;
    }

    public void setDeliveryPointOfServiceId(String deliveryPointOfServiceId) {
        this.deliveryPointOfServiceId = deliveryPointOfServiceId;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
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

    public String getSplitOrderType() {
        return splitOrderType;
    }

    public void setSplitOrderType(String splitOrderType) {
        this.splitOrderType = splitOrderType;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Date getOrderShipmentsDate() {
        return orderShipmentsDate;
    }

    public void setOrderShipmentsDate(Date orderShipmentsDate) {
        this.orderShipmentsDate = orderShipmentsDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
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

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getSellerMessage() {
        return sellerMessage;
    }

    public void setSellerMessage(String sellerMessage) {
        this.sellerMessage = sellerMessage;
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

    public String getReferencedCode() {
        return referencedCode;
    }

    public void setReferencedCode(String referencedCode) {
        this.referencedCode = referencedCode;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    public OrderHistoryItemEntity getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(OrderHistoryItemEntity historyItems) {
        this.historyItems = historyItems;
    }

    public List<OrderReturnEntity> getReturnDetails() {
        return returnDetails;
    }

    public void setReturnDetails(List<OrderReturnEntity> returnDetails) {
        this.returnDetails = returnDetails;
    }
}
