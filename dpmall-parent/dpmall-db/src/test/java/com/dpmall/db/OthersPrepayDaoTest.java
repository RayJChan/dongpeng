package com.dpmall.db;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.OthersPrePayEntity;
import com.dpmall.db.bean.TmallPrePayEntity;
import com.dpmall.db.dao.OthersPrepayDao;
import com.dpmall.db.dao.TmallPrepayDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class OthersPrepayDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(OthersPrepayDaoTest.class);
	
	@Autowired
	private OthersPrepayDao othersPrepayDao;


	@Test
    public void getList(){
		logger.info("=====================getList=======================");
		List<OthersPrePayEntity> result = othersPrepayDao.getList("8796132134458","","Y","2",1,10,"");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
    }

	@Test
	public void getWriteCodeAndOrderCode(){
		logger.info("=====================getList=======================");
		OthersPrePayEntity result = othersPrepayDao.getWriteCodeAndOrderCode("8796132134458");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void updateOrders(){
		logger.info("=====================getList=======================");
		int result = othersPrepayDao.updateOrders("8837348001747","8796095676507");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}


}
