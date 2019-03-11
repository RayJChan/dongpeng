package com.dpmall.web.controller.form;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 */
public class SalOrderRecoreForm implements Serializable {


	/**图片名称**/
	private List<String> orderCodeList;


	public List<String> getOrderCodeList() {
		return orderCodeList;
	}

	public void setOrderCodeList(List<String> orderCodeList) {
		this.orderCodeList = orderCodeList;
	}
}
