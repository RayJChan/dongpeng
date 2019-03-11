package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cwj
 * o2o订单
 *
 */
public class OrderReturnDetailsModel implements Serializable {
	
	private static final long serialVersionUID = 7091512392910799710L;

	/**商品编码**/
	public String productCode;
	
	/**品类**/
	public String category;
	
	/**数量**/
	public String returnQuantity;
	
	/**退款总价**/
	public BigDecimal returnPayAmount;
	
	/**商品名称**/
	public String productName;
	
	
	/**退货单号**/
	public String returnOderCode ;
	
	/**单位**/
	public String unit;
	
	/**判断是否退货**/
	public String returnCheck;
	
	/**退货状态**/
	public String returnStatus ;
	
	
	
}
