package com.dpmall.db;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.dao.PrePayDao;

public class AppPrePayDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(AppPrePayDaoTest.class); 
	
	@Autowired
	private PrePayDao prePayDao;
	
	/**
     * 特权定金接单下派
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
	public void testDistribute() {
		logger.info("=================distribute====================");
		Integer count = prePayDao.distribute("100","a100015013", "110");
		logger.info(JSON.toJSONString(count));
	}
	
	/**
     * 特权定金接单下派
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
	public void testDistributeO2O() {
		logger.info("=================distribute====================");
		Integer count = prePayDao.distributeO2o("a100015013", "dai");
		logger.info(JSON.toJSONString(count));
	}
	
	
	/**
     * 特权定金导购员状态条数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
	public void testGet2AcceptorCount() {
		logger.info("=================get2AcceptorCount====================");
		Integer count = prePayDao.get2AcceptorCount("46567675", "ABORTED");
		logger.info(JSON.toJSONString(count));
	}
	
}
