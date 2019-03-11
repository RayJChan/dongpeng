package com.dpmall.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.PrePayEntity;
import com.dpmall.db.bean.PrePayItemEntity;
import com.dpmall.db.dao.PrePayDao;


public class PrePayDaoTest extends SpringTestCase {
	private final Logger logger = LoggerFactory.getLogger(PrePayDaoTest.class);
	
	@Autowired
	private PrePayDao prePayDao;
	
	@Test
	public void get2DistributeCountTest() {
		logger.info("result:"+prePayDao.get2DistributeCount("310145", "2"));
	}
	
	@Test
	public void get2StoreCountTest() {
		logger.info("result:"+prePayDao.get2StoreCount("8796129429496", "6"));
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGet4ConsignmentId() {
		logger.info("=====================testGet4ConsignmentId=======================");

		PrePayEntity result = prePayDao.get4ConsignmentId("aSITB100652326");

		logger.info(JSON.toJSONString(result));
//		
//		byte [] productList = result.productList;
//		if(result.productList != null) {
//			
//			Object obj = null;        
//	        try {          
//	            ByteArrayInputStream bis = new ByteArrayInputStream (productList);          
//	            ObjectInputStream ois = new ObjectInputStream (bis);          
//	            obj = ois.readObject();        
//	            ois.close();     
//	            bis.close();     
//	        } catch (IOException ex) {          
//	            ex.printStackTrace();     
//	        } catch (ClassNotFoundException ex) {          
//	            ex.printStackTrace();     
//	        }
//	        
//	        List<String> proList = ( List<String>) obj;
//	        //定义list的长度和上面的产品明细list的长度一致
//	        List<PrePayItemEntity> productItems = new ArrayList<PrePayItemEntity>();
//	        for(int i =0 ;i<proList.size();i++) {
//	        	PrePayItemEntity entity = new PrePayItemEntity();
//	        	String [] newd = proList.get(i).split(",");
//	        	entity.category = newd[0];
//	        	entity.quantity = newd[1];
//	        	entity.totalPrice = new BigDecimal(newd[2]).setScale(2, BigDecimal.ROUND_HALF_UP);
//	        	productItems.add(entity);
//	        }
//	        System.out.println(productItems.toString());
//	        result.productItems = productItems;
//	        logger.info(JSON.toJSONString(result));
//		}
//		
		
	}
	@Test
    public void testGetOnePage4AcceptorId(){
		logger.info("=====================getOnePage4AcceptorId=======================");
		List<PrePayEntity> result = prePayDao.getOnePage4AcceptorId("8796191379041", "1", "ddds" ,0, 999,"TOSTORE");
    	logger.info(JSON.toJSONString(result));
    }
	@Test
    public void testGet4Search(){
		logger.info("=====================get4Search=======================");
		List<PrePayEntity> result = prePayDao.get4Search("13266398695");
    	logger.info(JSON.toJSONString(result));
    }
	
	/**
	 * 特权定金销商商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param startNum 上一次加载的最后项
	 * @param pageSize 页的大小
	 * @return 特权定金销商商订单状态列表
	 */
	@Test
	public void testGetOnePage4Distribute() {
		logger.info("=================testGetOnePage4Distribute====================");
		List<PrePayEntity> entitieList = prePayDao.getOnePage4Distribute("310597", "1", "dd", 0, 999,"TOSTORE");
		logger.info(JSON.toJSONString(entitieList));
	}
	
	/**
	 * 特权定金销商商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param startNum 上一次加载的最后项
	 * @param pageSize 页的大小
	 * @return 特权定金销商商订单状态列表
	 */
	@Test
	public void testGetOnePage4StoreId() {
		logger.info("=================testGetOnePage4StoreId====================");
		List<PrePayEntity> entitieList = prePayDao.getOnePage4StoreId("8796126054392", "1", "dd",0, 999);
		logger.info(JSON.toJSONString(entitieList));
	}
	
	@Test
	public void testGet4priDepositCode() {
		logger.info("=====================testGet4ConsignmentId=======================");
		PrePayEntity result = prePayDao.get4priDepositCode("1882419599143265878");
    	logger.info(JSON.toJSONString(result));
	}
	


	@Test
	public void testGet2AcceptorCount () {
		logger.info("=====================testGet2AcceptorCount=======================");
		int count = prePayDao.get2AcceptorCount("8796191379041","2");
    	logger.info(JSON.toJSONString(count+":count"));
	}
	
	@Test
	public void testGet2StoreCount () {
		logger.info("=====================testGet2StoreCount=======================");
		int count = prePayDao.get2StoreCount("8796129527800","1");
    	logger.info(JSON.toJSONString(count+":count"));
	}
	
	@Test
	public void testUpdateOrder() {
		logger.info("=====================testUpdateOrder=======================");
		int count = prePayDao.updateOrder("aSITB100613015","ACCEPT");
    	logger.info(JSON.toJSONString(count+":count"));
	}
	
	@Test
	public void testUpdateO2oOrder () {
		logger.info("=====================testUpdateOrder=======================");
		int count = prePayDao.updateO2oOrder("aSITB100613015","aini爱你o");
    	logger.info(JSON.toJSONString(count+":count"));
	}
}

