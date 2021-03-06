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
	
	
}
