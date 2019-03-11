package com.dpmall.db;

import com.alibaba.dubbo.common.utils.Log;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.SalOrderOperationDetailEntity;
import com.dpmall.db.bean.SalesLeadsOrderEntity;
import com.dpmall.db.dao.SalesLeadsOrderDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SalesLeadsOrderDaoTest extends SpringTestCase {

	private static final Logger LOG = LoggerFactory.getLogger(SalesLeadsOrderDaoTest.class);

	@Autowired
	private SalesLeadsOrderDao salesLeadsOrderDao;

	@Test
	public void getOnePage4Agency() {
		logger.info("================getOnePage4Agency====================");
		List<SalesLeadsOrderEntity> result =  salesLeadsOrderDao.getList4AgencyOrStore
				("Y","8796149141050","8796129527800","2",1,10,"D","ACCEPTED");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void getDetails() {
		logger.info("================getDetails====================");
		SalesLeadsOrderEntity result =  salesLeadsOrderDao.getDetails("8797633203806");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void getCountUnOperate() {
		logger.info("================getCountUnOperate====================");
		int  result =  salesLeadsOrderDao.getCountUnOperate("8796126203450");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}


}