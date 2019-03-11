package com.dpmall.web.controller.form;

import java.io.Serializable;

/**
 * 订单项 
 * @author river
 * @since 2017-07-10
 */
public class SaleLeadsGoodsForm implements Serializable {
	
	private static final long serialVersionUID = 4687983236180867282L;
	
	/**订单项ID*/
	public String orderItemId;
	
	/**订单项数目*/
	public Integer itemNum;
	
	/**商品编码*/
	public String productCode;
	
	/**商品名称*/
	public String productName;
	
	/**成交价格*/
	public Double dealPrice;
	
	/**品类**/
	public String category;
	
}
