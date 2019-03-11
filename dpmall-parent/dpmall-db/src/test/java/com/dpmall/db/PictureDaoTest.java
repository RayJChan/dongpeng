package com.dpmall.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.DateUtils;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderItemEntity;
import com.dpmall.db.bean.OrderReturnDetailsEntity;
import com.dpmall.db.bean.OrderReturnEntity;
import com.dpmall.db.bean.PictureEntity;
import com.dpmall.db.dao.AppOrderDao;
import com.dpmall.db.dao.PictureDao;

public class PictureDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(PictureDaoTest.class); 
	
	@Autowired
	private PictureDao pictureDao; 
	
	/**
	 * author:crown
	 * getOnePage4Distribute
	 */
	
	@Test
    public void getPictureTest(){
		logger.info("=====================getPictureTest=========================");
    	List<PictureEntity> result = pictureDao.getPicture(0, 10,"C");
    	logger.info("\nresult：\n"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
	public void uploadPictureTest() {
		logger.info("=====================uploadPictureTest=========================");
		PictureEntity entity = new PictureEntity();
		entity.setId(1231l);
		entity.setName("test.png");
    	int result = pictureDao.uploadPicture(entity);
    	logger.info("\nresult：\n"+JSON.toJSONString(result)+"\n");
	}
	
	
	@Test
    public void getHomePagePictureTest(){
		logger.info("=====================getHomePagePictureTest=========================");
    	List<PictureEntity> result = pictureDao.getHomePagePicture();
    	logger.info("\nresult：\n"+JSON.toJSONString(result)+"\n");
    }
	
	
	
}
