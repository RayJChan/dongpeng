package com.dpmall.model.in;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 特权订单信息
 */
public class TmallOrderInfoModelIn implements Serializable {

	/**特权单id**/
	private Long tmallOrderId;

	/**图片名称**/
	private List<TmallOrderPictureModelIn> pictureNames;

	/**商品信息**/
	private List<TmallOrderGoodsModelIn> tmallOrderGoods;

	/**备注**/
	private String remark;

	/**优惠金额**/
	private String discountAmount;

	/**总金额(支付金额)**/
	private String payPrice;

	/**操作人**/
	private String operatorBy;

	/**
	 * 订单评价
	 */
	private String orderEvaluation;

	/**
	 * 流失情况
	 */
	private String isLoss;

	/**
	 * 回访时间
	 */
	private String revisitTime;

	/**
	 *删除商品ID
	 */
	private List<String> delProductIds;

	public List<String> getDelProductIds() {
		return delProductIds;
	}

	public void setDelProductIds(List<String> delProductIds) {
		this.delProductIds = delProductIds;
	}

	public String getOrderEvaluation() {
		return orderEvaluation;
	}

	public void setOrderEvaluation(String orderEvaluation) {
		this.orderEvaluation = orderEvaluation;
	}

	public String getIsLoss() {
		return isLoss;
	}

	public void setIsLoss(String isLoss) {
		this.isLoss = isLoss;
	}

	public String getRevisitTime() {
		return revisitTime;
	}

	public void setRevisitTime(String revisitTime) {
		this.revisitTime = revisitTime;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getTmallOrderId() {
		return tmallOrderId;
	}

	public void setTmallOrderId(Long tmallOrderId) {
		this.tmallOrderId = tmallOrderId;
	}

	public List<TmallOrderPictureModelIn> getPictureNames() {
		return pictureNames;
	}

	public void setPictureNames(List<TmallOrderPictureModelIn> pictureNames) {
		this.pictureNames = pictureNames;
	}

	public List<TmallOrderGoodsModelIn> getTmallOrderGoods() {
		return tmallOrderGoods;
	}

	public void setTmallOrderGoods(List<TmallOrderGoodsModelIn> tmallOrderGoods) {
		this.tmallOrderGoods = tmallOrderGoods;
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
