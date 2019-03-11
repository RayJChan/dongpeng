package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dpmall.common.DateUtils;
import com.dpmall.common.SpringTestCase;
import com.dpmall.common.TimeScope;
import com.dpmall.datasvr.api.ISaleLeadsOrderService;
import com.dpmall.datasvr.api.ISaleLeadsService;
import com.dpmall.model.*;
import com.dpmall.model.in.SalOrderGoodsModelIn;
import com.dpmall.model.in.SalOrderInfoModelIn;
import com.dpmall.model.in.SalOrderPictureModelIn;
import com.dpmall.model.in.SaleLeadOrderModelIn;
import com.dpmall.param.SaleLeadStatisticParam;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public class SaleLeadsOrderServiceFacadeTest extends SpringTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(SaleLeadsOrderServiceFacadeTest.class);
    
    @Autowired
    private ISaleLeadsOrderService saleLeadsOrderService;

	@Test
	public void getList4AgencyOfUnHandle(){
		LOG.info("=====================getList4AgencyOfUnHandle====================");
		List<SaleLeadsOrderModel> result = saleLeadsOrderService.getList4AgencyOfUnHandle("8100",1,10,"徐梦蕾");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n\n");
	}

    @Test
    public void getList4Agency(){
		LOG.info("=====================getList4Agency====================");
    	List<SaleLeadsOrderModel> result = saleLeadsOrderService.getList4Agency("317716","3",1,10,"","LZ201901156985","DD","111","2018-01-01 00:00:00.000","2019-03-01 15:10:54","ACCEPTED");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n\n");
    }

	@Test
	public void getList4Store(){
		LOG.info("=====================getList4Store====================");
		List<SaleLeadsOrderModel> result = saleLeadsOrderService.getList4Store("123","2",1,10,"","","导入姓名11","","2018-01-01 00:00:00.000","2019-03-05 15:10:54","");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void getListConut4Agency(){
		LOG.info("=====================getListConut4Agency====================");
		SalOrderListCountModel result = saleLeadsOrderService.getListConut4Agency("317716");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n\n");
	}

	@Test
	public void getListConut4Store(){
		LOG.info("=====================getListConut4Store====================");
		SalOrderListCountModel  result =  saleLeadsOrderService.getListConut4Store("8796127954936");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}


	@Test
	public void reject(){
		LOG.info("=====================reject====================");
		List<String> idList  = new ArrayList<>();
		idList.add("84");
//		idList.add("8797633203806");
		int result = saleLeadsOrderService.reject(idList,"8796093107839","不要了","372");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void agencyAccept(){
		LOG.info("=====================agencyAccept====================");
		List<String> list = new ArrayList<>();
		list.add("88");
//		list.add("8797698707038");

		int result = saleLeadsOrderService.agencyAccept(list,"123","好好处理","372");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void storeAccept(){
		LOG.info("=====================storeAccept====================");
		List<String> idList  = new ArrayList<>();
		idList.add("86");
//		idList.add("8797633203806");

		int result = saleLeadsOrderService.storeAccept(idList,"门店已处理啦啦","372");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}


	@Test
	public void getDetails(){
		LOG.info("=====================getDetails====================");


		SaleLeadsOrderDetailsModel result = saleLeadsOrderService.getDetails("86","372");//8797633203806

		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void addRemarks(){
		LOG.info("=====================addRemarks====================");

		String result = saleLeadsOrderService.addRemarks("88","经销商备注666","321","372");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}

	@Test
	public void updateCustomerInfo(){
		LOG.info("=====================updateCustomerInfo====================");
		SaleLeadsOrderModel model =new SaleLeadsOrderModel();
		model.setId(86L);
		model.setClientName("testName");
		model.setClientTel("13112717333");
		model.setServerRegion("广东省");
		model.setServerCity("佛山市");
		model.setServerDistrict("禅城区");
		model.setServerAddress("东鹏总部");
		model.setClientType("家装");
		model.setDecorateSpace("厨房");
		model.setFitmentTime("两个月后");

		model.setStylePreference("中式");
		model.setBudget("200000");
		model.setArea("120平方");
		model.setVillage("碧桂园");
		model.setProductPreference("天山碧玉");
		model.setCustBenefit("到店消费10000送12方阳台砖");
		int result = saleLeadsOrderService.updateCustomerInfo(model,"372");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}


	@Test
	public void updateOrderProgress(){
		LOG.info("=====================updateOrderProgress====================");
		SaleLeadOrderModelIn in = new SaleLeadOrderModelIn();
		in.statusName = "邀约失败";
		in.saleLeadsOrderId = 86L;
		in.remark = "邀约失败";
		in.operatorBy = "372";
		in.orderEvaluation = "3";
		in.isLoss = "0";
		in.revisitTime = "2019-01-01";

		int result = saleLeadsOrderService.updateOrderProgress(in);
		LOG.info("\nresult:\n" + JSON.toJSONString(result) + "\n");
	}

	@Test
	public void updateSalOrderInfo(){
		LOG.info("=====================updateSalOrderInfo====================");
		List<String> pictures = new ArrayList<>();
		pictures.add("1111.jpg");
		pictures.add("222.jpg");
		pictures.add("333.jpg");

		List<SalOrderGoodsModelIn> saleLeadsOrderGoods = new ArrayList<>();
		SalOrderGoodsModelIn vo= new SalOrderGoodsModelIn();
//		vo.setOrderItemId("2");
//		vo.setProductCode("800EFG11010_A");
//		vo.setProductName("天山碧玉");
//		vo.setCategory("抛釉砖");
//		vo.setUnit("件");
//		vo.setQuantity("111");
//		vo.setAmount("6666");
//
		SalOrderGoodsModelIn vo1= new SalOrderGoodsModelIn();
		vo1.setOrderItemId("new");
		vo1.setProductCode("230EPJ99010_A");
		vo1.setProductName("半抛六角砖");
		vo1.setCategory("木地板");
		vo1.setUnit("件");
		vo1.setQuantity("22");
		vo1.setAmount("6666");
//
//		SalOrderGoodsModelIn insert= new SalOrderGoodsModelIn();
//		insert.setOrderItemId("6");
//		insert.setProductCode("800EFG11010_B");
//		insert.setProductName("天山碧玉");
//		insert.setCategory("抛釉砖");
//		insert.setUnit("件");
//		insert.setQuantity("33");
//		insert.setAmount("777");
//
//		saleLeadsOrderGoods.add(vo);
		saleLeadsOrderGoods.add(vo1);
//		saleLeadsOrderGoods.add(insert);




		SalOrderInfoModelIn in  = new SalOrderInfoModelIn();
		in.setSaleLeadsOrderId(89L);
		in.setOperatorBy("372");
//		in.setPictureNames(pictures);
		in.setSaleLeadsOrderGoods(saleLeadsOrderGoods);
		in.setRemark("备注信息");
		in.setPayPrice("60000");

		int result = saleLeadsOrderService.updateSalOrderInfo(in);
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
	}




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
	}
	@Test
	public void getHistory4Oms(){
		List<SalesLeadsOperationModel> result = saleLeadsOrderService.getHistory4Oms("s","","2018-06-20","2018-06-28",1,10);
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");

	}

	@Test
	public void getSalOrderItem4Oms(){
		List<SalOrderItem4OmsModel> result = saleLeadsOrderService.getSalOrderItem4Oms("8801696403038");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");

	}

	@Test
	public void getUpdateStatusRemark(){
		String result = saleLeadsOrderService.getUpdateStatusRemark("152");
		LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");

	}

	@Test
	public void getOperateRemark(){
		List<String> d = new LinkedList<>();
		for(int i=1;i<=300;i++){
			d.add(i+"");
		}
//		d.add("S-0000003ZJ1");
//		d.add("S-00000044X7");
		LOG.info("\nresult:\n" + JSON.toJSONString(saleLeadsOrderService.getOperateRemark(d))+"\n");

	}






}
