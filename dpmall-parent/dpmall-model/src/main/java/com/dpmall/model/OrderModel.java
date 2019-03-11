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
	public BigDecimal orderTotal=BigDecimal.ZERO;
	
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
	
	
}
