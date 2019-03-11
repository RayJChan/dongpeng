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
		list.add("310597");

		BigDecimal result = firstPageDao.salesLeadsCount(list, "11", null, "2019-01-01", "2019-01-30", "1","Y");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }

	@Test
	public void salesLeadsCount_Today(){
		logger.info("=====================salesLeadsCount_Today=======================");
		List<String> list = new ArrayList<String>();
		list.add("310597");

		BigDecimal result = firstPageDao.salesLeadsCount_Today(list, "", null, "2", "N");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
	}




	@Test
    public void orderCountOfAgencyTest(){
		logger.info("=====================orderCountOfAgencyTest=======================");
		List<String> list = new ArrayList<String>();
		list.add("317716");
		BigDecimal result = firstPageDao.orderCount(list, null,  "2017-11-01", "2017-11-30", "2","","Y");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	@Test
    public void orderCountOfStoreTest(){
		logger.info("=====================orderCountOfStoreTest=======================");
		BigDecimal result = firstPageDao.orderCount(null,null, "2017-11-01", "2017-11-30", "2","8796129527800","N");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }


	@Test
	public void tmallPrepayCountOfAgency(){
		logger.info("=====================prepayCountOfAgencyTest=======================");
		List<String> list = new ArrayList<String>();
		list.add("317716");
		BigDecimal result = firstPageDao.tmallPrepayCount(list, null,  "2017-10-01", "2017-10-30", "2","","Y");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
	}


	@Test
	public void tmallPrepayCountOfStore(){
		logger.info("=====================tmallPrepayCountOfStore=======================");
		BigDecimal result = firstPageDao.tmallPrepayCount(null, null,  "2017-10-01", "2017-10-30", "2","8796126054392","N");

		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void tmallPrepayCount_Today(){
		logger.info("=====================tmallPrepayCount_Today=======================");
		BigDecimal result = firstPageDao.tmallPrepayCount_Today(null, null,  "2","8796126054392","N");

		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
	}




	
//	@Test
//    public void othersPrepayCountOfAgency(){
//		logger.info("=====================prepayCountOfAgencyTest=======================");
//		List<String> list = new ArrayList<String>();
//		list.add("317716");
//		BigDecimal result = firstPageDao.othersPrepayCountOfAgency(list, null,  "2017-10-01", "2017-10-30", "2");
//		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
//    }
//
//	@Test
//    public void othersPrepayCountOfStore(){
//		logger.info("=====================prepayCountOfStoreTest=======================");
//		BigDecimal result = firstPageDao.othersPrepayCountOfStore( "2017-11-01", "2017-11-30", "1","8796129527800");
//		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
//    }
	
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
