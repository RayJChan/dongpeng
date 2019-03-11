package com.dpamll.datasvr;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IOrderService;
import com.dpmall.model.OrderModel;

public class OrderServiceFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceFacadeTest.class);
	@Autowired
	private IOrderService orderService;

	
	@Test
	
	public void getOnePage4DistributeTest() {

		logger.info("\nresult:\n"+JSON.toJSONString(orderService.getOnePage4Distribute("8100", "4",null, 0, 10,""))+"\n");

		
	}
	
@Test
	
	public void getOnePage4StoreIdTest() {
		logger.info("result:"+JSON.toJSONString(orderService.getOnePage4StoreId("8796126054392", "2", null, null, 0, 10,null)));
	}
	
	
	
	/**
	 * author:crown
	 * getOrderDetails
	 */
	@Test
    public void testGetOrderDetails(){

		logger.info("=====================getOrderDetails======================");
		OrderModel result = orderService.getOrderDetails("a100632427");//aSITB100910286,aSITB100915062,aSITB100919000
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
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
		Integer result = orderService.get2DistributeCount("310145", "8796105670747");
    	logger.info(JSON.toJSONString(result));
    }
	
	/**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     */
	@Test
	public void testGet2AcceptorCount() {
		logger.info("===================get2AcceptorCount=================");
		Integer result = orderService.get2AcceptorCount("8796105375835", "1");
		logger.info(JSON.toJSONString(result));
	}
	
	/**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     */
	@Test
	public void testGetOnePage4AcceptorId() {
		logger.info("===================getOnePage4AcceptorId=================");
		List<OrderModel> result = orderService.getOnePage4AcceptorId("123456", "8796105637979", 0, 20);
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
		Integer count = orderService.get2StoreCount("8796129527800","777", "2");
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
		OrderModel result = orderService.getReturnRequestDetails("a100629175");
    	logger.info(JSON.toJSONString(result));
    }
}
