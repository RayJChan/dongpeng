package com.dpmall.db.bean;

/**
 * 操作人信息

 */
public class OperatorRoleEntity {

	/**角色**/
	private String role;

	/**经销商编码**/
	private String agencyCode;

	/**门店名称**/
	private String storeName;

	/**用户名称**/
	private String userName;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}
