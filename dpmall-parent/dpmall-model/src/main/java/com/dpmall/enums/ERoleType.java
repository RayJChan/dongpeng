package com.dpmall.enums;

/**
 * 角色类型
 * @author river
 * @date 2017-07-20
 */
public enum ERoleType {
	/**店员*/
	STORE_USER(1,"store_user"),
	/**店长*/
	STORE_MGR(2,"store_mgr"),
	/**经销商*/
	AGENCY(3,"agency");
	
    private Integer value;
	
	private String roleCode;
	
	private ERoleType(Integer value, String roleCode) {
		this.value = value;
		this.roleCode = roleCode;
	}

	public Integer getValue() {
		return value;
	}

	public String getRoleCode() {
		return roleCode;
	}
	
	
}
