package com.dpmall.db;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.po.PrePayOperationPo;
import com.dpmall.db.dao.PrePayOperationDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PrePayOperationDaoTest extends SpringTestCase{
	
	private final Logger LOG = LoggerFactory.getLogger(PrePayOperationDaoTest.class);
	
	@Autowired
	private PrePayOperationDao prePayOperationDao;
	
	@Test
    public void testInsert(){
		PrePayOperationPo po = new PrePayOperationPo();
		po.setId("3444");
		po.setPrePayId("8835447457747");
		po.setOperator("8796224278113");
		po.setOperatorName("广东江门恩平万达");
		po.setOperationType("UpdateOrderProgress");
		po.setOperationTypeName("更新订单状态");
		po.setOperationTypeDesc("未完成");
		po.setOperatorRemark("6666");

		int result = prePayOperationDao.insert(po);
		LOG.info("result:\n"+ JSON.toJSONString(result)+"\n");
    }
}
