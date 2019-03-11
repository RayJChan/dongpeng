package com.dpmall.db.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author xiecong
 * o2o订单
 *
 */
public class PrePayEntity {
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
	
	/**订单状态**/
	public String status;
	
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
	
	/**订单金额(特权定金总金额)*/
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
	
	/**产品名称*/
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
	
	/**折扣金额**/
	public String discountPrice;
	
	/**限制时间**/
	public String LimitedTime;
	
	/**交易状态*/
	public String OrderStatus;
	
	/**推荐门店**/
	public String recommendStore;
	
	/**约定发货时间**/
	public Date orderShipmentsDate;
	
	/**O2O经销商状态**/
	public String agencyO2OStatus;
	
	/**O2O门店状态**/
	public String storeO2OStatus;
	
	/**特权定金产品明细**/
	public byte []  productList;

	/**特权定金实收金额**/
	public BigDecimal actualPrice;
	
	/**预约时间**/
	public Date appointmentDate;
	
	/**参考单号**/
	public String referencedCode;
	
	/**产品类型**/
	public String productType;
	
	/**PrePayEntry**/
	public List<PrePayItemEntity> items;
	
	/**PrePayEntry**/
	public List<PrePayItemEntity> productItems;
	
}
