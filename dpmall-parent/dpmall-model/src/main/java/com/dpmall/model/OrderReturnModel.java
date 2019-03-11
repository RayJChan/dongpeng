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

	
	
	
	
	
}
