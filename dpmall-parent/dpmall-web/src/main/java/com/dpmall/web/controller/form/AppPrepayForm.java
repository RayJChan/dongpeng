package com.dpmall.web.controller.form;

public class AppPrepayForm extends RequestForm{
	
	/**经销商Id**/
	public String distributorId;
	
	/**实物类订单Id**/
	public String orderCode;
	
	/**商店Id**/
	public String storeId;
	
	/**拒单类型**/
	public String rejectType;
	
	/**拒单备注**/
	public String rejectRemark;
	
	/**订单下派时间**/
	public String distributeTime;
	
	/**客户姓名**/
	public String clientName;
	
	/**客户电话**/
	public String clientTel;
	
	/**导购员ID**/
	public String acceptorId;
	
	/**开始页码**/
	public Integer startNum;
	
	public Integer pageSize;
	
	/**导购员接单备注**/
	public String acceptComment;
	
	/**订单状态**/
	public String status;
	
	/**经销商备注**/
	public String remark;
	
	/**发货单号**/
	public String consignmentId;
	
	/**查询数据**/
	public String search;
	
	/**电话号码**/
	public String phone;
	
	/**核销码**/
	public String priDepositCode;
	
	/**搜索状态**/
	public String statusSearch;
	
	
}
