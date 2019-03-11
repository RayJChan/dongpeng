package com.dpamll.datasvr;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IFirstPageService;
import com.dpmall.model.FirstPageModel;

public class FirstPageServiceFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(FirstPageServiceFacadeTest.class);
	@Autowired
	private IFirstPageService firstPageService;

	@Test
    public void getFirstPageOfAgencyTest(){

		logger.info("=====================getFirstPageOfAgencyTest======================");

		FirstPageModel result = firstPageService.getFirstPageOfAgency("310360");

    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void getFirstPageOfStoreTest(){

		logger.info("=====================getFirstPageOfStoreTest======================");
		FirstPageModel result = firstPageService.getFirstPageOfStore("8796129527800");
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
	
}
