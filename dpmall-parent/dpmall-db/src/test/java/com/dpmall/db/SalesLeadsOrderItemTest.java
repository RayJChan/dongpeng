package com.dpmall.db;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.dpmall.db.bean.po.SalOrderItemsPo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dpmall.common.SpringTestCase;
import com.dpmall.db.dao.SalesLeadsOrderItemDao;

public class SalesLeadsOrderItemTest extends SpringTestCase{
	
	private final Logger LOG = LoggerFactory.getLogger(SalesLeadsOrderItemTest.class);
	
	@Autowired
	private  SalesLeadsOrderItemDao salesLeadsOrderItemDao; 
	
//	@Test
//    public void testInsert(){
//		SalesLeadsOrderItemEntity entity = new SalesLeadsOrderItemEntity();
//		entity.catetory="1";
//		entity.dealPrice=new BigDecimal(10086);
//		entity.orderId=10086L;
//		entity.quantity=20;
//		LOG.info("result:"+salesLeadsOrderItemDao.insert(entity));
//    }

	@Test
	public void testInsert() {
		SalOrderItemsPo vo = new SalOrderItemsPo();
		vo.setIsDelete("1");
		vo.setOrderItemId("7772");
		vo.setProductCode("800EFG11010_A");
		vo.setProductName("天山碧玉");
		vo.setCategory("抛釉砖");
		vo.setUnit("件");
		vo.setQuantity("111");
		vo.setAmount("6666");
		vo.setSalesLeadsOrderId("2");
//			int i = salesLeadsOrderItemDao.edit(vo);
		int j = salesLeadsOrderItemDao.insert(vo);

		LOG.info("result:" + j);
	}

	@Test
	public void getItemIdByOrderId() {
		Set<String> result = salesLeadsOrderItemDao.getItemIdByOrderId("8797797011038");

		LOG.info("result:" + JSON.toJSONString(result));
	}

	@Test
	public void delete() {
		Set<String> r = new HashSet<>();
//		r.add("804");
//		r.add("3");
//		r.add("805");
		int result = salesLeadsOrderItemDao.delete(r);

		LOG.info("result:" + JSON.toJSONString(result));
	}

}
