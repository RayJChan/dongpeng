package com.dpmall.db.bean;

import java.math.BigDecimal;

/**
 * @author xiecong
 * o2o订单
 *
 */
public class ReportEntity {
	/**ID**/
	public Long id;
	
	/**经销商id**/
	public String agencyId ;
	
	/**门店id**/
	public String storeId;
	
	/**数量**/
	public Long count;
	
	/**接单时间-派单时间的秒数总和**/
	public Long acceptTimeSum ;
	
	/**发货时间-接单时间的秒数总和**/
	public Long  deliverTimeSum;	
	
	/**留资 转化成功的数量**/
	public BigDecimal salesSuccessCount;
	
	/**留资 转化成功的金额的总和**/
	public BigDecimal salesSuccessPrice;
	
	
	//-----------------对比用------------------------
	/**实物  派单数**/
	public BigDecimal orderDistributeCountContrast;
	
	/**实物  派单数**/
	public BigDecimal orderAcceptCountContrast;
	
	
	
}
