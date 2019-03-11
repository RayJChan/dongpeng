package com.dpmall.db;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.StatusDefinitionEntity;
import com.dpmall.db.dao.StatusDefinitionDao;

public class StatusDefinitionDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(StatusDefinitionDaoTest.class); 
	
	@Autowired
	private StatusDefinitionDao statusDefinitionDao; 


	@Test
    public void testGetInfoByType(){
		logger.info("=====================testGetInfoByType=======================");
		List<StatusDefinitionEntity> result = statusDefinitionDao.getInfoByType("itemRefund");
		logger.info(JSON.toJSONString(result));
    }
	
}
