package com.dpmall.datasvr.api;

import java.util.List;

import com.dpmall.model.ReportModel;


/** * @author  作者 E-mail: 
 * @date 创建时间：2017年8月31日 下午1:38:32 
 * @version 1.0  
 * @parameter  
 * @since   
 * @return  */
public interface IReportService {
	
	/**
	 * 时间段内OMS派单给经销商的实物订单数量
	 * **/
	public int orderDistributCount(String agencyId,String startTime,String endTime);
	
	/**
	 * 时间段内经销商接单数
	 * **/
	public int orderAgencyAcceptCount(String agencyId,String startTime,String endTime);
	
	/**
	 * 实物 门店的派单/接单数量 
	 * **/
	public int orderStoreAcceptCount(String storeId,String startTime,String endTime);
	
	
	/**
	 * 特权订金 OMS 派单给经销商
	 * @param agencyId :经销商id
	 */
	public int prePayDistributCount(String agencyId,String startTime,String endTime);
	
	/**
	 * 特权定金，经销商接单数量
	 */
	public int prePayAgencyAcceptCount(String agencyId,String startTime,String endTime);
	
	/**
	 * 特权定金 门店的派单/接单数量
	 */
	public int prePayStoreAcceptCount(String storeId,String startTime,String endTime);
	
	/**
	 * 特权订金 核销数量
	 */
	public int prePayWriteOffCount(String storeId,String startTime,String endTime);
	
	/**
	 * 经销商对应的门店的接单数量 
	 */
	public List<ReportModel> prePayStoreAcceptCountOfAgency (String agencyId,String startTime,String endTime);
	
//--------------------------------------------------以下的是新接口---------------------------------------------------------------------
	
//	/**
//	 * 实物订单接单数
//	 * **/
//	public List<OrderModel> recievedOrders(List<String> storePks,String startdate,String enddate);
//	
//	
//	/**
//	 * 实物派单数
//	 */
//	public List<OrderModel> distributedOrders(List<String> storePks,String startdate,String enddate);
//	
	
	/**
	 * 实物 经销商统计
	 */
	public ReportModel OrdersOfAgency(String code,List<String> agencyPks,String dateFormat,String startdate,String enddate)throws Exception;
	
	/**
	 * 实物 门店统计
	 */
	public ReportModel OrdersOfStore(String roleCode,String storeId,String startdate,String enddate,String dateFormat)throws Exception;
	
	/**
	 * 特权定金 经销商统计
	 */
	public ReportModel PrepaysOfAgency(List<String>agencyPks,String startdate,String enddate,String code,String dateFormat)throws Exception;
	
	/**
	 * 特权定金 门店统计
	 */
	public ReportModel PrepaysOfStore(String storeId,String startdate,String enddate,String dateFormat)throws Exception;
	
	/**
	 * 留资  经销商统计
	 */
	public ReportModel SalesLeadesOfAgency(List<String>agencyPks,String startdate,String enddate,String code,String dateFormat)throws Exception;
	
	/**
	 * 留资  门店统计
	 */
	public ReportModel SalesLeadesOfStore(String storeId,String startdate,String enddate,String dateFormat)throws Exception;
	
	/**
	 * 获取权限范围信息
	 */
	public List<String> getAppGroupInfo(String code);
	
	
	
}
