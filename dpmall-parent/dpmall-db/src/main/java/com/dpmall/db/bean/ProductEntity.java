package com.dpmall.db.bean;

/**
 * 商品统计表
 * table:T_PRODUCT_STATISTICS
 * @author river
 * @since 2017-06-23
 */
public class ProductEntity {
	
	/**PK**/
    private Long id;
    
    /**产品名称*/
    private String productName;
   
    /**产品型号*/
    private String productCode;
    
    /**产品品类*/
    private String productCategory;

	/**单位**/
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
    
    
    
}
