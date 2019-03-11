package com.dpamll.datasvr;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IPrepayService;
import com.dpmall.model.PrepayModel;

public class PrePayFacadeTest extends SpringTestCase{
	private static final Logger LOG = LoggerFactory.getLogger(PrePayFacadeTest.class);
	@Autowired
	private IPrepayService prepayService;
	
	/**
     * 特权定金门店订单状态条数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
	public void testDistribute() {
		logger.info("=================distribute====================");
		Integer count = prepayService.distribute("110", "a100015013", "165434", "daihx");
		logger.info(JSON.toJSONString(count));
	}
	
	/**
     * 特权定金订单状态条数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	@Test
	public void testGet2AcceptorCount() {
		logger.info("=================get2AcceptorCount====================");
		Integer count = prepayService.get2AcceptorCount("46567675", "CANCELLED");
		logger.info(JSON.toJSONString(count));
	}
	
	/**
     * 特权定金获取单据明细
     * @param consignmentId 发货单ID
     * @return 特权定金获取单据明细
     */
	@Test
	public void testGet4ConsignmentId() {
		logger.info("=====================testGet4ConsignmentId=======================");
		PrepayModel result = prepayService.get4ConsignmentId("aSITB100667001");
    	logger.info(JSON.toJSONString(result));
	}
	/**
	 * 特权定金导购员状态列表
	 * @param acceptorId 导购员Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金导购员状态列表
	 */
	@Test
	public void testGetOnePage4AcceptorId() {
		logger.info("===================getOnePage4AcceptorId=================");
		List<PrepayModel> result = prepayService.getOnePage4AcceptorId("8796191379041", "2",  null ,0, 20,"");
		logger.info(JSON.toJSONString(result));
	}
	
	@Test
	public void testGetOnePage4Distribute() {
		logger.info("===================testGetOnePage4Distribute=================");

		List<PrepayModel> result = prepayService.getOnePage4Distribute("310597", "1", null,0, 999,null);

		logger.info(JSON.toJSONString(result));
	}
	
	@Test
	public void testGetOnePage4StoreId() {
		logger.info("===================testGetOnePage4StoreId=================");
		List<PrepayModel> result = prepayService.getOnePage4StoreId("8796126054392", "1",  null ,1, 999);
		logger.info(JSON.toJSONString(result));
	}
	
	@Test
	public void testGet4priDepositCode() {
		logger.info("=====================testGet4ConsignmentId=======================");
		PrepayModel result = prepayService.get4priDepositCode("1882419599143265878");
    	logger.info(JSON.toJSONString(result));
	}
	@Test
	public void tetsUpdateOrder() {
		logger.info("=====================testGet4ConsignmentId=======================");
		Integer result = prepayService.updateOrder("aSITB100613015","ACCEPT","aaa");
    	logger.info(JSON.toJSONString(result));
	}
}
