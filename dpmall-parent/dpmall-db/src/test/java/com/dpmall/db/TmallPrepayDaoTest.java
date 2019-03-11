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
		List<TmallPrePayEntity> result = tmallPrepayDao.getList("317951","","Y","",1,10,"","","","","","");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
    }


	@Test
	public void getListCount(){
		logger.info("=====================getList=======================");
		String result = tmallPrepayDao.getListCount("317951","","Y","","","","","","","");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}



	@Test
	public void editXiaoTitle(){
		logger.info("=====================editXiaoTitle=======================");
		PrepayPo vo = new PrepayPo();
		vo.setAgencyListStatus("COMPLETED");
		vo.setPrePayId("36068");

		int result = tmallPrepayDao.editXiaoTitle(vo);
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}


	@Test
	public void getSfStatusByPrePayId(){
		logger.info("=====================getList=======================");

		String result = tmallPrepayDao.getSfStatusByPrePayId("8830270605267");
		logger.info("\n"+JSON.toJSONString(result)+"\n");
	}


}
