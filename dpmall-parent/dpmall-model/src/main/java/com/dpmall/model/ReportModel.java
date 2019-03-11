package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实物订单信息
 * @author river
 * @since 2017-07-10
 */
public class ReportModel implements Serializable {

	private static final long serialVersionUID = -3797659104646003358L;

	/**ID**/
	public Long id;
	
	/**经销商id**/
	public String agencyId ;
	
	/**门店id**/
	public String storeId;
	
	/**数量**/
	public String count;
//----------------------------新增------------------------------------	
	/**派单数量**/
	public String distributeCount;
	
	/**接单数量**/
	public String acceptCount;
	
//--------------实物------------------	
	/**派单平均值**/
	public String distributeAverageValue;
	
	/**接单平均值**/
	public String acceptAverageValue;
	
	/**平均接单时长**/
	public String acceptAverageTime;
	
	/**平均发货时长**/
	public String deliverAverageTime;
	
	/**累计接单率**/
	public String acceptRate;
//--------------特权定金-------------
	/**核销数量**/
	public String writeOffCount;
	
	/**核销平均值**/
	public String writeOffAverageValue;
	
	/**核销率**/
	public String writeOffRate;
	
	/**平均核销时长**/
	public String writeOffAverageTime;

	/**核销好评率**/
	public String favorableRate;
	
//--------------留资-------------
	/**转化笔数**/
	public String conversionCount;
	
	/**转化平均值**/
	public String conversionAverageValue;
	
	/**转化率**/
	public String conversionRate;
	
	/**平均转化时长**/
	public String conversionAverageTime;
	
	/**判断用 判断是否在app显示**/
	public String judgement;

//-------------------对比结果-----------------------	
	
	/**派单数量比较结果**/
	public String distributeCountCon;
	
	/**接单数量 比较结果**/
	public String acceptCountCon;
//--------------实物------------------	
	/**派单平均值 比较结果**/
	public String distributeAverageValueCon;
	
	/**接单平均值 比较结果**/
	public String acceptAverageValueCon;
	
	/**平均发货时长 比较结果**/
	public String deliverAverageTimeCon;
	
	/**累计接单率 比较结果**/
	public String acceptRateCon;
//--------------特权定金-------------
	/**核销数量 比较结果**/
	public String writeOffCountCon;
	
	/**核销平均值 比较结果**/
	public String writeOffAverageValueCon;
	
	/**核销率 比较结果**/
	public String writeOffRateCon;
	
	/**平均核销时长 比较结果**/
	public String writeOffAverageTimeCon;
	
	/**核销好评率 比较结果**/
	public String favorableRateCon;
	
//--------------留资-------------
	/**转化笔数 比较结果**/
	public String conversionCountCon;
	
	/**转化平均值 比较结果**/
	public String conversionAverageValueCon;
	
	/**转化率 比较结果**/
	public String conversionRateCon;
	
	/**平均转化时长 比较结果**/
	public String conversionAverageTimeCon;
	
	/**判断用 比较结果**/
	public String judgementCon;
	
	//-------报表三期 新增字段----------------------------

	/**派单变化变化比率**/
	public String distributeCountChangeRate;

	/**接单变化变化比率**/
	public String acceptCountChangeRate;
	
	//----实物------------------
	
	/**累计接单率变化比率**/
	public String acceptRateChangeRate;
	
	/**平均发货时长变化比率**/
	public String deliverAverageTimeChangeRate;
	
	/**派单平均值变化比率**/
	public String distributeAverageValueChangeRate;

	/**接单平均值变化比率**/
	public String acceptAverageValueChangeRate;
	
	
	
	//-------特权订金------------
	/**核销数量**/
	public String writeOffCountChangeRate;
	
	/**平均核销时长变化比率**/
	public String writeOffAverageTimeChangeRate;
	
	/**核销平均值**/
	public String writeOffAverageValueChangeRate;
	
	/**核销率**/
	public String writeOffRateChangeRate;

	
	//-------留资---------
	/**平均转化时长变化比率**/
	public String conversionAverageTimeChangeRate;
	
	/**转化笔数变化比值**/
	public String conversionCountChangeRate;
	
	/**转化平均值 变化比率**/
	public String conversionAverageValueChangeRate;
	
	/**转化率 变化比率**/
	public String conversionRateChangeRate;
	
	

	
	
	
	
	
	
	
	
}
