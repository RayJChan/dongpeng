package com.dpmall.web.controller.form;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportDetailForm extends RequestForm implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	/**经销商ID**/
	private String agencyId;
	
	/**开始时间**/
	private String startTime;
	
	/**结束时间**/
	private String endTime;
	
	/**门店ID**/
	private String storeId;
	
	/**权限范围编码**/
	private String code;
	
	/**角色编码**/
	private String roleCode ;
	
	/**日期格式**/
	private String dateFormat ;
	
	/**开始页码**/
	private Integer startNum;
	
	private Integer pageSize;
	
	/**查询条件或者登陆账号是否属于门店**/
	private String isStore;
	
	/**需要查询角色以下几级**/
	private Integer index = 1;
	
	/**上一页最后数据序号**/
	private Integer pastRowNum;
	
	/**上一页最后数据结果**/
	private BigDecimal pastResult;
	

	/**省名**/
	private String provinceName;
	
	/**品类**/
	private String category;
	
	/**登陆的token**/
	private String token;
	
	/**登陆的id**/
	private String accessId;
	
	

	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getIsStore() {
		return isStore;
	}

	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getPastRowNum() {
		return pastRowNum;
	}

	public void setPastRowNum(Integer pastRowNum) {
		this.pastRowNum = pastRowNum;
	}

	public BigDecimal getPastResult() {
		return pastResult;
	}

	public void setPastResult(BigDecimal pastResult) {
		this.pastResult = pastResult;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	

	
}
