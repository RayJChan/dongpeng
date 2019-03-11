package com.dpmall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售线索
 * @author river
 * @since 2017-07-10
 */
public class SaleLeadsModel  implements Serializable {
	private static final long serialVersionUID = 2413878781664810236L;

	/**留资订单ID*/
	public Long id;
	
	/**客户姓名*/
	public String clientName;
	
	/**客户电话**/
	public String clientTel;	
	
	/**客户地址*/
	public String clientAddr;
	
	/**客户备注**/
	public String clientRemark;
	
	/**完单备注**/
	public String finishRemark;
	
	/**服务地址*/
	public String serviceAddress;
	
	/**服务意向*/
	public String serviceCatelog;
	
	/**服务摘要*/
	public String serviceTitle;
	
	/**预计服务时间**/
	public String serviceDate;
	
	/**装修风格-风格偏好*/
	public String style;
	
	/**品牌偏好*/
	public String brand;
	
	/**客户预算*/
	public Double budget;
	
	/**客服电话*/
	public String callServiceTel;
	
	/**客服备注*/
	public String callServiceRemark;
	
	/**销售线索订单状态*/
	public String saleLeadsStatus;
	
	/**下派时间*/
	public String distributeTime;
	
	/**经销商Id*/
	public Long distributorId;
	
	/**经销商操作时间*/
	public String distributorOperateTime;
	
	/**经销商用户名*/
	public String distributorUserName;
	
	/**建议店铺ID*/
	public Long recommendstoreId;
	
	/**建议店铺名称*/
	public String recommendstoreName;

	/**店铺接单人*/
	public String storeAcceptor;
	
	/**店铺接单人名称*/
	public String storeAcceptorName;
	
	/**接单时间*/
	public String storeAcceptTime;
	
	/**店铺接单人备注*/
	public String storeAcceptorRemark;
	
	/**预约时间*/
	public String appointmentTime;
	
	/**结单时间*/
	public String closeTime;
	
	/**成单金额*/
	public Double total;
	
	/**拒单类型*/
	public Integer rejectType;
	
	/**拒单原因*/
	public String rejectRemark;
	
	/**经销商备注*/
	public String agencyRemark;
	
	/**订单编码*/
	public String orderCode;
	
	/**接单店铺*/
	public String acceptStore;
	
	/**到店时间*/
	public Date arriveDate;

	/**留资订单单据**/
	public String orderPic;
	
	/**是否经销商下派给自己**/
	public String isAgency = "Y";
	
	/***留资订单单据(显示)**/
	public List<String> orderPics=new ArrayList<String>();
	
	/**成交商品列表*/
	public List<SaleLeadsGoodsModel> orderItemList=new ArrayList<SaleLeadsGoodsModel>();;
	
}
