package com.dpmall.model;

import java.io.Serializable;

/**
 * @author cwj
 * 产品信息
 *
 */
public class ProductModel implements Serializable {
	
	private static final long serialVersionUID = -6061060375292440732L;

	/**ID**/
	private Long id;
		
	/**产品品类**/
	private String productCategory;
	
	/**产品名称**/
	private String productName;
	
	/**产品编码**/
	private String productCode;

	/**单位**/
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
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
	
	
	
}
