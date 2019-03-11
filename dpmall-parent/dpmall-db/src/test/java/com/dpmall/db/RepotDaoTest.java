package com.dpmall.db;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.ReportDetailEntity;
import com.dpmall.db.bean.ReportEntity;
import com.dpmall.db.dao.ReportDao;

public class RepotDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(RepotDaoTest.class); 
	
	@Autowired
	private ReportDao reportDao;
	
	
	@Test
	public void distributedOrders4AgencyTest() {
		logger.info("================distributedOrders4AgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		list.add("311716");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);

		BigDecimal count = reportDao.distributedOrders4Agency(list,addBig, "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptOrders4AgencyTest() {
		logger.info("================acceptOrders4AgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("a");
		add1.add("b");
		add1.add("c");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
//		addBig.add(add2);
		
		BigDecimal count = reportDao.acceptOrders4Agency(list,addBig,"2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptTimeSum4AgencyTest() throws ParseException {
		logger.info("================acceptTimeSum4AgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		BigDecimal count = reportDao.acceptTimeSum4Agency(list,addBig,  "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void deliveryTimeSum4AgencyTest() throws ParseException {
		logger.info("================deliveryTimeSum4AgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.deliveryTimeSum4Agency(list,addBig, "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	
	
	@Test
	public void distributedOrders4StoreTest() throws ParseException {
		logger.info("================distributedOrders4StoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.distributedOrders4Store("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptOrders4StoreTest() throws ParseException {
		logger.info("================acceptOrders4StoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-10";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.acceptOrders4Store("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptTimeSum4StoreTest() throws ParseException {
		logger.info("================acceptTimeSum4StoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-10";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		
		BigDecimal count = reportDao.acceptTimeSum4Store("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString("count:"+count));
	}
	
	@Test
	public void deliveryTimeSum4StoreTest() throws ParseException {
		logger.info("================deliveryTimeSum4StoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-10";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		
		BigDecimal count = reportDao.deliveryTimeSum4Store("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString("count:"+count));
	}
	
	//---------------------------------------------特权定金-----------------------------------------------
	
	@Test
	public void distributedPrepay4AgencyTest() throws ParseException {
		logger.info("================distributedPrepay4AgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.distributedPrepay4Agency(list, addBig, "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptPrepay4AgencyTest() throws ParseException {
		logger.info("================acceptPrepay4AgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.acceptPrepay4Agency(list,addBig, "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	
	@Test
	public void distributePrepayOfStoreTest() throws ParseException {
		logger.info("================distributePrepayOfStoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.distributePrepayOfStore("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptPrepayOfStoreTest() throws ParseException {
		logger.info("================acceptPrepayOfStoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.acceptPrepayOfStore("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void prePayWriteOffOfAgencyTest() {
		logger.info("================prePayWriteOffOfAgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.prePayWriteOffOfAgency(list,addBig, "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	
	@Test
	public void prePayWriteOffOfStroreTest() throws ParseException {
		logger.info("================prePayWriteOffOfStroreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.prePayWriteOffOfStrore("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void WriteOffTimeSum4AgencyyTest() {
		logger.info("================WriteOffTimeSum4AgencyyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.writeOffTimeSum4Agency(list,addBig,"2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void WriteOffTimeSum4StoreTest() throws ParseException {
		logger.info("================WriteOffTimeSum4StoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.writeOffTimeSum4Store("8796126054392", "2017-07", "2017-08");
		
		logger.info(JSON.toJSONString(count));
	}
	
	//---------------------------------------------留资-----------------------------------------------
	@Test
	public void distributeSalesLeadsOfAgencyTest() throws ParseException {
		logger.info("================distributeSalesLeadsOfAgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal AgencyCount = reportDao.distributeSalesLeadsOfAgency(list,addBig, "2017-07", "2017-08");
		logger.info(JSON.toJSONString("AgencyCount:"+AgencyCount));
	}
	
	
	@Test
	public void acceptSalesLeadsOfAgencyTest() throws ParseException {
		logger.info("================acceptSalesLeadsOfAgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal AgencyCount = reportDao.acceptSalesLeadsOfAgency(list, addBig,"2017-07", "2017-08");
		logger.info(JSON.toJSONString("AgencyCount:"+AgencyCount));
	}
	

	@Test
	public void distributeSalesLeadsOfStoreTest() throws ParseException {
		logger.info("================distributeSalesLeadsOfStoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.distributeSalesLeadsOfStore("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptSalesLeadsOfStoreTest() throws ParseException {
		logger.info("================acceptSalesLeadsOfStoreTest====================");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date enddate=new Date();
		String startdateStr = "2017-09-20";
		Date startdate=sdf.parse(startdateStr);
		System.out.println(startdate);
		BigDecimal count = reportDao.acceptSalesLeadsOfStore("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	
	@Test
	public void salesLeadsSuccessfulCountTest() throws ParseException {
		logger.info("================salesLeadsSuccessfulCountTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count1 = reportDao.salesLeadsSuccessfulCount(list,null,addBig, "2017-07", "2017-08");
		BigDecimal count2 = reportDao.salesLeadsSuccessfulCount(null,"8796126054392",null, "2017-07", "2017-08");
		logger.info(JSON.toJSONString("ageency:"+count1+"store:"+count2));
	}
	
	@Test
	public void salesLeadsSuccessPriceTest() throws ParseException {
		logger.info("================salesLeadsSuccessPriceTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count1 = reportDao.salesLeadsSuccessPrice(list,null, addBig,"2017-07", "2017-08");
		BigDecimal count2 = reportDao.salesLeadsSuccessPrice(null,"8796126054392",null, "2017-07", "2017-08");
		logger.info(JSON.toJSONString("ageency:"+count1+"store:"+count2));
	}
	
	@Test
	public void writeOffPriceOfAgencyTest() throws ParseException {
		logger.info("================writeOffPriceOfAgencyTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.writeOffPriceOfAgency(list, addBig,"2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void writeOffPriceOfStoreTest() throws ParseException {
		logger.info("================writeOffPriceOfStoreTest====================");
		BigDecimal count = reportDao.writeOffPriceOfStore("8796126054392", "2017-07", "2017-08");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void deliveryOrdersCountsTest() throws ParseException {
		logger.info("================deliveryOrdersCountsTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count1 = reportDao.deliveryOrdersCounts(list,null,addBig, "2017-08", "2017-09");
		BigDecimal count2 = reportDao.deliveryOrdersCounts(null,"8796126054392", null,"2017-08", "2017-09");
		logger.info(JSON.toJSONString("ageency:"+count1+"store:"+count2));
	}
	
	@Test
	public void salesLeadsSuccessTimesTest() throws ParseException {
		logger.info("================salesLeadsSuccessTimesTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count1 = reportDao.salesLeadsSuccessTimes(list,null,addBig, "2017-08", "2017-09");
		BigDecimal count2 = reportDao.salesLeadsSuccessTimes(null,"8796126054392",null, "2017-08", "2017-09");
		logger.info(JSON.toJSONString("ageency:"+count1+"store:"+count2));
	}
	
	@Test
	public void distributeTotal4AgencyOfYesTest() throws ParseException {
		logger.info("================totalPrice4AgencyOfYesTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.distributeTotal4AgencyOfYes(list,addBig, "2017-07", "2017-10");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void distributeTotal4AgencyOfNoTest() throws ParseException {
		logger.info("================totalPrice4AgencyOfNoTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.distributeTotal4AgencyOfNo(list,addBig, "2017-07", "2017-10");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptTotal4AgencyOfYesTest() throws ParseException {
		logger.info("================acceptTotal4AgencyOfYesTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.acceptTotal4AgencyOfYes(list,addBig, "2017-07", "2017-10");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptTotal4AgencyOfNoTest() throws ParseException {
		logger.info("================acceptTotal4AgencyOfNoTest====================");
		List<String> list=new ArrayList<String>();
		list.add("310145");
		list.add("310597");
		
		List<String> add1=new ArrayList<String>();
		add1.add("aaaa");
		add1.add("b123");
		add1.add("cssss");
		
		
		List<List<String>> addBig = new ArrayList<List<String>>();
		addBig.add(add1);
		
		BigDecimal count = reportDao.distributeTotal4AgencyOfNo(list,addBig, "2017-07", "2017-10");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void distributeTotal4StoreOfYesTest() throws ParseException {
		logger.info("================distributeTotal4StoreOfYesTest====================");
		BigDecimal count = reportDao.distributeTotal4StoreOfYes("8796126054392", "2017-07", "2017-12");
		logger.info(JSON.toJSONString(count));
	}
	
	
	@Test
	public void distributeTotal4StoreOfNoTest() throws ParseException {
		logger.info("================distributeTotal4StoreOfNoTest====================");
		BigDecimal count = reportDao.distributeTotal4StoreOfNo("8796126054392", "2017-07", "2017-12");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptTotal4StoreOfYesTest() throws ParseException {
		logger.info("================acceptTotal4StoreOfYesTest====================");
		BigDecimal count = reportDao.acceptTotal4StoreOfYes("8796126054392", "2017-07", "2017-12");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void acceptTotal4StoreOfNoTest() throws ParseException {
		logger.info("================acceptTotal4StoreOfNoTest====================");
		BigDecimal count = reportDao.acceptTotal4StoreOfNo("8796126054392", "2017-07", "2017-12");
		logger.info(JSON.toJSONString(count));
	}
	
	@Test
	public void getRoleCodeTest() throws ParseException {
		logger.info("================getRoleCodeTest====================");
		String result = reportDao.getRoleCode("100101");
		logger.info(JSON.toJSONString(result));
	
	}
	
	@Test
	public void getContrastInfoTest() throws ParseException {
		logger.info("================getContrastInfoTest====================");
		ReportDetailEntity result = reportDao.getContrastInfo("100010", "YEAR", "N", "","2016-01-01","2016-12-31");
		logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
	
	}
	
	
	
	
	
}
