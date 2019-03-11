package com.dpmall.enums;

/**
 * <p>
 * 销售线索状态
 * @author river
 * @date 2017-07-17
 */
public enum ESaleLeadsOrderStatus {

	/**待下派*/
	DISTRIBUTE_PEND(0,"2distribute"),

	/**拒单*/
	REJECTED(5,"rejected"),

	/**经销商已下派*/
	DISTRIBUTED(10,"distributed"),

	/**已接单*/
	ACCEPTED(15,"accepted"),

	/**已预约*/
	APPOINTED(20,"appointed"),

	/**已到店*/
	VISITED(23,"visited"),

	/**已成交*/
	SUCCESS(25,"sucess"),

	/**已失败*/
	FAILED(30,"failed");

    
	private Integer value;
	
	private String code;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private ESaleLeadsOrderStatus(Integer value, String code) {
		this.value = value;
		this.code = code;
	}
	
}
