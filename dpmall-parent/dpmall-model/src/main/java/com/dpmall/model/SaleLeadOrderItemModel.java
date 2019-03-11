package com.dpmall.model;

import java.io.Serializable;

/**
 * 订单项 
 * @author cwj
 * @since 2018-05-10
 */
public class SaleLeadOrderItemModel implements Serializable {

	/**ID*/
	private String id;

	/**留资订单ID**/
	private String orderId;

	/**产品编码**/
	private String productCode;

	/**商品数量*/
	private Integer quantity;

	/**商品名称*/
	private String productName;

	/**该商品合计金额*/
	private String price;

	/**品类**/
	private String productCatetory;

	/**单位**/
	private String unit;

	/**单位**/
	private String unitPrice;


	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductCatetory() {
		return productCatetory;
	}

	public void setProductCatetory(String productCatetory) {
		this.productCatetory = productCatetory;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
