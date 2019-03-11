package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IAppUserExcleImportService;
import com.dpmall.datasvr.api.IFirstPageService;
import com.dpmall.model.FirstPageModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AppUserExcleImportFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(AppUserExcleImportFacadeTest.class);
	@Autowired
	private IAppUserExcleImportService appUserExcleImportService;

	@Test
    public void getFirstPageOfAgencyTest(){

		logger.info("=====================getFirstPageOfAgencyTest======================");

		appUserExcleImportService.addApp();

//    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	

	
}
