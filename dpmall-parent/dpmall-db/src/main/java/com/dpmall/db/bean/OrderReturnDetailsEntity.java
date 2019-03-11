package com.dpmall.db.bean;

import java.math.BigDecimal;

/**
 * @author cwj
 * 退货订单
 *
 */
public class OrderReturnDetailsEntity {
	
	
	/**商品编码**/
	public String productCode;
	
	/**品类**/
	public String category;
	
	/**退货数量**/
	public String returnQuantity;
	
	/**退款总价**/
	public BigDecimal returnPayAmount;
	
	/**商品名称**/
	public String productName;
	
	
	/**退货单号**/
	public String returnOderCode ;
	
	/**单价**/
	public BigDecimal basePrice;
	
	/**单位**/
	public String unit;
	
	/**判断是否退货**/
	public String returnCheck;
	
	/**退货状态**/
	public String returnStatus ;
	

	
	
	
	

	
	
	
	

}
