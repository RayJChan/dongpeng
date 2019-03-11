package com.dpmall.db;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.ProductEntity;
import com.dpmall.db.dao.ProductDao;

public class ProductDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(ProductDaoTest.class); 
	
	@Autowired
	private ProductDao productDao;
	
	
	@Test
	public void getProductInfoTest() {
		logger.info("=====================getProductInfoTest=======================");
		List<ProductEntity> result = productDao.getProductInfo(0, 10);
		logger.info("\nresult:\n" + JSON.toJSONString(result) + "\n");
	}
	
	@Test
	public void getInfoByProductNumTest() {
		logger.info("=====================getInfoByProductNumTest=======================");
		ProductEntity result = productDao.getInfoByProductCode("FG805378_A");
		logger.info("\nresult:\n" + JSON.toJSONString(result) + "\n");
	}
	
	
}
