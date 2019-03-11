package com.dpmall.model.in;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单项 
 * @author cwj
 * @since 2018-05-16
 */
public class SalOrderGoodsModelIn implements Serializable {

	/**订单项ID*/
	private String orderItemId;

	/**商品编码*/
	private String productCode;

	/**商品名称*/
	private String productName;

	/**商品品类**/
	private String category;

	/**单位**/
	private String unit;

	/**单价**/
	private String unitPrice;

	/**单位**/
	private String quantity;

	/**商品金额**/
	private String amount;


	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
}
