package com.dpmall.db;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.AppUserEntity;
import com.dpmall.db.dao.AppUserDao;

public class AppUserDaoTest extends SpringTestCase {
	private Logger LOG = LoggerFactory.getLogger(AppUserDaoTest.class);
	
	@Autowired
	private AppUserDao appUserDao;
	
	@Test
	public void testLogin() {
		AppUserEntity entity =appUserDao.login("316413", "14e1b600b1fd579f47433b88e8d85291");
		logger.info("result:"+JSON.toJSONString(entity));
	}
	
	@Test
	public void testChangePassword() {
		Integer result =appUserDao.changePassword("1", "xiecong2","xiecong");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void testGetAgencyUserInfo() {
		AppUserEntity result=appUserDao.getAgencyUserInfo("6");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void testGetStoreUserInfo() {
		AppUserEntity result=appUserDao.getStoreUserInfo("6");
		logger.info("result:"+JSON.toJSONString(result));
	}
	/*
	@Test
	public void  createStoreUserTest() {
		AppUserEntity entity=new AppUserEntity();
		entity.id=5L;
		entity.username="123";
		entity.password="456";
		LOG.info(appUserDao.createStoreUser(entity)+"");
	}
	*/
	@Test
	public void getStoreAllUserTest() {
		
		LOG.info(JSON.toJSONString(appUserDao.getStoreAllUser(2L)));
	}
	
	
	@Test
	public void getStoreAllUserTest2() {
		
		LOG.info(JSON.toJSONString(appUserDao.getStoreAllUser(2L)));
	}
	
	@Test
	public void getAppGroupInfoTest() {
		
		LOG.info(JSON.toJSONString(appUserDao.getAppGroupInfo("310597")));
	}
	
	@Test
	public void getOrderUserInfoTest() {
		
		LOG.info(JSON.toJSONString(appUserDao.getOrderUserInfo("8796224343649")));
	}
	
	@Test
	public void updatePasswordTest() {
		Integer result =appUserDao.updatePassword("cwj","888");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	@Test
	public void getIdByUserNameTest() {
		String result =appUserDao.getIdByUserName("cwj");
		logger.info("result:"+JSON.toJSONString(result));
	}
	
	
	@Test
	public void getUserByUserIdTest() {
		logger.info("\nresult:\n"+JSON.toJSONString(appUserDao.getUserByUserId("8796191477345"))+"\n");
	}

	@Test
	public void checkUsreName() {
		logger.info("\nresult:\n"+JSON.toJSONString(StringUtils.isEmpty(appUserDao.checkUsreName("dddd")))+"\n");

	}





}
