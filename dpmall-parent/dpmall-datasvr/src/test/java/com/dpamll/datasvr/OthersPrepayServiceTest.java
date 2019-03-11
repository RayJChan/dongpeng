package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.JavaBeanUtils;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IOthersPrepayService;
import com.dpmall.datasvr.api.ITmallPrepayService;
import com.dpmall.db.bean.po.PrepayPo;
import com.dpmall.model.in.OtherPrePayInfoModelIn;
import com.dpmall.model.in.OthersPrePayItemsModelIn;
import com.dpmall.model.in.PrepayModelIn;
import com.dpmall.model.in.SalOrderPictureModelIn;
import com.dpmall.model.prePay.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class OthersPrepayServiceTest extends SpringTestCase {

    private Logger Logger = LoggerFactory.getLogger(OthersPrepayServiceTest.class);

    @Autowired
    private IOthersPrepayService othersPrepayService;


    @Test
    public void getListOfAgency() {
        Logger.info("===================getListOfAgency===============");
        List<OthersPrePayModel> out = othersPrepayService.getListOfAgency("8796126203450", "2", 2, 5, "");
        Logger.info("result:\n" + JSON.toJSONString(out) + "\n");
    }

    @Test
    public void getListOfStore() {
        Logger.info("===================getListOfAgency===============");
        List<OthersPrePayModel> out = othersPrepayService.getListOfStore("8796127954936", "2", 1, 10, "");
        Logger.info("result:\n" + JSON.toJSONString(out) + "\n");
    }
//
//	@Test
//	public void getListCountOfAgency() {
//		Logger.info("===================getListOfAgency===============");
//		TmallPrepayListCountModel out = tmallPrepayService.getListCountOfAgency("8796132134458");
//		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
//	}
//
//	@Test
//	public void getListCountOfStore() {
//		Logger.info("===================getListOfAgency===============");
//		TmallPrepayListCountModel out= tmallPrepayService.getListCountOfStore("8796126054392");
//		Logger.info("result:\n"+JSON.toJSONString(out)+"\n");
//	}
//
//

    @Test
    public void getDetails() {
        logger.info("=====================getDetails=======================");
        OthersPrePayDetailModel result = othersPrepayService.getDetails("8830270703571");
        logger.info("\n" + JSON.toJSONString(result) + "\n");
    }


    @Test
    public void agencyAccept() {
        logger.info("=====================agencyAccept=======================");
        List<String> list = new ArrayList<>();
//        list.add("8834923562963");
        list.add("8830270703571");
//        int result = othersPrepayService.agencyAccept(list, "8796127954936","8796224278113");
		int result = othersPrepayService.agencyAccept(list,"1","8796224278113");


        logger.info("\n" + JSON.toJSONString(result) + "\n");
    }


    @Test
    public void storeAccept() {
        logger.info("=====================storeAccept=======================");
        List<String>idList = Arrays.asList("8834923562963");
        int result = othersPrepayService.storeAccept(idList,"8796224278113");
//		int result = othersPrepayService.agencyAccept(list,"1");


        logger.info("\n" + JSON.toJSONString(result) + "\n");
    }

    @Test
    public void withdraw() {
        logger.info("=====================withdraw=======================");
        int result = othersPrepayService.withdraw("8830270703571","8796224278113");
        logger.info("\n" + JSON.toJSONString(result) + "\n");
    }


    @Test
    public void updateCustomInfo() {
        logger.info("=====================updateCustomInfo=======================");
        PrepayModelIn in = new PrepayModelIn();
        in.setPrePayId("8834890368979");

        in.setCustomerName("cwjTest");
        in.setPhone("13112717111");
        in.setRegion("广东省");
        in.setCity("佛山市");
        in.setDistrict("南海区");
        in.setStreetName("东鹏分公司");

        in.setClientType("客户类型1");
        in.setFitmentSpace("客房");
        in.setFitmentTime("1个月后");
        in.setFitmentArea("200m2");
        in.setStylePreference("中式");
        in.setConsumptionBudget("50w");
        in.setVillage("碧桂园22");
        in.setProductPreference("天山碧玉");
        in.setCustBenefit("优惠20w");


        int result = othersPrepayService.updateCustomInfo(in,"8796224278113");
        logger.info("\n" + JSON.toJSONString(result) + "\n");
    }


    @Test
    public void updateOrderProgress() {
        logger.info("=====================updateOrderProgress====================");
        int result = othersPrepayService.updateOrderProgress("已到店", "8830270506963", "77", "8796224278113", "");
        logger.info("\nresult:\n" + JSON.toJSONString(result) + "\n");
    }


    @Test
    public void updateSalOrderInfo() {
        logger.info("=====================updateSalOrderInfo====================");

        //图片
        List<SalOrderPictureModelIn> pictures = new ArrayList<>();
        SalOrderPictureModelIn picture1 = new SalOrderPictureModelIn();
        picture1.setPictureUrl("dpmall/saleLeadsOrders/8796224278113/20180912/close_popup.png");

        pictures.add(picture1);

        //商品详情
        List<OthersPrePayItemsModelIn> inList = new ArrayList<>();

        OthersPrePayItemsModelIn in1 = new OthersPrePayItemsModelIn();
        in1.setItemsId("new");
        in1.setProductName("800X800伊朗白玉");
        in1.setProductCode("FG805391_A");
        in1.setProductCategory("瓷砖类");
       in1.setAmount(20);
        in1.setUnitPrice("5");
        in1.setUnit("片");
       in1.setTotalPrice("1000");
//
//        OthersPrePayItemsModelIn in2 = new OthersPrePayItemsModelIn();
//        in2.setItemsId("8796158611065");
//        in2.setProductName("天山111");
//        in2.setProductCode("800EFG11010_B");
//        in2.setProductCategory("抛釉砖");
//        in2.setAmount(0);
//        in2.setUnitPrice("10");
//        in2.setUnit("件");
////        in2.setTotalPrice("500");

        inList.add(in1);
//        inList.add(in2);


        //
        OtherPrePayInfoModelIn in = new OtherPrePayInfoModelIn();
        in.setPrePayId("8837348001747");
        in.setPictureNames(pictures);
       in.setPayPrice("950");
        in.setDiscountPrice("50");
        in.setOthersPrePayItems(inList);
        in.setRemark("ghh");

        int result = othersPrepayService.updatePrePayOrderInfo(in,"8796224278113");
//        int result = othersPrepayService.savePrePayOrderInfo( in);


        logger.info("\nresult:\n" + JSON.toJSONString(result) + "\n");
    }



    @Test
    public void judgeWriteOffCode() {
        logger.info("=====================judgeWriteOffCode====================");
         boolean result  = othersPrepayService.judgeWriteOffCode("8830270474195","1234567891065870809");
        logger.info("\nresult:\n" + JSON.toJSONString(result) + "\n");

    }


    @Test
    public void updatePrePayToDpmall() {
        logger.info("=====================judgeWriteOffCode====================");
        int result  = othersPrepayService.updatePrePayToDpmall("8830270474195");
        logger.info("\nresult:\n" + JSON.toJSONString(result) + "\n");

    }



}
