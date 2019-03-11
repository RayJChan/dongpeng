package com.dpmall.db.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cwj
 * 退货订单
 *
 */
public class OrderReturnEntity {
	
	/**退货总金额**/
	public BigDecimal returnTotalPrice = BigDecimal.ZERO;
	
	/**退货单号**/
	public String returnOderCode ;
	
	/**退货状态**/
	public String returnStatus ;
	
	/**退货单详情**/
	public List<OrderReturnDetailsEntity> returnDetailsList =  new ArrayList<OrderReturnDetailsEntity>();
	
	

}
