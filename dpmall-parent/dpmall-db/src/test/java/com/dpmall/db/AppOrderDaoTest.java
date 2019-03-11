package com.dpmall.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.DateUtils;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderItemEntity;
import com.dpmall.db.bean.OrderReturnDetailsEntity;
import com.dpmall.db.bean.OrderReturnEntity;
import com.dpmall.db.dao.AppOrderDao;

public class AppOrderDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(AppOrderDaoTest.class); 
	
	@Autowired
	private AppOrderDao appOrderDao;
	
	/**
	 * author:crown
	 * getOnePage4Distribute
	 */
	
	@Test
    public void testGetOnePage4Distribute(){
		logger.info("=====================crown=========================");
    	List<OrderEntity> result = appOrderDao.getOnePage4Distribute("316921", "2",null, 0, 999,"d");
    	logger.info(JSON.toJSONString(result));
    }
	
	@Test
    public void testGetOnePage4StoreId(){
		logger.info("=====================crown=========================");
    	List<OrderEntity> result = appOrderDao.getOnePage4StoreId("8796129527800", "3", null, "ddd", 0, 100,"");
    	logger.info(JSON.toJSONString(result));
    }
	@Test
    public void testAccept(){
		logger.info("=====================1Crown=======================");
//    	int result = appOrderDao.accept("12306", "8803499018195", "水印");
//    	logger.info(JSON.toJSONString(result));
    }
	
	/**
     * 经销商下派到店铺
     * @param orderCode 订单编码
     * @param storeId 店铺ID
     * @param remark 备注
     */
	@Test
	public void testDistributes() {
		logger.info("==============testDistributes==============");
		int result1 = appOrderDao.distribute4O2o("1", "1111", "Test备注");
		//int result2 = appOrderDao.distribute4Consignment("ddddd", "2222");
		logger.info(Integer.toString(result1));
		
	}
	

	@Test
    public void testGetOrderDetails(){
		logger.info("=====================getOrderDetails=======================");
		OrderEntity result = appOrderDao.getOrderDetails("aSITB100663001");
		logger.info(JSON.toJSONString(result));
    }
	
	/**
     * 实物类经销商订单状态条数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
    public void testGet2DistributeCount(){
		logger.info("=====================testGet2DistributeCount=======================");
		Integer result = appOrderDao.get2DistributeCount("310597", "2");
    	logger.info(JSON.toJSONString(result));
    }
	
	/**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 实物类导购员订单状态条数
     */
	@Test
	public void testGet2AcceptorCount() {
		logger.info("=================testGet2AcceptorCount====================");
		Integer count = appOrderDao.get2AcceptorCount("5590590", "8796105375835");
		logger.info(JSON.toJSONString(count));
	}
	
	/**
     * 实物类经销商订单状态条数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
    public void testGetOnePage4AcceptorId(){
		logger.info("=====================getOnePage4AcceptorId=======================");
		List<OrderEntity> result = appOrderDao.getOnePage4AcceptorId("123456", "8796105637979", 0, 20);
    	logger.info(JSON.toJSONString(result));
    }
	
	/**
     * 实物类门店订单状态条数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
	public void testGet2StoreCount() {
		logger.info("=================testGet2StoreCount====================");

		Integer count = appOrderDao.get2StoreCount("8796129527800","8796191379041", "2");

		logger.info(JSON.toJSONString(count));
	}
	
	/**
     * 实物类获取退货单据明细
     * @param consignmentId 发货单ID
     * @return 订单详情
     */
	@Test
    public void TestGetReturnRequestDetails(){
		logger.info("=====================getReturnRequestDetails=======================");
		OrderEntity result = appOrderDao.getReturnRequestDetails("aSITB100663001");
    	logger.info(JSON.toJSONString(result));
    }
	
	@Test
    public void testFormatUnit(){
		logger.info("=====================getOrderDetails=======================");
		OrderEntity result = appOrderDao.getOrderDetails("aSITB100628349");
		List<OrderItemEntity> entityList = result.items;
		List<OrderItemEntity> newList = new ArrayList<OrderItemEntity>();
		for (OrderItemEntity entity : entityList) {
			entity.unit = appOrderDao.formatUnit(entity.unit);//转格式
			newList.add(entity);
		}
		result.items = newList;//赋值

		logger.info(JSON.toJSONString(result));
    }
	

	@Test
    public void testGetComments(){
		logger.info("=====================testGetComments=======================");
		StringBuffer buybuffer  =new StringBuffer();//拼接用
		StringBuffer sellerbuffer = new StringBuffer();
		List<OrderEntity> result = appOrderDao.getComments("aSITB100674079");
		int i = 1;
		for (OrderEntity entity :result) {
			if (entity.buyerMessage != null) {
				buybuffer.append(i+":"+entity.buyerMessage+",");
			}
			if (entity.sellerMessage != null ) {
				sellerbuffer.append(i+":"+entity.sellerMessage+",");
			}
			++i;
			System.out.println("buybuffer--------------"+buybuffer+"--\nsellerbuffer-----"+sellerbuffer+"\ni--"+i);
		}
		//去除最后一个逗号
		if (buybuffer.length() > 0) {
			String buyStr = buybuffer.substring(0, buybuffer.length()-1);
			System.out.println("buyStr--------------"+buyStr);
		}
		if (sellerbuffer.length() > 0) {
			String sellerStr = sellerbuffer.substring(0, sellerbuffer.length()-1);
			System.out.println("sellerStr--------------"+sellerStr);
		}
		
		logger.info(JSON.toJSONString(result));
    }
	
	@Test
	public void distributedOrders() {
		List<String> list=new ArrayList<String>();
		list.add("8796129429496");
		Calendar calendar=Calendar.getInstance();
		Date enddate=new Date();
		calendar.set(2017, 9, 20);
		Date startdate=calendar.getTime();
		logger.info(JSON.toJSONString(appOrderDao.distributedOrders(list, startdate, enddate)));
	}
	
	@Test
    public void getPartOfReturnDetailsTest(){
		logger.info("=====================getPartOfReturnDetailsTest=======================");
		OrderReturnEntity result = appOrderDao.getPartOfReturnDetails("aSITB100919000","RA00205001");
		logger.info(JSON.toJSONString(result));
    }
	
	@Test
    public void getPartOfReturnCodesTest(){
		logger.info("=====================getPartOfReturnCodesTest=======================");
		List<String> result = appOrderDao.getPartOfReturnCodes("aSITB100915062");
		logger.info(JSON.toJSONString(result));
    }
	
	@Test
    public void getReturnPriceSumTest(){
		logger.info("=====================getReturnPriceSumTest=======================");
		String result = appOrderDao.getReturnPriceSum("aSITB100920001");
		logger.info("\nresult:"+JSON.toJSONString(result)+"\n");
    }
	
	
}
