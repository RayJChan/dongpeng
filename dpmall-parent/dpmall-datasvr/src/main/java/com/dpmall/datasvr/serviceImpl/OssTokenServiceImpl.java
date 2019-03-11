package com.dpmall.datasvr.serviceImpl;

import com.dpmall.common.OssStsUtils;
import com.dpmall.common.TimeFormatUtils;
import com.dpmall.datasvr.api.IOssTokenService;
import com.dpmall.model.OssTokenModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("ossTokenService")
public class OssTokenServiceImpl implements IOssTokenService {

	private OssTokenModel mapToModel(Map<String, String> resultMap) {
		OssTokenModel model = new OssTokenModel();
		model.setSTSAccessKeyId(resultMap.get("STSAccessKeyId")); 
		model.setSTSAccessKeySecret(resultMap.get("STSAccessKeySecret")); 
		model.setSTSSecurityToken(resultMap.get("STSSecurityToken")); 
		model.setBucketName(resultMap.get("bucketName"));
		model.setALiYunHost(resultMap.get("endpoint"));
		return model;
	}


	/**
	 * 获取图片服务器oss 的临时登陆凭证
	 */
	public OssTokenModel getOssToken(String readOrWrite, String operatorBy) {
		OssStsUtils ossStsUtils = new OssStsUtils();
		OssTokenModel model = new OssTokenModel();
		Map<String, String> resultMap = new HashMap<String, String>();
		//获取临时登陆信息
		resultMap = ossStsUtils.getOssTemporary(readOrWrite);
		if (resultMap != null) {
			model = this.mapToModel(resultMap);
			
			String time = TimeFormatUtils.timeFormate2(new Date());
			StringBuffer bufferFloderName = new StringBuffer();
			bufferFloderName.append("dpmall/saleLeadsOrders");
			bufferFloderName.append("/"+operatorBy);
			bufferFloderName.append("/"+time);
			model.setFloderName(bufferFloderName.toString());
		}
		return model;
	}
	
	
	

}
