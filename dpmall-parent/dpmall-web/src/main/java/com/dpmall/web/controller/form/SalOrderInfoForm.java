package com.dpmall.web.controller.form;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 */
public class SalOrderInfoForm implements Serializable {

	/**留资单id**/
	private Long saleLeadsOrderId;

	/**图片名称**/
	private List<String> pictureNames;

	/**商品信息**/
	private List<SalOrderGoodsForm> saleLeadsOrderGoods;

	/**备注**/
	private String remark;

	/**总金额(支付金额)**/
	private String payPrice;

	/**操作人**/
	private String operatorBy;


	public Long getSaleLeadsOrderId() {
		return saleLeadsOrderId;
	}

	public void setSaleLeadsOrderId(Long saleLeadsOrderId) {
		this.saleLeadsOrderId = saleLeadsOrderId;
	}

	public List<String> getPictureNames() {
		return pictureNames;
	}

	public void setPictureNames(List<String> pictureNames) {
		this.pictureNames = pictureNames;
	}

	public List<SalOrderGoodsForm> getSaleLeadsOrderGoods() {
		return saleLeadsOrderGoods;
	}

	public void setSaleLeadsOrderGoods(List<SalOrderGoodsForm> saleLeadsOrderGoods) {
		this.saleLeadsOrderGoods = saleLeadsOrderGoods;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

	public String getOperatorBy() {
		return operatorBy;
	}

	public void setOperatorBy(String operatorBy) {
		this.operatorBy = operatorBy;
	}
}
