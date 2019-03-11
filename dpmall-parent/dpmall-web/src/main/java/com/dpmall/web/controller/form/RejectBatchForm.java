package com.dpmall.web.controller.form;

import java.util.List;

public class RejectBatchForm extends RequestForm{
	/**经销商账户ID*/
	public String distributorId;
	/**留资单ID*/
	public List<String> saleLeadsOrderIdList;
	/**拒接类型*/
	public String rejectType;
	/**拒接备注*/
	public String rejectRemark;
}
