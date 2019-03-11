package com.dpmall.db.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cwj
 * 退货订单
 *
 */
public class OrderReturnEntity {
	
	/**退货总金额**/
	public BigDecimal returnTotalPrice = BigDecimal.ZERO;
	
	/**退货单号**/
	public String returnOderCode ;
	
	/**退货状态**/
	public String returnStatus ;
	
	/**退货单详情**/
	public List<OrderReturnDetailsEntity> returnDetailsList =  new ArrayList<OrderReturnDetailsEntity>();

	public BigDecimal getReturnTotalPrice() {
		return returnTotalPrice;
	}

	public void setReturnTotalPrice(BigDecimal returnTotalPrice) {
		this.returnTotalPrice = returnTotalPrice;
	}

	public String getReturnOderCode() {
		return returnOderCode;
	}

	public void setReturnOderCode(String returnOderCode) {
		this.returnOderCode = returnOderCode;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public List<OrderReturnDetailsEntity> getReturnDetailsList() {
		return returnDetailsList;
	}

	public void setReturnDetailsList(List<OrderReturnDetailsEntity> returnDetailsList) {
		this.returnDetailsList = returnDetailsList;
	}
}
