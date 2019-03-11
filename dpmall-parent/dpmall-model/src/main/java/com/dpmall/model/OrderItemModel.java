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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSplitOrderType() {
		return splitOrderType;
	}

	public void setSplitOrderType(String splitOrderType) {
		this.splitOrderType = splitOrderType;
	}

	public String getPackQua() {
		return packQua;
	}

	public void setPackQua(String packQua) {
		this.packQua = packQua;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTmallQuantity() {
		return tmallQuantity;
	}

	public void setTmallQuantity(String tmallQuantity) {
		this.tmallQuantity = tmallQuantity;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public BigDecimal getPromotionTotalsaved() {
		return promotionTotalsaved;
	}

	public void setPromotionTotalsaved(BigDecimal promotionTotalsaved) {
		this.promotionTotalsaved = promotionTotalsaved;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getJuntanPrice() {
		return juntanPrice;
	}

	public void setJuntanPrice(BigDecimal juntanPrice) {
		this.juntanPrice = juntanPrice;
	}

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public BigDecimal getReturnMony() {
		return returnMony;
	}

	public void setReturnMony(BigDecimal returnMony) {
		this.returnMony = returnMony;
	}

	public BigDecimal getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(BigDecimal returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public BigDecimal getReturnBasePrice() {
		return returnBasePrice;
	}

	public void setReturnBasePrice(BigDecimal returnBasePrice) {
		this.returnBasePrice = returnBasePrice;
	}

	public String getReturnOderCode() {
		return returnOderCode;
	}

	public void setReturnOderCode(String returnOderCode) {
		this.returnOderCode = returnOderCode;
	}
}
