package com.dpamll.datasvr;


import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IReportService;
import com.dpmall.model.ReportModel;

public class ReportFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(ReportFacadeTest.class);
	@Autowired
	private IReportService reportService;
	
//	
//	@Test
//	public void orderDistributCountTest() {
//		logger.info("=================orderDistributCountTest====================");
//		Integer count = reportService.orderDistributCount("310145", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//
//	@Test
//	public void orderAgencyAcceptCountTest() {
//		logger.info("=================orderAgencyAcceptCountTest====================");
//		Integer count = reportService.orderAgencyAcceptCount("310145", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//	
//	@Test
//	public void orderStoreAcceptCountTest() {
//		logger.info("=================orderStoreAcceptCountTest====================");
//		Integer count = reportService.orderStoreAcceptCount("8796126054392", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//	
//	
//	@Test
//	public void prePayDistributCountTest() {
//		logger.info("=================prePayDistributCountTest====================");
//		Integer count = reportService.prePayDistributCount("310145", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//	
//	@Test
//	public void prePayAgencyAcceptCountTest() {
//		logger.info("=================prePayAgencyAcceptCountTest====================");
//		Integer count = reportService.prePayAgencyAcceptCount("310145", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//	
//	@Test
//	public void prePayStoreAcceptCountTest() {
//		logger.info("=================prePayStoreAcceptCountTest====================");
//		Integer count = reportService.prePayStoreAcceptCount("8796126054392", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//	
//	
//	@Test
//	public void prePayWriteOffCountTest() {
//		logger.info("=================prePayDistributCountTest====================");
//		Integer count = reportService.prePayWriteOffCount("8796126054392", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(count));
//	}
//	
//	
//	@Test
//	public void prePayStoreAcceptCountOfAgencyTest () {
//		logger.info("=================prePayStoreAcceptCountOfAgencyTest====================");
//		List<ReportModel> result = reportService.prePayStoreAcceptCountOfAgency("310145", "2017-08-01","2017-08-31");
//		logger.info(JSON.toJSONString(result));
//	}
	
	@Test
	public void OrdersOfAgencyTest () {
		logger.info("=================OrdersOfAgencyTest====================");
//		List<String> agencyPks=new ArrayList<String>();
//		agencyPks.add("310145");
//		agencyPks.add("310597");
		try {
			
			List<String> agencyPks =  reportService.getAppGroupInfo("100010");
			logger.info("\n agencyPks:\n"+JSON.toJSONString(agencyPks)+"\n");
			ReportModel result = reportService.OrdersOfAgency("100010", agencyPks, "YEAR", "2017-01-01","2017-12-31");
			System.out.println("-----result:"+result.toString());
			logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void OrdersOfStoreTest () {
		logger.info("=================OrdersOfStoreTest====================");
		try {
			ReportModel result = reportService.OrdersOfStore("M","8796126054392", "2017-10-01","2017-10-31","MONTH");
			System.out.println("-----result:"+result.toString());
			logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void PrepaysOfAgencyTest () {
		logger.info("=================PrepaysOfAgencyTest====================");
//		List<String> agencyPks=new ArrayList<String>();
//		agencyPks.add("310145");
//		agencyPks.add("310597");
		try {
			List<String> agencyPks =  reportService.getAppGroupInfo("dptest");
			ReportModel result = reportService.PrepaysOfAgency(agencyPks,"2018-01-01","2018-01-31","dptest","MONTH");
			System.out.println("-----result:"+result.toString());
			logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void PrepaysOfStoreTest () {
		logger.info("=================PrepaysOfStoreTest====================");
		try {
			ReportModel result = reportService.PrepaysOfStore("8796126054392", "2017-10-01","2017-10-31","MONTH");
			System.out.println("-----result:"+result.toString());
			logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void SalesLeadesOfAgencyTest () {
		logger.info("=================SalesLeadesOfAgencyTest====================");
//		List<String> agencyPks=new ArrayList<String>();
//		agencyPks.add("310145");
//		agencyPks.add("310597");
		try {
			List<String> agencyPks =  reportService.getAppGroupInfo("100010");
			
			ReportModel result = reportService.SalesLeadesOfAgency(agencyPks, "2017-10-01","2017-10-31","100010","QUARTER");
			logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void SalesLeadesOfStoreTest () {
		logger.info("=================SalesLeadesOfStoreTest====================");
		try {
			ReportModel result = reportService.SalesLeadesOfStore("8796126054392", "2017-10-01","2017-12-31","QUARTER");
			System.out.println("-----result:"+result.toString());
			logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAppGroupInfoTest () {
		logger.info("=================SalesLeadesOfStoreTest====================");
		List<String> result = reportService.getAppGroupInfo("zongbu");
		logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
	}
	
	
	
}
