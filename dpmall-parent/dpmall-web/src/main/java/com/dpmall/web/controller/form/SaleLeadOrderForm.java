package com.dpmall.web.controller.form;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售线索
 * @author river
 * @since 2017-07-10
 */
public class SaleLeadOrderForm  extends RequestForm {

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
	
	/**服务地址*/
	public String serviceAddress;
	
	/**服务意向*/
	public String serviceCatelog;
	
	/**服务摘要*/
	public String serviceTitle;
	
	/**预计服务时间**/
	public String serviceDate;
	
	/**风格偏好*/
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
	
	/**店铺接单时间*/
	public String storeAcceptTime;
	
	/**店铺接单人备注*/
	public String storeAcceptorRemark;
	
	/**预约到店时间*/
	public String appointmentTime;
	
	/**结单时间*/
	public String closeTime;
	
	/**成单金额*/
	public Double total;
	
	/**拒单类型*/
	public Integer rejectType;
	
	/**拒单原因*/
	public String rejectRemark;
	
	/**完单备注**/
	public String finishRemark;
	
	/**经销商备注*/
	public String agencyRemark;
	
	/**订单编码*/
	public String orderCode;
	
	/**订单单据**/
	public String orderPic;
	
	/**成交商品列表*/
	public List<SaleLeadsGoodsForm> orderItemList=new ArrayList<SaleLeadsGoodsForm>();

	/**状态名称**/
	public String statusName;

	/**留资单id**/
	public Long saleLeadsOrderId;

	/**更新状态时的备注*/
	public String remark;

	/**未完成类型*/
	public String failTypeId;

	/**订单评价*/
	public String orderRvaluation;

	/**回访时间*/
	public String revisitTime;

	/**流失情况*/
	public String isLoss;






	
}
