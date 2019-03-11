package com.dpmall.db.bean;

import java.math.BigDecimal;

/**
 * @author chenweijie
 * 首页信息
 *
 */
public class FirstPageEntity {
	/**留资: 待接单数量**/
	public BigDecimal salesWaitAcceptCount ;
	
	/**留资 :跟进中数量**/
	public BigDecimal salesFollowingCount ;
	
	/**留资 :完成数量**/
	public BigDecimal salesFinishedCount ;
	
	/**留资：与上个月对比**/
	public String salesComparison;
	
	
	/**实物:待接单数量**/
	public BigDecimal orderWaitAcceptCount ;
	
	/**实物:跟进中数量**/
	public BigDecimal orderFollowingCount ;
	
	/**实物:完成数量**/
	public BigDecimal orderFinishedCount ;
	
	/**实物：与上个月对比**/
	public String orderComparison;
	
	
	/**特权订金:待接单数量**/
	public BigDecimal prePayWaitAcceptCount ;
	
	/**特权订金:跟进中数量**/
	public BigDecimal prePayFollowingCount ;
	
	/**特权订金:完成数量**/
	public BigDecimal prePayFinishedCount ;
	
	/**特权订金：与上个月对比**/
	public String prePayComparison;
	
	
	
	/**留资转化率**/
	public String conversionRate;
	
	/**历史留资转化率**/
	public String lastConversionRate;
	
	/**转化率的比较**/
	public String conversionRateComparison;
	
	/**实物订单接单率**/
	public String acceptRate;
	
	/**历史实物订单接单率**/
	public BigDecimal lastAcceptRate;
	
	/**接单率的比较**/
	public String acceptRateComparison;
	
	/**特权订金核销率**/
	public String writeOffRate;
	
	/**历史特权订金核销率**/
	public BigDecimal lastWriteOffRate;
	
	/**核销率的比较**/
	public String writeOffRateComparison;	
	
	
	
}
