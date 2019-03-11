package com.dpmall.model.in;

import java.io.Serializable;
import java.util.List;

/**
 * 留资单信息
 */
public class SalOrderInfoModelIn implements Serializable {

	/**留资单id**/
	private Long saleLeadsOrderId;

	/**图片名称**/
	private List<SalOrderPictureModelIn> pictureNames;

	/**商品信息**/
	private List<SalOrderGoodsModelIn> saleLeadsOrderGoods;

	/**备注**/
	private String remark;

	/**优惠金额**/
	private String discountAmount;

	/**总金额(支付金额)**/
	private String payPrice;

	/**操作人**/
	private String operatorBy;

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getSaleLeadsOrderId() {
		return saleLeadsOrderId;
	}

	public void setSaleLeadsOrderId(Long saleLeadsOrderId) {
		this.saleLeadsOrderId = saleLeadsOrderId;
	}

	public List<SalOrderPictureModelIn> getPictureNames() {
		return pictureNames;
	}

	public void setPictureNames(List<SalOrderPictureModelIn> pictureNames) {
		this.pictureNames = pictureNames;
	}

	public List<SalOrderGoodsModelIn> getSaleLeadsOrderGoods() {
		return saleLeadsOrderGoods;
	}

	public void setSaleLeadsOrderGoods(List<SalOrderGoodsModelIn> saleLeadsOrderGoods) {
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
