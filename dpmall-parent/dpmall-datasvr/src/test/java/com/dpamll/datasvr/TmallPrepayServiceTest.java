package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.ITmallPrepayService;
import com.dpmall.model.in.TmallOrderGoodsModelIn;
import com.dpmall.model.in.TmallOrderInfoModelIn;
import com.dpmall.model.in.TmallOrderPictureModelIn;
import com.dpmall.model.prePay.TmallPrePayDetailModel;
import com.dpmall.model.prePay.TmallPrepayListCountModel;
import com.dpmall.model.prePay.TmallPrePayModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TmallPrepayServiceTest extends SpringTestCase {

	private Logger Logger = LoggerFactory.getLogger(TmallPrepayServiceTest.class);

	@Autowired
	private ITmallPrepayService tmallPrepayService;


	@Test
	public void getListOfAgency() {
		Logger.info("===================getListOfAgency===============");
//		List<TmallPrePayModel> out = tmallPrepayService.getListOfAgency("316158","2",1,10,"","TMC16082900085-1","王瑞","13137970463","2018-03-01 15:10:54","2019-03-02 15:10:54");
		List<TmallPrePayModel> out = tmallPrepayService.getListOfAgency("316158","1",1,10,"","","","","","");
		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
	}
	@Test
	public void getListOfStore() {
		Logger.info("===================getListOfStore===============");
		List<TmallPrePayModel> out = tmallPrepayService.getListOfStore("233233","2",1,10,"","","","","","");
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
		list.add("28256");
//		list.add("8827206666195");
//		int result = tmallPrepayService.accept(list,"1","Y");
//		int result = tmallPrepayService.accept(list,"8796127954936","Y");
		int result = tmallPrepayService.accept(list,"12333","Y");


		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void withdraw(){
		logger.info("=====================withdraw=======================");
//		int result = tmallPrepayService.withdraw("8830270605267",,"Y");
		int result = tmallPrepayService.withdraw("28256","Y");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}
	@Test
	public void getDetails(){
		logger.info("=====================getDetails=======================");
		TmallPrePayDetailModel result = tmallPrepayService.getDetails("36068");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}



	@Test
	public void addRemarks(){
		logger.info("=====================addRemarks====================");

		String result = tmallPrepayService.addRemarks("28256","经销商备注666","","8796224278113");
		logger.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void getO2oIdByConsignmentId(){
		logger.info("=====================addRemarks====================");

		String result = tmallPrepayService.getO2oIdByConsignmentId("8813134710739");
		logger.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}


	@Test
	public void updateOrderProgress(){
		logger.info("=====================updateOrderProgress====================");
		int result = tmallPrepayService.updateOrderProgress("未成交","804","???????????","2974","1","0","3","2019-03-04 16:51:19");
		logger.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void updateTmallOrderInfo(){
		logger.info("=====================updateTmallOrderInfo====================");
		TmallOrderInfoModelIn tmallOrderInfoModelIn = new TmallOrderInfoModelIn();

		List<TmallOrderPictureModelIn> pictures = new ArrayList<>();
		TmallOrderPictureModelIn tmallOrderPictureModelIn = new TmallOrderPictureModelIn();
		tmallOrderPictureModelIn.setPictureType("Other1");
		tmallOrderPictureModelIn.setPictureUrl("dpmall/saleLeadsOrders/8796192427614/1507690652380.jpg");
		pictures.add(tmallOrderPictureModelIn);

		List<TmallOrderGoodsModelIn> orderGoods = new ArrayList<>();
		TmallOrderGoodsModelIn tmallOrderGoodsModelIn = new TmallOrderGoodsModelIn();
		tmallOrderGoodsModelIn.setProductCode("W135105FYQ");
		tmallOrderGoodsModelIn.setProductId("535");
		tmallOrderGoodsModelIn.setProductName("洁卫系列_网络专供_节水王二代1031A座便器齐套白色顶按双档400墙距");
		tmallOrderGoodsModelIn.setCategory("连体座便器");
		tmallOrderGoodsModelIn.setUnit("个");
		tmallOrderGoodsModelIn.setUnitPrice("58.5");
		tmallOrderGoodsModelIn.setAmount("3276");
		tmallOrderGoodsModelIn.setQuantity("56");
		orderGoods.add(tmallOrderGoodsModelIn);

        String a = "15";
        String b = "16";
        List<String> objects = new ArrayList<>();
        objects.add(a);
        objects.add(b);

        tmallOrderInfoModelIn.setPictureNames(pictures);
		tmallOrderInfoModelIn.setTmallOrderGoods(orderGoods);
		tmallOrderInfoModelIn.setDelProductIds(objects);
		tmallOrderInfoModelIn.setTmallOrderId(804L);
		tmallOrderInfoModelIn.setPayPrice("20190307");
		tmallOrderInfoModelIn.setDiscountAmount("19971003");
		tmallOrderInfoModelIn.setOrderEvaluation("5");
		tmallOrderInfoModelIn.setIsLoss("0");
		tmallOrderInfoModelIn.setRevisitTime("2019-03-04 16:51:19");
		tmallOrderInfoModelIn.setRemark("备注");
		tmallOrderInfoModelIn.setOperatorBy("2974");
		int result = tmallPrepayService.updateTmallOrderInfo(tmallOrderInfoModelIn);
		logger.info("\nresult:\n" + JSON.toJSONString(result)+"\n");

	}



/*
	@Test
	public void saveSalOrderInfo(){
		LOG.info("=====================saveSalOrderInfo====================");
		SalOrderPictureModelIn pictureModelIn  = new SalOrderPictureModelIn();
		pictureModelIn.setPictureType("baojia");
		pictureModelIn.setPictureUrl("dpws/showInfo/8796224278113/20180524/272CF2FE-B146-4EC4-A2F7-27659AF8313C.jpg");

		SalOrderPictureModelIn pictureModelIn2  = new SalOrderPictureModelIn();
		pictureModelIn2.setPictureType("qita");
		pictureModelIn2.setPictureUrl("dpws/showInfo/8796224278113/20180524/272CF2FE-B146-4EC4-A2F7-27659AF8313C.jpg");


		SalOrderPictureModelIn pictureModelIn3  = new SalOrderPictureModelIn();
		pictureModelIn3.setPictureType("baojia");
		pictureModelIn3.setPictureUrl("dpws/showInfo/8796224278113/20180524/272CF2FE-B146-4EC4-A2F7-27659AF8313C.jpg");


		SalOrderPictureModelIn pictureModelIn4  = new SalOrderPictureModelIn();
		pictureModelIn4.setPictureType("baojia");
		pictureModelIn4.setPictureUrl("dpws/showInfo/8796224278113/20180524/272CF2FE-B146-4EC4-A2F7-27659AF8313C.jpg");
		List<SalOrderPictureModelIn> pictureList = new ArrayList<>();
		pictureList.add(pictureModelIn);
		pictureList.add(pictureModelIn2);
		pictureList.add(pictureModelIn3);
		pictureList.add(pictureModelIn4);


		List<SalOrderGoodsModelIn> saleLeadsOrderGoods = new ArrayList<>();

		SalOrderGoodsModelIn vo1= new SalOrderGoodsModelIn();
		vo1.setOrderItemId("new");
		vo1.setProductCode("230EPJ99010_A");
		vo1.setProductName("半抛六角砖");
//		vo1.setCategory("木地板");
//		vo1.setUnit("件");
		vo1.setQuantity("0");
		vo1.setAmount("0");

//		saleLeadsOrderGoods.add(vo);
		saleLeadsOrderGoods.add(vo1);
//		saleLeadsOrderGoods.add(insert);


		SalOrderInfoModelIn in  = new SalOrderInfoModelIn();
		in.setSaleLeadsOrderId(86L);
		in.setPictureNames(pictureList);
		in.setSaleLeadsOrderGoods(saleLeadsOrderGoods);
//		in.setRemark("备注信息");
		in.setPayPrice("0");
		in.setDiscountAmount("0");

		int result = saleLeadsOrderService.saveSalOrderInfo(in);
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}*/
}
