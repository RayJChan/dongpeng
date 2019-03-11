package com.dpmall.db;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.ReportDetailEntity;
import com.dpmall.db.dao.ReportDetailDao;

public class RepotDetailDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(RepotDetailDaoTest.class); 
	
	@Autowired
	private ReportDetailDao  reportDetailDao;
	
	
	@Test
	public void orderHistoryTest() throws ParseException {
		logger.info("================orderHistoryTest====================");
		String format = null;
		String judegement ="YEAR"  ;
		if ("YEAR".equals(judegement)) {//年度
			format = "YYYY";
		}
		if ("MONTH".equals(judegement)) {//月度
			format = "YYYY-MM";
		}
		if ("QUATER".equals(judegement)) {//季度
			format = "YYYY-q";
		}
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		if (format != null) {
			 result = reportDetailDao.orderHistory("310145",format, 0, 10,"2017-12-30");
			
		}
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	@Test
	public void getGroupPKsTest() throws ParseException {
		logger.info("================getGroupPKsTest====================");
		
		List<String> codes = new ArrayList<String>();
		codes.add("100001");
		codes.add("12");
		
		List<String> result = new ArrayList<String>();
		
		result = reportDetailDao.getGroupPKs(codes);
		
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void getGroupPKsOfAgencyTest() {
		logger.info("================getGroupPKsOfAgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		list.add("311716");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<String> result = new ArrayList<String>();
		
		result = reportDetailDao.getGroupPKsOfAgency(list, bigList);
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void getStorePKsOfStoreTest() {
		logger.info("================getStorePKsOfStoreTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		list.add("311716");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<String> result = new ArrayList<String>();
		
		result = reportDetailDao.getStorePKsOfStore(list, bigList);
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	
	
	@Test
	public void getOrderSortTest() {
		logger.info("================getOrderSortTest====================");
		List<String> list=new ArrayList<String>();
		list.add("8796655865459");
		list.add("310597");
		list.add("311716");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("8796650524275");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		result = reportDetailDao.sortInfo(list, bigList,"2017-09", "2017-10", 0, 50,"PD","MONTH","N");
		logger.info(JSON.toJSONString("---result:"+result.toString()));
	}
	
	@Test
	public void getSortAVGTest() {
		logger.info("================getSortAVGTest====================");
		List<String> list=new ArrayList<String>();
		list.add("8796653866611");
		list.add("310597");
		list.add("311716");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("123");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		ReportDetailEntity result = new ReportDetailEntity();
		result = reportDetailDao.getSortAVG(list, bigList,"2017-10", "2017-12","FHAVGTIME","QUARTER","N");
		logger.info(JSON.toJSONString("---result:"+result.toString()));
	}
	
	
	@Test
	public void prePayHistoryTest() throws ParseException {//待测试
		logger.info("================prePayHistoryTest====================");
		String format = null;
		String judegement ="YEAR"  ;
		if ("YEAR".equals(judegement)) {//年度
			format = "YYYY";
		}
		if ("MONTH".equals(judegement)) {//月度
			format = "YYYY-MM";
		}
		if ("QUATER".equals(judegement)) {//季度
			format = "YYYY-q";
		}
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		if (format != null) {
			 result = reportDetailDao.prePayHistory("310145", format, "121212", "N", 0, 10,"2017-12-30");
		}
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	@Test
	public void salePayHistoryTest() throws ParseException {//待测试
		logger.info("================salePayHistoryTest====================");
		String format = null;
		String judegement ="YEAR"  ;
		if ("YEAR".equals(judegement)) {//年度
			format = "YYYY";
		}
		if ("MONTH".equals(judegement)) {//月度
			format = "YYYY-MM";
		}
		if ("QUATER".equals(judegement)) {//季度
			format = "YYYY-q";
		}
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		if (format != null) {
			 result = reportDetailDao.saleHistory("310145", format, "121212", "Y", 0, 10,"2017-12-30");
		}
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	@Test
	public void orderRegionTest() throws ParseException {
		logger.info("================orderRegionTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("11");
		list.add("12");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("12");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		result = reportDetailDao.orderRegion(list, bigList, "N", "Y", "JD", "123", "广东省", 0, 12, "2017-09-01", "2017-09-30");
		
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	
	@Test
	public void prePayRegionTest() throws ParseException {
		logger.info("================prePayRegionTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("11");
		list.add("12");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("12");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		result = reportDetailDao.prePayRegion(list, bigList, "N", "N",  "123", "广东省", 0, 12, "2017-09-01", "2017-09-30");
		
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	@Test
	public void saleRegionTest() {
		logger.info("================saleRegionTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("11");
		list.add("12");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("12");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		result = reportDetailDao.saleRegion(list, bigList, "N", "N",  "123", "广东省", 0, 12, "2017-09-01", "2017-09-30");
		
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	
	@Test
	public void getCategoryTop5Test() {
		logger.info("================getCategoryTop5Test====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("11");
		list.add("12");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("12");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		List<ReportDetailEntity> result = new ArrayList<ReportDetailEntity>();
		result = reportDetailDao.getCategoryTop5(list, bigList, "Y", "PD",  "123",  "2017-09-01", "2017-09-30");
		
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	
	@Test
	public void categoryTop5CountTest() {
		logger.info("================categoryTop5CountTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("11");
		list.add("12");
		
		List<String> add1=new ArrayList<String>();
		add1.add("8796650131059");
		add1.add("12");
		add1.add("123");
		
		
		List<List<String>> bigList = new ArrayList<List<String>>();
		bigList.add(add1);
		
		Long result = reportDetailDao.categoryTop5Count(list, bigList, "Y", "PD",  "123",  "2017-09-01", "2017-09-30");
		
		
		logger.info(JSON.toJSONString(result.toString()));
	
	}
	

	
	
	
	
}
