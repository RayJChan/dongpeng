package com.dpmall.model;

import java.io.Serializable;

public class OssTokenModel implements Serializable {

	private static final long serialVersionUID = -1317883821482747072L;

	/**临时登陆用户名**/
	private String STSAccessKeyId ;
	
	/**临时登陆密码**/
	private String STSAccessKeySecret ;
	
	/**临时登陆凭证**/
	private String STSSecurityToken ;
	
	/**oss库的名称**/
	private String bucketName ;
	
	/**阿里云地址**/
	private String ALiYunHost ;
	
	/**文件夹名称**/
	private String floderName ;
	
	
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getALiYunHost() {
		return ALiYunHost;
	}

	public void setALiYunHost(String aLiYunHost) {
		ALiYunHost = aLiYunHost;
	}

	public String getFloderName() {
		return floderName;
	}

	public void setFloderName(String floderName) {
		this.floderName = floderName;
	}

	public String getSTSAccessKeyId() {
		return STSAccessKeyId;
	}

	public void setSTSAccessKeyId(String sTSAccessKeyId) {
		STSAccessKeyId = sTSAccessKeyId;
	}

	public String getSTSAccessKeySecret() {
		return STSAccessKeySecret;
	}

	public void setSTSAccessKeySecret(String sTSAccessKeySecret) {
		STSAccessKeySecret = sTSAccessKeySecret;
	}

	public String getSTSSecurityToken() {
		return STSSecurityToken;
	}

	public void setSTSSecurityToken(String sTSSecurityToken) {
		STSSecurityToken = sTSSecurityToken;
	}
	
	
	
	
	
	
}
