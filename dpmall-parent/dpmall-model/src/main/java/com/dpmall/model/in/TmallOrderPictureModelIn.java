package com.dpmall.model.in;

import java.io.Serializable;

/**
 * 特权订单信息
 */
public class TmallOrderPictureModelIn implements Serializable {

	/**
	 * 特权图片
	 **/
	private String pictureUrl;

	/**
	 * 特权图片
	 **/
	private String pictureType;

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPictureType() {
		return pictureType;
	}

	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}
}
