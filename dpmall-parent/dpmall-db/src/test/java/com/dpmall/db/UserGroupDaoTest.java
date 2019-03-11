package com.dpmall.db;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.AppGroupEntity;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderItemEntity;
import com.dpmall.db.bean.OrderReturnEntity;
import com.dpmall.db.dao.AppOrderDao;
import com.dpmall.db.dao.UserGroupDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserGroupDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(UserGroupDaoTest.class);
	
	@Autowired
	private UserGroupDao userGroupDao;
	

	@Test
    public void insertAppGroupBatch(){
		logger.info("=====================insertAppGroupBatch=========================");
		AppGroupEntity entity = new AppGroupEntity();
		entity.setCode("11");
		entity.setName("cwj");
		entity.setType("daqu");
		entity.setParentId(66L);

		AppGroupEntity entity2 = new AppGroupEntity();
		entity2.setCode("11");
		entity2.setName("cwj");
		entity2.setType("daqu");
		entity2.setParentId(6666L);

		List<AppGroupEntity> list = new ArrayList<>();
		list.add(entity);
		list.add(entity2);
		int i = userGroupDao.insertAppGroupBatch(list);
    	logger.info(JSON.toJSONString(i));
    }


	@Test
	public void insertAppGroup(){
		logger.info("=====================insertAppGroup=========================");
		AppGroupEntity entity = new AppGroupEntity();
		entity.setCode("11");
		entity.setName("cwj");
		entity.setType("daqu");
		entity.setParentId(66L);

		int i = userGroupDao.insertAppGroup(entity);
		logger.info(JSON.toJSONString(i));
	}




}
