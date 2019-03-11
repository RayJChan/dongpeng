package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrePayItemModel implements Serializable{

	private static final long serialVersionUID = -5119796586410268662L;

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
	public String basePrice;
	
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
	
	
	/**下单时间**/
	public Date createds;
	
	/**限制时间**/
	public String limitedTime;
	
	/**核销码**/
	public String writeoffCode;
	
	/**特权定金信息条件**/
	public String pDescription;
	
	/**特权定金信息名称**/
	public String pName ;
	
	/**订单来源*/
	public String salesApplication;
	
	

}
