package com.dpamll.datasvr;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IReportDetailService;
import com.dpmall.datasvr.serviceImpl.ReportDetailServiceImpl;
import com.dpmall.model.ReportDetailModel;

public class ReportDetailFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(ReportDetailFacadeTest.class);

	@Autowired
	private IReportDetailService reportDetailService;
	
	
	@Test
	public void OrderDistributedHistoryTest () {
		logger.info("=================OrderDistributedHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
			 result = reportDetailService.orderDistributedHistory("100010","MONTH", 0, 999,"2017-09-30");
		
		logger.info(JSON.toJSONString(result.toString()));
		
	}
	
	@Test
	public void orderAcceptHistoryTest () {
		logger.info("=================orderAcceptHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
			 result = reportDetailService.orderAcceptHistory("310145","MONTH", 0, 999,"2017-09-30");
		
		logger.info(JSON.toJSONString(result.toString()));
		
	}
	
	@Test
	public void orderAcceptAVGHistoryTest () {
		logger.info("=================orderAcceptAVGHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
			 result = reportDetailService.orderAcceptAVGHistory("310145","MONTH", 0, 999,"2017-09-30");
		
		logger.info(JSON.toJSONString(result.toString()));
		
	}
	
	@Test
	public void orderAcceptRateHistoryTest () {
		logger.info("=================orderAcceptRateHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
			 result = reportDetailService.orderAcceptRateHistory("310145","MONTH", 0, 999,"2017-09-30");
		
		logger.info(JSON.toJSONString(result.toString()));
		
	}
	
	@Test
	public void orderDeliverAVGTimeHistoryTest () {
		logger.info("=================orderDeliverAVGTimeHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
			 result = reportDetailService.orderDeliverAVGTimeHistory("310145","MONTH", 0, 999,"2017-09-30");
		
		logger.info(JSON.toJSONString(result.toString()));
		
	}
	
	
	
	@Test
	public void getGroupPKsTest () {
		logger.info("=================orderAcceptHistoryTest====================");
		List<String> result = new ArrayList<String>();
		ReportDetailServiceImpl serviceImpl = new ReportDetailServiceImpl();
			 result = serviceImpl.getGroupPKs("100010", 2, "agency");
		logger.info(JSON.toJSONString(result.toString()));
		
	}
	
	@Test
	public void orderDistributedSortTest () {
		logger.info("=================orderDistributedSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderDistributedSort("100012", 1, "zigongsi", "2017-09-01", "2017-09-31", 0, 12,"MONTH",0,new BigDecimal(0));
 		/*
 		ReportDetailModel model = new ReportDetailModel();
 		String avg = "50";
 		if ((Double.valueOf(avg)) > (Double.valueOf(result.get(result.size()-1).result))) {
 			model.groupName  = "平均值";
 	 		model.result = avg;
 	 		result.add(model);
 		}
 		 Collections.sort(result, new Comparator<ReportDetailModel>(){//根据派单值paixu
             public int compare(ReportDetailModel o1, ReportDetailModel o2) {
                 if((Double.valueOf(o1.result))>(Double.valueOf(o2.result))){
                     return 1;
                 }
                 if((Double.valueOf(o1.result))==(Double.valueOf(o2.result))){
                     return 0;
                 }
                 return -1;
             }  
         });
 		 Collections.reverse(result);//倒序
 		 
 		for (int i =0;i<3;i++) {//前3有“平均值”，删除“平均值”
			ReportDetailModel isAvg = result.get(i);
			if ("平均值".equals(isAvg.groupName)) {
				result.remove(i);
			}
 		}*/
			 
		logger.info(JSON.toJSONString(result));
		
	}
	
	@Test
	public void orderDistributeAVGSortTest () {
		logger.info("=================orderDistributeAVGSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderDistributeAVGSort("100010", 2, "zongbu", "2017-09-01", "2017-09-31", 12, 12,"MONTH",12,new BigDecimal(58));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	
	@Test
	public void orderAcceptRateSortTest () {
		logger.info("=================orderAcceptRateSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderAcceptRateSort("100010", 1, "zongbu", "2017-09", "2017-10", 0, 10,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void orderDeliverAVGTimeSortTest () {
		logger.info("=================orderDeliverAVGTimeSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		

 		result = reportDetailService.orderDeliverAVGTimeSort("100001", 4, "dailishang", "2017-10-01", "2017-10-31", 12, 12,"MONTH",12,new BigDecimal(560437));

			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void orderAcceptAVGSortTest () {
		logger.info("=================orderAcceptAVGSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderAcceptAVGSort("100010", 2, "xianzu", "2017-09-01", "2017-09-30", 12, 12,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffAVGSortTest () {
		logger.info("=================prepayWriteOffAVGSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffAVGSort("100010", 1, "zongbu", "2017-07", "2017-10", 0, 10,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffRateSortTest () {
		logger.info("=================prepayWriteOffRateSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffRateSort("100001", 1, "zongbu", "2017-07", "2017-10", 0, 10,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffAVGTimeSortTest () {
		logger.info("=================prepayWriteOffAVGTimeSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffAVGTimeSort("100001", 1, "mendian", "2017-09-01", "2017-09-31", 0, 10,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	
	@Test
	public void saleConversionAVGSortTest () {
		logger.info("=================saleConversionAVGSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionAVGSort("100001", 1, "zongbu", "2017-07", "2017-10", 0, 10,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void saleConversionRateSortTest () {
		logger.info("=================saleConversionRateSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionRateSort("100001", 1, "zongbu", "2017-07", "2017-10", 0, 10,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void saleConversionAVGTimeSortTest () {
		logger.info("=================saleConversionAVGTimeSortTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionAVGTimeSort("100010", 2, "zongbu", "2017-09-01", "2017-09-31",0, 12,"MONTH",9,new BigDecimal(10));
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	
	@Test
	public void prepayDistributeHistoryTest () {
		logger.info("=================prepayDistributeHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayDistributeHistory("100010", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffCountHistoryTest () {
		logger.info("=================prepayWriteOffCountHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffCountHistory("100010", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffAVGHistoryTest () {
		logger.info("=================prepayWriteOffAVGHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffAVGHistory("100010", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffRateHistoryTest () {
		logger.info("=================prepayWriteOffRateHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffRateHistory("100010", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void prepayWriteOffAVGTimeHistoryTest () {
		logger.info("=================prepayWriteOffAVGTimeHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prepayWriteOffAVGTimeHistory("100010", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void saleConversionCountHistoryTest () {
		logger.info("=================saleConversionCountHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionCountHistory("100001", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void saleConversionAVGHistoryTest () {
		logger.info("=================saleConversionAVGHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionAVGHistory("100001", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void saleConversionRateHistoryTest () {
		logger.info("=================saleConversionRateHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionRateHistory("100001", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void saleConversionAVGTimeHistoryTest () {
		logger.info("=================saleConversionAVGTimeHistoryTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleConversionAVGTimeHistory("100001", "MONTH", "1233333", "N", 0, 999,"2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void orderDistributeProvinceRegionTest () {
		logger.info("=================orderDistributeProvinceRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderDistributeProvinceRegion("100001", "N","","2017-09-01", "2017-09-30", 0, 12, new BigDecimal(0), 0);
			 
		logger.info(JSON.toJSONString(result.toString()));
	}

	@Test
	public void orderDistributeCityRegionTest () {
		logger.info("=================orderDistributeCityRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderDistributeCityRegion("100001", "广东省","N","","2017-09-01", "2017-09-30", 0, 12, new BigDecimal(0), 0);
			 
		logger.info(JSON.toJSONString(result.toString()));
	}	
	
	@Test
	public void orderAcceptProvinceRegionTest () {
		logger.info("=================orderAcceptProvinceRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderAcceptProvinceRegion("100001", "N","","2017-09-01", "2017-09-30", 0, 12, new BigDecimal(0), 0);
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void orderAcceptCityRegionTest () {
		logger.info("=================orderAcceptCityRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.orderAcceptCityRegion("100001", "广东省","N","","2017-09-01", "2017-09-30", 0, 12, new BigDecimal(0), 0);
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	
	@Test
	public void prePayProvinceRegionTest () {
		logger.info("=================prePayProvinceRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prePayProvinceRegion("100001", "N", null, "2018-01-01", "2018-01-31", 0, 20, new BigDecimal(0), 0);
			 
		logger.info("\n result: \n"+JSON.toJSONString(result)+"\n");
	}

	
	@Test
	public void prePayCityRegionTest () {
		logger.info("=================prePayCityRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.prePayCityRegion("100001", "广东省","N","","2018-01-01", "2018-01-30", 0, 12, new BigDecimal(0), 0);

			 
 		logger.info("\n result: \n"+JSON.toJSONString(result)+"\n");
	}
	
	@Test
	public void saleProvinceRegionTest () {
		logger.info("=================saleProvinceRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleProvinceRegion("100001", "Y","232","2017-09-01", "2017-09-30", 0, 12, new BigDecimal(0), 0);
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	
	@Test
	public void saleCityRegionTest () {
		logger.info("=================saleCityRegionTest====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.saleCityRegion("100001", "广东省","N","","2017-08-01", "2017-09-30", 0, 12, new BigDecimal(0), 0);
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void distributeCategoryTop5Test () {
		logger.info("=================distributeCategoryTop5Test====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.distributeCategoryTop5("100001", "CZ", "N", "", "2017-09-01", "2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
	
	@Test
	public void acceptCategoryTop5Test () {
		logger.info("=================acceptCategoryTop5Test====================");
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
 		result = reportDetailService.acceptCategoryTop5("100001", "CZ", "Y", "2", "2017-08-01", "2017-09-30");
			 
		logger.info(JSON.toJSONString(result.toString()));
	}
}
