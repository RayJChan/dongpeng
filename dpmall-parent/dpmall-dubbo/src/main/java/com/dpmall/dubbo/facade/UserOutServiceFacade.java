package com.dpmall.dubbo.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.dubbo.api.IUserOutService;
import com.dpmall.model.UserModelOut;


public class UserOutServiceFacade implements IUserOutService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserOutServiceFacade.class);
	
	@Autowired
	private IUserOutService userOutService;

	public UserModelOut getUserById(String userId) {
		if (LOG.isInfoEnabled()) {
			LOG.info("{method:'UserOutServiceFacade::getUserById',in:{userId:'" + userId +"'}}");
		}
		UserModelOut result = userOutService.getUserById(userId);
		if(LOG.isDebugEnabled()){
			LOG.info("{method:'UserOutServiceFacade::getUserById',out:"+JSON.toJSONString(result)+"}");
		}
		return result;
	}


	
	
	
	

}
