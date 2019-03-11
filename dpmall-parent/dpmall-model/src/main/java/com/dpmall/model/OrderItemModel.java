package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 谢聪
 * orderItems表的映射类
 *
 */
public class OrderItemModel implements Serializable{

	private static final long serialVersionUID = -7235254363010889247L;

	/**id**/
	public String id;
	
	/**拆单品类**/
	public String splitOrderType;
	
	/**包装量**/
	public String packQua;
	
	/**商品编码**/
	public String code;
	
	/**商品名称**/
	public String name;
	
	/**商品描述**/
	public String description;
	
	/**品类**/
	public String category;
	
	/**单位**/
	public String unit;
	
	/**重量**/
	public String netWeight;
	
	/**体积**/
	public String volume;
	
	/**规格**/
	public String size;
	
	/**数量**/
	public String quantity;
	
	/**天猫数量**/
	public String tmallQuantity;
	
	/**单价**/
	public BigDecimal basePrice;
	
	/**销售金额**/
	public BigDecimal totalPrice;
	
	/**运费**/
	public BigDecimal deliveryCost;
	
	/**行优惠**/
	public BigDecimal promotionTotalsaved;
	
	/**行实付价格**/
	public BigDecimal payAmount;
	
	/**均摊费**/
	public BigDecimal juntanPrice;
	
	/**服务费**/
	public BigDecimal serviceAmount;
	
	/**商品编码**/
	public String productCode;
	
	/**商品品类**/
	public String productCategory;
	
	/**退货金额**/
	public BigDecimal returnMony;
	
	/**退货数量**/
	public BigDecimal returnQuantity;
	
	/**退货单价**/
	public BigDecimal returnBasePrice;
	
	/**退货单号**/
	public String returnOderCode;
	
	
}
