package com.dpmall.web.controller;

import com.alibaba.fastjson.JSON;
import com.dpmall.datasvr.api.IHybrisService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hybris")
public class HybrisController {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private IHybrisService hybrisService;
	/**
	 获取token
	 */
	@RequestMapping(value="/getHybrisToken",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Map<String,Object> getHybrisToken(){
		LOG.info("{method:'HybrisController::getHybrisToken'");
		Map<String,Object> result = new HashMap<>();
		try{
			String token = hybrisService.getHybrisToken();
			result.put("access_token", token);
			result.put("message", "成功");
			result.put("resultCode", 200);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result.put("message", "系统错误");
			result.put("resultCode", 500);
		}
		LOG.info("{method:'HybrisController::getHybrisToken',out:{res:'" + JSON.toJSONString(result) + "'}}");
		return result;
	}
}
