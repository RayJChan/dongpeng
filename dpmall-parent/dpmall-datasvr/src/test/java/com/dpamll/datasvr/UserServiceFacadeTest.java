package com.dpamll.datasvr;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IUserService;
import com.dpmall.model.AppGroupModel;
import com.dpmall.model.LoginResModel;
import com.dpmall.model.UserModel;

public class UserServiceFacadeTest extends SpringTestCase {

	private Logger Logger = LoggerFactory.getLogger(UserServiceFacadeTest.class);
	
	@Autowired
	private IUserService userService;

	@Test
	public void test() {
		Logger.info(JSON.toJSONString(userService.getStoreAllUser(2L)));
	}
	
	@Test
	public void testLogin() {
		logger.info("result:"+JSON.toJSONString(userService.login("100001", "14e1b600b1fd579f47433b88e8d85291")));
	}
	
	@Test
	public void testGetUserInfo() {
		UserModel result=userService.getUserInfo("2", "6");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void testGetAppGroupInfo() {
		AppGroupModel result=userService.getAppGroupInfo("310597");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void updatePasswordTest() {
		Integer result=userService.updatePassword("316052", "999","18957312221");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void getIdByUserNameTest() {
		String result=userService.getIdByUserName("cwj");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void loginTest() {
		LoginResModel result=userService.login("316052",null);
		logger.info("result:"+JSON.toJSONString(result));
	}

}
