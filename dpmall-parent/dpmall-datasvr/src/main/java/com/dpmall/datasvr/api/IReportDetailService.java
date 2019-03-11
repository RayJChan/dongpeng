package com.dpmall.datasvr.api;

import java.math.BigDecimal;
import java.util.List;

import com.dpmall.model.ReportDetailModel;

/** * @author  作者 E-mail: 
 * @date 创建时间：2017年8月31日 下午1:38:32 
 * @version 1.0  
 * @parameter  
 * @since   
 * @return  */
public interface IReportDetailService {
	
	//--------------------------------------------------历史记录-------------------------------------------------------------------------------------------------------
	//----------------------实物-------------------------------------------------------------------------------------------
	
	/**
	 * 实物 派单数 历史记录
	 */
	public List<ReportDetailModel> orderDistributedHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 实物 接单 历史记录
	 */
	public List<ReportDetailModel> orderAcceptHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 实物 派单平均值 历史记录
	 */
	public List<ReportDetailModel> orderDistributeAVGHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 实物 接单平均值 历史记录
	 */
	public List<ReportDetailModel> orderAcceptAVGHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 实物 累计接单率 历史记录
	 */
	public List<ReportDetailModel> orderAcceptRateHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 实物 平均发货时长 历史记录
	 */
	public List<ReportDetailModel> orderDeliverAVGTimeHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime);
	
	//-------------特权订金----------------
	
	/**
	 * 特权订金   派单  历史趋势
	 */
	public List<ReportDetailModel> prepayDistributeHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 特权订金 核销数 历史记录
	 */
	public List<ReportDetailModel> prepayWriteOffCountHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 特权订金   核销平均值  历史趋势
	 */
	public List<ReportDetailModel> prepayWriteOffAVGHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 特权订金   核销率  历史趋势
	 */
	public List<ReportDetailModel> prepayWriteOffRateHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 特权订金   核销平均时长  历史趋势
	 */
	public List<ReportDetailModel> prepayWriteOffAVGTimeHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	//-------------留资----------------
	
	/**
	 * 留资   转化笔数  历史趋势
	 */
	public List<ReportDetailModel> saleConversionCountHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 留资   转化平均值  历史趋势
	 */
	public List<ReportDetailModel> saleConversionAVGHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 留资  转化率  历史趋势
	 */
	public List<ReportDetailModel> saleConversionRateHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	/**
	 * 留资   平均转化时长  历史趋势
	 */
	public List<ReportDetailModel> saleConversionAVGTimeHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime);
	
	
	
	//--------------------------------------------------排名-------------------------------------------------------------------------------------------------------
	
	//-------------实物----------------
	
	/**
	 * 实物  派单   排名
	 * @param code : 分组编码 	如：100001
	 * @param index : 需要查询的以下几级
	 * @param roleCode ：角色编码  	如：“zongbu”
	 * @param startTime :开始时间
	 * @param endTime ：结束时间
	 * @param offset 
	 * @param pageSize 
	 * @return
	 */
	public List<ReportDetailModel> orderDistributedSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 实物  派单平均单值  排名
	 */
	public List<ReportDetailModel> orderDistributeAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 实物  接单平均单值  排名
	 */
	public List<ReportDetailModel> orderAcceptAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	
	/**
	 * 实物  累计接单率  排名
	 */
	public List<ReportDetailModel> orderAcceptRateSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 实物  平均发货时长  排名
	 */
	public List<ReportDetailModel> orderDeliverAVGTimeSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	//-------------特权订金----------------
	
	/**
	 * 特权订金   核销平均值   排名  
	 */
	public List<ReportDetailModel> prepayWriteOffAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 特权订金   核销率   排名  
	 */
	public List<ReportDetailModel> prepayWriteOffRateSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 特权订金   核销时长   排名  
	 */
	public List<ReportDetailModel> prepayWriteOffAVGTimeSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	//-------------留资----------------
	
	/**
	 * 留资   转化平均值   排名
	 */
	public List<ReportDetailModel> saleConversionAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 留资   转化率  排名
	 */
	public List<ReportDetailModel> saleConversionRateSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	/**
	 * 留资  平均转化时长   排名
	 */
	public List<ReportDetailModel> saleConversionAVGTimeSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult);
	
	//------------------------------------------------------------区域分布---------------------------------------------------------------------------
	/**实物  派单 省 区域分布**/
	public List<ReportDetailModel> orderDistributeProvinceRegion (String code,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	/**实物  派单 市 区域分布**/
	public List<ReportDetailModel> orderDistributeCityRegion ( String code,String provinceName,String isStore,String storeId ,String startTime, String endTime,Integer startNum, Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	/**实物  接单 省 区域分布**/
	public List<ReportDetailModel> orderAcceptProvinceRegion (String code,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	/**实物  接单 市 区域分布**/
	public List<ReportDetailModel> orderAcceptCityRegion (String code,String provinceName,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	
	
	/**特权  省 区域分布**/
	public List<ReportDetailModel> prePayProvinceRegion (String code,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	/**特权  市 区域分布**/
	public List<ReportDetailModel> prePayCityRegion ( String code,String provinceName,String isStore,String storeId ,String startTime, String endTime,Integer startNum, Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	
	/**留资  省 区域分布**/
	public List<ReportDetailModel> saleProvinceRegion (String code,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	
	/**留资  市 区域分布**/
	public List<ReportDetailModel> saleCityRegion ( String code,String provinceName,String isStore,String storeId ,String startTime, String endTime,Integer startNum, Integer pageSize,BigDecimal pastResult,Integer pastRowNum);
	
	//------------------------------------------------------------品类销售top5---------------------------------------------------------------------------
	
	/**品类销售top5  派单**/
	public List<ReportDetailModel> distributeCategoryTop5 ( String code,String category,String isStore,String storeId ,String startTime, String endTime);
	
	/**品类销售top5  接单**/
	public List<ReportDetailModel> acceptCategoryTop5 ( String code,String category,String isStore,String storeId ,String startTime, String endTime);
	

}
