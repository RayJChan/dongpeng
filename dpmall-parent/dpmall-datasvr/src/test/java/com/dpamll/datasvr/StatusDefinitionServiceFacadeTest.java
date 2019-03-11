package com.dpamll.datasvr;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IStatusDefinitionService;
import com.dpmall.model.StatusDefinitionModel;

public class StatusDefinitionServiceFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(StatusDefinitionServiceFacadeTest.class);
	
	
	
	@Autowired
	private IStatusDefinitionService statusDefinitionService;
	
	/**
	  * 获取拒单原因
	  */
	@Test
    public void getInfoByTypeTest(){
		logger.info("=====================getInfoByTypeTest=======================");
		List<StatusDefinitionModel> result = statusDefinitionService.getInfoByType("NoDealReasons");
    	logger.info("\n"+JSON.toJSONString(result)+"\n");
    }
}
