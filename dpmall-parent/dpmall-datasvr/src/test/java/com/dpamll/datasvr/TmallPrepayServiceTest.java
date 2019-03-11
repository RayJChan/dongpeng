package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.ITmallPrepayService;
import com.dpmall.model.prePay.TmallPrePayDetailModel;
import com.dpmall.model.prePay.TmallPrepayListCountModel;
import com.dpmall.model.prePay.TmallPrePayModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TmallPrepayServiceTest extends SpringTestCase {

	private Logger Logger = LoggerFactory.getLogger(TmallPrepayServiceTest.class);

	@Autowired
	private ITmallPrepayService tmallPrepayService;


	@Test
	public void getListOfAgency() {
		Logger.info("===================getListOfAgency===============");
		List<TmallPrePayModel> out = tmallPrepayService.getListOfAgency("8796224376378","2",1,10,"");
		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
	}
	@Test
	public void getListOfStore() {
		Logger.info("===================getListOfStore===============");
		List<TmallPrePayModel> out = tmallPrepayService.getListOfStore("8796126054392","2",1,10,"");
		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
	}

	@Test
	public void getListCountOfAgency() {
		Logger.info("===================getListCountOfAgency===============");
		TmallPrepayListCountModel out = tmallPrepayService.getListCountOfAgency("8796132134458");
		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
	}

	@Test
	public void getListCountOfStore() {
		Logger.info("===================getListCountOfStore===============");
		TmallPrepayListCountModel out= tmallPrepayService.getListCountOfStore("8796126054392");
		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
	}


	@Test
	public void accept(){
		logger.info("=====================accept=======================");
		List<String> list = new ArrayList<>();
		list.add("8813122324435");
//		list.add("8827206666195");
//		int result = tmallPrepayService.accept(list,"1","Y");
//		int result = tmallPrepayService.accept(list,"8796127954936","Y");
		int result = tmallPrepayService.accept(list,"1","Y");


		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void withdraw(){
		logger.info("=====================withdraw=======================");
//		int result = tmallPrepayService.withdraw("8830270605267",,"Y");
		int result = tmallPrepayService.withdraw("8830270605267","Y");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}
	@Test
	public void getDetails(){
		logger.info("=====================getDetails=======================");
		TmallPrePayDetailModel result = tmallPrepayService.getDetails("8814845659091");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}



	@Test
	public void addRemarks(){
		logger.info("=====================addRemarks====================");

		String result = tmallPrepayService.addRemarks("8813657360339","经销商备注666","","8796224278113");
		logger.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void getO2oIdByConsignmentId(){
		logger.info("=====================addRemarks====================");

		String result = tmallPrepayService.getO2oIdByConsignmentId("8813134710739");
		logger.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}





}
