package com.dpamll.datasvr;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IPrepayService;

public class PrepayServiceFacadeTest extends SpringTestCase{
	@Autowired
	IPrepayService prepayService;
	
	private final Logger logger =LoggerFactory.getLogger(PrepayServiceFacadeTest.class);
	@Test
	public void get2DistributeCountTest() {
		logger.info(JSON.toJSONString(prepayService.get2DistributeCount("8796125974074", "4")));
	}
	
	@Test
	public void updateOrderTest() {
		logger.info(JSON.toJSONString(prepayService.updateOrder("aSIT100040004", "ALLOCATED", "2333")));
	}
}
