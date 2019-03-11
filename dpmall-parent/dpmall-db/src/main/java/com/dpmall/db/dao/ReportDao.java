package com.dpmall.db.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.ReportDetailEntity;
import com.dpmall.db.bean.ReportEntity;

/** * @author  作者 E-mail:  
 * 	  @date 创建时间：2017年8月31日 下午2:35:14  
	  @version 1.0 * @parameter  
	  @since  
	  @return  */
public interface ReportDao {
	/**
	 * 时间段内OMS派单给经销商的实物订单数量
	 * **/
	int orderDistributCount(@Param("agencyId")String agencyId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**
	 * 时间段内经销商派单给店铺的实物订单数量
	 * **/
	int orderAgencyAcceptCount(@Param("agencyId")String agencyId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**
	 * 实物，经销商下派门店数量
	 * **/
	int orderAgencyToStoreCount(@Param("storeId")String storeId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**
	 * 实物 门店的接单数量 
	 * **/
	int orderStoreAcceptCount(@Param("storeId")String storeId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
	
	
	/**
	 * 特权订金 OMS 派单给经销商
	 * @param agencyId :经销商id
	 */
	int prePayDistributCount(@Param("agencyId")String agencyId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
	/**
	 * 特权定金，经销商接单数量
	 */
	int prePayAgencyAcceptCount(@Param("agencyId")String agencyId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
	/**
	 * 特权定金，经销商下派门店数量
	 * @param agencyId:经销商id
	 * @param storeId :门店id
	 * @return
	 */
	int prePayAgencyToStoreCount (@Param("storeId")String storeId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
	/**
	 * 特权定金 门店的接单数量
     * @param storeId :门店id
	 */
	int prePayStoreAcceptCount(@Param("storeId")String storeId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**
	 * 特权订金 核销数量
	 * @param storeId :门店id
	 */
	int prePayWriteOffCount(@Param("storeId")String storeId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**
	 * 经销商对应的门店的接单数量 
	 */
	public List<ReportEntity> prePayStoreAcceptCountOfAgency(@Param("agencyId")String agencyId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
//---------------------------------------------------NEW-------------------------------------------------------	
	
	//---------------------------------------------实物-----------------------------------------------
	/**
	 * 实物，OMS下派经销商数量
	 */
	public BigDecimal distributedOrders4Agency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList, @Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，经销商接单数量
	 */
	public BigDecimal acceptOrders4Agency(@Param("list")List<String> list, @Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物：经销商 接单时间-派单时间的差值的总和
	 */
	public BigDecimal acceptTimeSum4Agency(@Param("list")List<String> list, @Param("bigList")List<List<String>> bigList, @Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 实物：经销商  发货时间-接单时间的差值的总和
	 */
	public BigDecimal deliveryTimeSum4Agency(@Param("list")List<String> list, @Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	
	/**
	 * 实物，经销商下派给门店数量
	 */
	public BigDecimal distributedOrders4Store(@Param("storeId")String storeId ,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 实物，门店接单数量
	 */
	public BigDecimal acceptOrders4Store(@Param("storeId")String storeId ,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 实物 门店 接单时间-派单时间的差值的总和
	 */
	public BigDecimal acceptTimeSum4Store(@Param("storeId")String storeId ,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 实物 门店  发货时间-接单时间的差值的总和
	 */
	public BigDecimal deliveryTimeSum4Store(@Param("storeId")String storeId ,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 实物 发货数量
	 */
	public BigDecimal deliveryOrdersCounts(@Param("list")List<String> list,@Param("storeId")String storeId,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms下派给经销商 总价 web类型
	 */
	public BigDecimal distributeTotal4AgencyOfYes(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms下派给经销商 总价 非web类型
	 */
	public BigDecimal distributeTotal4AgencyOfNo(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms接单 总价 web类型
	 */
	public BigDecimal acceptTotal4AgencyOfYes(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms接单  总价 非web类型
	 */
	public BigDecimal acceptTotal4AgencyOfNo(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms派单给门店 web类型
	 */
	public BigDecimal distributeTotal4StoreOfYes(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms派单给门店 非web类型
	 */
	public BigDecimal distributeTotal4StoreOfNo(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms派单给门店 web类型
	 */
	public BigDecimal acceptTotal4StoreOfYes(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 实物，oms派单给门店 非web类型
	 */
	public BigDecimal acceptTotal4StoreOfNo(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);

	
	
	
	//---------------------------------------------特权定金-----------------------------------------------
	
	/**
	 * 特权定金，OMS下派经销商数量
	 */
	public BigDecimal distributedPrepay4Agency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 特权定金，经销商 接单数量
	 */
	public BigDecimal acceptPrepay4Agency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 特权 经销商下派给门店 数量
	 */
	public BigDecimal distributePrepayOfStore(@Param("storeId")String storeId ,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 特权 门店接单 数量
	 */
	public BigDecimal acceptPrepayOfStore(@Param("storeId")String storeId ,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 特权订金 经销商核销数量
	 */
	public BigDecimal prePayWriteOffOfAgency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	
	/**
	 * 特权订金 门店核销数量
	 */
	public BigDecimal prePayWriteOffOfStrore(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 特权订金 经销商 核销时间-接单时间的差值的总和
	 */
	public BigDecimal writeOffTimeSum4Agency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	
	/**
	 * 特权订金 门店 核销时间-接单时间的差值的总和
	 */
	public BigDecimal writeOffTimeSum4Store(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 特权订金 经销商 核销金额总和
	 */
	public BigDecimal writeOffPriceOfAgency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	/**
	 * 特权订金 门店 核销金额总和 
	 */
	public BigDecimal writeOffPriceOfStore(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);
	
	
	
	//---------------------------------------------留资-----------------------------------------------

	/**
	 * 留资，OMS下派经销商数量
	 */
	public BigDecimal distributeSalesLeadsOfAgency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 留资，经销商 接单数量
	 */
	public BigDecimal acceptSalesLeadsOfAgency(@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 留资，门店 经销商下派给门店
	 */
	public BigDecimal distributeSalesLeadsOfStore(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);

	
	/**
	 * 留资，门店 接单数量
	 */
	public BigDecimal acceptSalesLeadsOfStore(@Param("storeId")String storeId,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 留资，转化成功的数量 
	 */
	public BigDecimal salesLeadsSuccessfulCount(@Param("list")List<String> list,@Param("storeId")String storeId, @Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 留资，转化成功的金额总价
	 */
	public BigDecimal salesLeadsSuccessPrice(@Param("list")List<String> list,@Param("storeId")String storeId,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**
	 * 留资，转化成功时间-接单时间
	 */
	public BigDecimal salesLeadsSuccessTimes(@Param("list")List<String> list,@Param("storeId")String storeId,@Param("bigList")List<List<String>> bigList,@Param("startdate")String startdate,@Param("enddate")String enddate);

	/**查询角色编码**/
	public String getRoleCode(@Param("code")String code);
	
	
	/**查询历史数据 做对比**/
	public ReportDetailEntity getContrastInfo (@Param("code")String code ,@Param("dateFormat")String dateFormat,@Param("isStore")String isStore,@Param("storeId")String storeId, @Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
}
