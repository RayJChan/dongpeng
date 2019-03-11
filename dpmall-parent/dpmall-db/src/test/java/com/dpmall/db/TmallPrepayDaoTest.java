package com.dpmall.db;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.StatusDefinitionEntity;
import com.dpmall.db.bean.TmallPrePayEntity;
import com.dpmall.db.bean.po.PrepayPo;
import com.dpmall.db.dao.StatusDefinitionDao;
import com.dpmall.db.dao.TmallPrepayDao;
import com.dpmall.db.dao.UtilsDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TmallPrepayDaoTest extends SpringTestCase{
	
	private final Logger logger=LoggerFactory.getLogger(TmallPrepayDaoTest.class);
	
	@Autowired
	private TmallPrepayDao tmallPrepayDao;


	@Test
    public void getList(){
		logger.info("=====================getList=======================");
		List<TmallPrePayEntity> result = tmallPrepayDao.getList("","","Y","2",1,10,"");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
    }


	@Test
	public void getListCount(){
		logger.info("=====================getList=======================");
		String result = tmallPrepayDao.getListCount("","8796127954936","N","2","");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}



	@Test
	public void editO2oConsignment(){
		logger.info("=====================getList=======================");
		PrepayPo vo = new PrepayPo();
		vo.setAgencyListStatus("COMPLETED");
		vo.setPrePayId("8830270605267");

		int result = tmallPrepayDao.editO2oConsignment(vo);
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}


	@Test
	public void getSfStatusByPrePayId(){
		logger.info("=====================getList=======================");

		String result = tmallPrepayDao.getSfStatusByPrePayId("8830270605267");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}


}
