package com.dpmall.datasvr;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.dubbo.api.IProductService;
import com.dpmall.model.ProductModel;

public class ProductServiceFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceFacadeTest.class);
	
	@Autowired
	private IProductService productService;

	@Test
    public void getProductInfoTest(){
		logger.info("=====================getProductInfoTest======================");
		List<ProductModel> result = productService.getProductInfo(0, 10);
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void getInfoByProductCodeTest(){
		logger.info("=====================getInfoByProductCodeTest======================");
		ProductModel result = productService.getInfoByProductCode("FG805343_A");
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
}
