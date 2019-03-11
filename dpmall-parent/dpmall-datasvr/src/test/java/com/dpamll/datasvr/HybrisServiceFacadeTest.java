package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IHybrisService;
import com.dpmall.datasvr.api.IUserService;
import com.dpmall.model.AppGroupModel;
import com.dpmall.model.LoginResModel;
import com.dpmall.model.UserModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HybrisServiceFacadeTest extends SpringTestCase {

	private Logger Logger = LoggerFactory.getLogger(HybrisServiceFacadeTest.class);
	
	@Autowired
	private IHybrisService hybrisService;

	@Autowired
	private HybrisUtils hybrisUtils;

	@Test
	public void test() {
		String result = hybrisService.getHybrisToken();

		logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
	}


	@Test
	public void refreshStatus() {
		Map<String,Object> map = new HashMap<>();
//		params 格式：{"type":"SalesLeadsOrder",pk:"1111"}
		map.put("type","Addresses");
		map.put("pk","8796246409239");

		String result = hybrisUtils.refreshStatus(map);
		logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
	}

	@Test
	public void getStatusPkByNames() {
		Map<String,Object> map = new HashMap<>();
//		params 格式：{"type":"SalesLeadsOrder",pk:"1111"}
		map.put("type","ConsignmentStatus");
		map.put("statusName","READY_FOR_PICKUP");
		String result = hybrisUtils.getStatusPkByNames(map);
		logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
	}


	//批量更新
	@Test
	public void batchRefresh() {
		Map<String,Object> map = new HashMap<>();
		map.put("type","Addresses");//O2OConsignmentItems,Consignment
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
		int b= hybrisUtils.batchRefresh(map);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
		System.out.println("result:\n" + b + "\n");
	}

	@Test
	public void getAppGroupInfo() {
		Map<String,Object> map = new HashMap<>();
//		params 格式：{"code":"317716"}
		map.put("code","100100");
		String result = hybrisUtils.getAppGroupInfo(map);
		logger.info("\nresult:\n"+JSON.toJSONString(result)+"\n");
	}





}
