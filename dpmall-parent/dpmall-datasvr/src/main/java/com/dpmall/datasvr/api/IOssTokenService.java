package com.dpmall.datasvr.api;


import com.dpmall.model.OssTokenModel;

public interface IOssTokenService {

	/**
	 * 获取图片服务器oss 的临时登陆凭证（需要操作人id）
	 */
	public OssTokenModel getOssToken(String readOrWrite, String operatorBy);
	
	
}
