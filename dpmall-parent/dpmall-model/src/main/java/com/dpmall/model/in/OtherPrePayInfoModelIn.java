package com.dpmall.model.in;

import java.io.Serializable;
import java.util.List;

/**
 * 留资单信息
 */
public class OtherPrePayInfoModelIn implements Serializable {

	/**特权订金id*/
	private String prePayId;

	/**图片名称**/
	private List<SalOrderPictureModelIn> pictureNames;

	/**商品信息**/
	private List<OthersPrePayItemsModelIn> othersPrePayItems;

	/**备注**/
	private String remark;

	/**优惠金额**/
	private String discountPrice;

	/**总金额(支付金额)**/
	private String payPrice;

	/**操作人**/
	private String operatorBy;

	public String getPrePayId() {
		return prePayId;
	}

	public void setPrePayId(String prePayId) {
		this.prePayId = prePayId;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public List<SalOrderPictureModelIn> getPictureNames() {
		return pictureNames;
	}

	public void setPictureNames(List<SalOrderPictureModelIn> pictureNames) {
		this.pictureNames = pictureNames;
	}

	public List<OthersPrePayItemsModelIn> getOthersPrePayItems() {
		return othersPrePayItems;
	}

	public void setOthersPrePayItems(List<OthersPrePayItemsModelIn> othersPrePayItems) {
		this.othersPrePayItems = othersPrePayItems;
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
