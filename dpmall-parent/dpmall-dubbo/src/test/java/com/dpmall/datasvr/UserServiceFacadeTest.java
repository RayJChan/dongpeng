package com.dpmall.datasvr;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.dubbo.api.IUserOutService;
import com.dpmall.model.UserModelOut;

public class UserServiceFacadeTest extends SpringTestCase {

	private Logger Logger = LoggerFactory.getLogger(UserServiceFacadeTest.class);
	
	
	@Autowired
	private IUserOutService userOutService  ;
	
	@Test
	public void getUserByIdTest() {
		UserModelOut result=userOutService.getUserById("8796224147041");
		logger.info("result:\n"+JSON.toJSONString(result)+"\n");
	}
	
	
}
