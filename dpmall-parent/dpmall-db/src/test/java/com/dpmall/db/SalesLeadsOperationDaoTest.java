package com.dpmall.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.SalesLeadsOperationEntity;
import com.dpmall.db.dao.SalesLeadsOperationDao;

public class SalesLeadsOperationDaoTest extends SpringTestCase{
	
	private final Logger LOG = LoggerFactory.getLogger(SalesLeadsOperationDaoTest.class);
	
	@Autowired
	private  SalesLeadsOperationDao salesLeadsOperationDao;

	@Test
    public void testInsert(){
		SalesLeadsOperationEntity ope = new SalesLeadsOperationEntity();
		ope.operatorBy = "张三";
		ope.operatorDate = new Date();
		ope.operatorDesc = "accept";
		ope.salesLeadsOrder = "1";
		ope.operatorType = "1111";

    	salesLeadsOperationDao.insert(ope);
    }

	@Test
	public void getOperateRemark(){
		List<String> d = new LinkedList<>();
		d.add("S-0000003ZJ1");
		d.add("S-00000044X7");

		LOG.info(JSON.toJSONString(salesLeadsOperationDao.getOperateRemark(d)));
	}


}
