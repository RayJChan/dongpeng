package com.dpamll.datasvr;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IPictureService;
import com.dpmall.model.PictureModel;

public class PictureServiceFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(PictureServiceFacadeTest.class);
	@Autowired
	private IPictureService pictureService;

	@Test
    public void getPicturesTest(){

		logger.info("=====================getFirstPageOfAgencyTest======================");
		List<PictureModel> result = pictureService.getPictures(0, 10,"D");
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void uploadPictureTest(){

		logger.info("=====================uploadPictureTest======================");
		int result = pictureService.uploadPicture("22,jpg", "祝福卡内容", "冬至", "C");
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
	
}
