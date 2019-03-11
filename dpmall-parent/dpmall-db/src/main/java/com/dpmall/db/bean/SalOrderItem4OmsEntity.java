package com.dpmall.db.bean;

/**
 */
public class SalOrderItem4OmsEntity {


    private String id;

	/**产品编号*/
	private String productCode;

	/**产品名称*/
	private String productName;

	/**产品分类*/
	private String productCategory;

	/**商品数量*/
	private String quantity;

	/**单位**/
	private String unit;

	/**单价**/
	private String unitPrice;

	/**该商品合计金额*/
	private String price;

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

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
