package com.dpamll.datasvr;

import java.util.List;

import com.dpmall.db.bean.po.OrderEditStatusPo;
import com.dpmall.model.in.OrderDistributeModelIn;
import com.dpmall.model.in.OrderEditStatusModelIn;
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

		logger.info("\nresult:\n"+JSON.toJSONString(orderService.getOnePage4Distribute("8200", "","25509906587", 0, 10,""))+"\n");

		
	}
	
@Test
	
	public void getOnePage4StoreIdTest() {
		logger.info("result:"+JSON.toJSONString(orderService.getOnePage4StoreId("8796126054392", "", null, null, 0, 10,null)));
	}
	
	
	
	/**
	 * author:crown
	 * getOrderDetails
	 */
	@Test
    public void testGetOrderDetails(){

		logger.info("=====================getOrderDetails======================");
		OrderModel result = orderService.getOrderDetails("48942");//aSITB100910286,aSITB100915062,aSITB100919000
    	logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
    }
	
	/**
     * 实物类经销商订单状态条数
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
     */
	@Test
	public void testGet2AcceptorCount() {
		logger.info("===================get2AcceptorCount=================");
		Integer result = orderService.get2AcceptorCount("8796105375835", "1");
		logger.info(JSON.toJSONString(result));
	}
	
	/**
     * 实物类导购员订单状态条数
     */
	@Test
	public void testGetOnePage4AcceptorId() {
		logger.info("===================getOnePage4AcceptorId=================");
		List<OrderModel> result = orderService.getOnePage4AcceptorId("123456", "8796105637979", 0, 20);
		logger.info(JSON.toJSONString(result));
	}
	/**
     * 实物类门店订单状态条数
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
     * @return 订单详情
     */
	@Test
    public void TestGetReturnRequestDetails(){
		logger.info("=====================getReturnRequestDetails=======================");
		OrderModel result = orderService.getReturnRequestDetails("48942");
    	logger.info(JSON.toJSONString(result));
    }


	@Test
	public void acceptOfAgency(){
		logger.info("=====================acceptOfAgency=======================");
		OrderDistributeModelIn in  = new OrderDistributeModelIn();
		in.setConsignmentId("48942");
		in.setStoreId("1");
		int result = orderService.acceptOfAgency(in);
		logger.info(JSON.toJSONString(result));
	}

	@Test
	public void rejectOfAgency(){
		logger.info("=====================rejectOfAgency=======================");
		OrderDistributeModelIn in  = new OrderDistributeModelIn();
		in.setConsignmentId("48942");
		int result = orderService.rejectOfAgency(in);
		logger.info(JSON.toJSONString(result));
	}


	@Test
	public void acceptOfStore(){
		logger.info("=====================acceptOfStore=======================");
		OrderDistributeModelIn in  = new OrderDistributeModelIn();
		in.setConsignmentId("48942");
		int result = orderService.acceptOfStore(in);
		logger.info(JSON.toJSONString(result));
	}


	@Test
	public void editStatus(){
		logger.info("=====================editStatus=======================");
		OrderEditStatusModelIn in  = new OrderEditStatusModelIn();
		in.setConsignmentCode("48942");
		in.setDate("2019-01-01");
		in.setShippedDate("2019-01-02");
		int result = orderService.editStatus(in);
		logger.info(JSON.toJSONString(result));
	}

}
