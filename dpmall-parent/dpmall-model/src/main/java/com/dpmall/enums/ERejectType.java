package com.dpmall.enums;

/**
 * <p>
 * 拒单类型
 * @author linjiashun
 * @date 2017-07-25
 */
public enum ERejectType {
	/**无货*/
	SOLD_OUT(1,"soldOut"),
	/**利润低*/
	LOW_PROFIT(2,"lowProfit"),
	/**其它*/
	OTHER(3,"other");
	
	private Integer value;
	
	private String code;
	
	private ERejectType(Integer value,String code){
		this.value = value;
		this.code = code;
	}

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
}