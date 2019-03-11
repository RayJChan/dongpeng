package com.dpmall.db;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderItemEntity;
import com.dpmall.db.bean.OrderReturnEntity;
import com.dpmall.db.dao.AppOrderDao;
import com.dpmall.db.dao.UtilsDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UtilsDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(UtilsDaoTest.class);
	
	@Autowired
	private UtilsDao utilsDao;

	@Test
    public void getReturnPriceSumTest(){
		logger.info("=====================getReturnPriceSumTest=======================");
		String result = utilsDao.testconnect();
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	
}
