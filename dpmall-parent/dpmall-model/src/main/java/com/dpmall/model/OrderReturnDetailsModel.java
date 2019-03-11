package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cwj
 * o2o订单
 *
 */
public class OrderReturnDetailsModel implements Serializable {
	
	private static final long serialVersionUID = 7091512392910799710L;

	/**商品编码**/
	public String productCode;
	
	/**品类**/
	public String category;
	
	/**数量**/
	public String returnQuantity;
	
	/**退款总价**/
	public BigDecimal returnPayAmount;
	
	/**商品名称**/
	public String productName;
	
	
	/**退货单号**/
	public String returnOderCode ;
	
	/**单位**/
	public String unit;
	
	/**判断是否退货**/
	public String returnCheck;
	
	/**退货状态**/
	public String returnStatus ;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public BigDecimal getReturnPayAmount() {
		return returnPayAmount;
	}

	public void setReturnPayAmount(BigDecimal returnPayAmount) {
		this.returnPayAmount = returnPayAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReturnOderCode() {
		return returnOderCode;
	}

	public void setReturnOderCode(String returnOderCode) {
		this.returnOderCode = returnOderCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReturnCheck() {
		return returnCheck;
	}

	public void setReturnCheck(String returnCheck) {
		this.returnCheck = returnCheck;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
}
