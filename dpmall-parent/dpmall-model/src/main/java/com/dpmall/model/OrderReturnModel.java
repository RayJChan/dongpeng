package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author cwj
 * o2o订单
 *
 */
public class OrderReturnModel implements Serializable {
	
	private static final long serialVersionUID = 7091512392910799710L;


	/**退货总价格**/
	public BigDecimal returnTotalPrice = BigDecimal.ZERO;
	
	/**退货单号**/
	public String returnOderCode ;
	
	/**是否显示勾选框**/
	public String isShowTick = "N";
	
	/**该退货单是否已经通过审核**/
	public String isPass = "N";
	
	/**退货状态**/
	public String returnStatus ;
	
	/**退货单详情**/
	public List<OrderReturnDetailsModel> returnDetailsList;


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

	public String getIsShowTick() {
		return isShowTick;
	}

	public void setIsShowTick(String isShowTick) {
		this.isShowTick = isShowTick;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public List<OrderReturnDetailsModel> getReturnDetailsList() {
		return returnDetailsList;
	}

	public void setReturnDetailsList(List<OrderReturnDetailsModel> returnDetailsList) {
		this.returnDetailsList = returnDetailsList;
	}
}
