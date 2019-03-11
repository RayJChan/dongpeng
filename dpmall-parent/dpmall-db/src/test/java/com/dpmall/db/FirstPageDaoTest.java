package com.dpmall.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.FirstPageEntity;
import com.dpmall.db.dao.AppOrderDao;
import com.dpmall.db.dao.FirstPageDao;

public class FirstPageDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(FirstPageDaoTest.class); 
	
	@Autowired
	private FirstPageDao firstPageDao;
	
	@Test
    public void salesLeadsCountTest(){
		logger.info("=====================salesLeadsCountTest=======================");
		List<String> list = new ArrayList<String>();
		list.add("310145");
		BigDecimal result = firstPageDao.salesLeadsCount(list, null, null, "2017-11-01", "2017-11-30", "3","N");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void orderCountOfAgencyTest(){
		logger.info("=====================orderCountOfAgencyTest=======================");
		List<String> list = new ArrayList<String>();
		list.add("317716");
		BigDecimal result = firstPageDao.orderCountOfAgency(list, null,  "2017-11-01", "2017-11-30", "2");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void orderCountOfStoreTest(){
		logger.info("=====================orderCountOfStoreTest=======================");
		BigDecimal result = firstPageDao.orderCountOfStore( "2017-11-01", "2017-11-30", "2","8796129527800");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }


	@Test
	public void tmallPrepayCountOfAgency(){
		logger.info("=====================prepayCountOfAgencyTest=======================");
		List<String> list = new ArrayList<String>();
		list.add("317716");
		BigDecimal result = firstPageDao.tmallPrepayCountOfAgency(list, null,  "2017-10-01", "2017-10-30", "2");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
	}


	@Test
	public void tmallPrepayCountOfStore(){
		logger.info("=====================tmallPrepayCountOfStore=======================");
		BigDecimal result = firstPageDao.tmallPrepayCountOfStore( "2017-01-01", "2018-08-01", "2","8796126054392");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
	}



	
	@Test
    public void othersPrepayCountOfAgency(){
		logger.info("=====================prepayCountOfAgencyTest=======================");
		List<String> list = new ArrayList<String>();
		list.add("317716");
		BigDecimal result = firstPageDao.othersPrepayCountOfAgency(list, null,  "2017-10-01", "2017-10-30", "2");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void othersPrepayCountOfStore(){
		logger.info("=====================prepayCountOfStoreTest=======================");
		BigDecimal result = firstPageDao.othersPrepayCountOfStore( "2017-11-01", "2017-11-30", "1","8796129527800");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void getHistoryTest(){
		logger.info("=====================getHistoryTest=======================");
		FirstPageEntity result = firstPageDao.getHistory("310145", "2017-08-01", "2017-08-30","N",null);
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void getHistoryStoreTest(){
		logger.info("=====================getHistoryStoreTest=======================");
		FirstPageEntity result = firstPageDao.getHistory(null, "2017-08-01", "2017-08-30","Y","8796129527800");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	
}
