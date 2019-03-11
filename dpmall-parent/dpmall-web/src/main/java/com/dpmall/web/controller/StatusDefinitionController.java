package com.dpmall.web.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dpmall.datasvr.api.IStatusDefinitionService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.StatusDefinitionModel;
import com.dpmall.web.controller.form.Response;
import com.dpmall.web.controller.form.StatusDefinitionForm;
/**
 * 
 * 原因选择
 * @author cwj
 *
 */
@Controller
@RequestMapping("/statusDefinition")
public class StatusDefinitionController {
	
	@Autowired
	private IStatusDefinitionService statusDefinitionService;
	
	private final Logger LOG=LoggerFactory.getLogger(StatusDefinitionController.class);
	
	/**
	 *  拒单原因选择
	 * **/
	@ResponseBody
	@RequestMapping("/refuseInfo")
	public Response refuseInfo(@RequestBody StatusDefinitionForm form) {
		Response response=new Response();
		LOG.info("{method:'StatusDefinitonController::refuseInfo',in:"+JSON.toJSONString(form)+"}");
		if (StringUtils.isEmpty(form.selectionType)) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				List<StatusDefinitionModel> list = new ArrayList<StatusDefinitionModel>();

				list = statusDefinitionService.getInfoByType(form.selectionType);

				response.data=list;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'StatusDefinitonController::refuseInfo',out:"+JSON.toJSONString(response)+"}");
		return response;
	}


	/**
	 *  拒单原因选择
	 * **/
	@ResponseBody
	@RequestMapping("/noDealReasons")
	public Response NoDealReasons() {
		Response response = new Response();
		LOG.info("{method:'StatusDefinitonController::NoDealReasons',start");

		try {
			response.data = statusDefinitionService.getInfoByType("NoDealReasons");
			response.resultCode = ErrorCode.SUCCESS;
		} catch (Exception e) {
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "系统错误";
			LOG.error(e.getMessage());
		}

		LOG.info("{method:'StatusDefinitonController::NoDealReasons',out:"+JSON.toJSONString(response)+"}");
		return response;
	}


	/**
	 *  获取留资状态(跟进中的状态)
	 * **/
	@ResponseBody
	@RequestMapping("/getSalOrderStatus")
	public Response getSalOrderStatus() {
		Response response = new Response();
		LOG.info("{method:'StatusDefinitonController::getSalOrderStatus',start}");

		try {
			response.data = statusDefinitionService.getSalOrderStatus();
			response.resultCode = ErrorCode.SUCCESS;
		} catch (Exception e) {
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "系统错误";
			LOG.error(e.getMessage());
		}

		LOG.info("{method:'StatusDefinitonController::getSalOrderStatus',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	
	
	
	
	
}
