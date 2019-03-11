package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IOssTokenService;
import com.dpmall.model.OssTokenModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OssTokenServiceFacadeTest extends SpringTestCase {

	private static final Logger LOG = LoggerFactory.getLogger(OssTokenServiceFacadeTest.class);
	@Autowired
	private IOssTokenService ossTokenService;
	
	@Test
    public void getOssTokenOfUserIdTest(){
    	LOG.info("=====================getOssTokenTest====================");
    	OssTokenModel result = ossTokenService.getOssToken("write","8730276180306949");
        LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n\n");
    }
	
}
