package com.dpmall.db.bean;

public class OthersPrePayItemsEntity {
	
	/**商品ID*/
	private String id;
	
	/**特权订金ID**/
	private String prePayId;
	
	/**产品编码**/
	private String productCode;
	
	/**商品数量*/
	private Integer amount;
	
	/**商品名称*/
	private String productName;
	
	/**单价*/
	private String unitPrice;
	
	/**品类**/
	private String productCategory;

	/**单位**/
	private String unit;

	/**该商品合计金额**/
	private Double totalPrice;


	/**经销商列表状态**/
	private String appStatusofAgency;

	/**门店列表状态*/
	private String appStatusofStore;

	/**特权订金状态**/
	private String prePayStatus;

	public String getAppStatusofAgency() {
		return appStatusofAgency;
	}

	public void setAppStatusofAgency(String appStatusofAgency) {
		this.appStatusofAgency = appStatusofAgency;
	}

	public String getAppStatusofStore() {
		return appStatusofStore;
	}

	public void setAppStatusofStore(String appStatusofStore) {
		this.appStatusofStore = appStatusofStore;
	}

	public String getPrePayStatus() {
		return prePayStatus;
	}

	public void setPrePayStatus(String prePayStatus) {
		this.prePayStatus = prePayStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrePayId() {
		return prePayId;
	}

	public void setPrePayId(String prePayId) {
		this.prePayId = prePayId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
