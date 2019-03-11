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
import com.dpmall.datasvr.api.IReportService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.ReportModel;
import com.dpmall.web.controller.form.ReportForm;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private IReportService reportService;
	
	private final Logger LOG=LoggerFactory.getLogger(ReportController.class);
	
	/**
	 *  实物：OMS派单给经销商的实物订单数量
	 * **/
	@ResponseBody
	@RequestMapping("/orderDistributCount")
	public Response orderDistributCount(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::orderDistributCount',in:"+JSON.toJSONString(form)+"}");
		if (form.agencyId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				int result=reportService.orderDistributCount(form.agencyId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::orderDistributCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	/**
	 * 时间段内经销商接单数
	 * **/
	@ResponseBody
	@RequestMapping("/orderAgencyAcceptCount")
	public Response orderAgencyAcceptCount(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::orderAgencyAcceptCount',in:"+JSON.toJSONString(form)+"}");
		if (form.agencyId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				int result=reportService.orderAgencyAcceptCount(form.agencyId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::acceptCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	


	/**
	 * 实物  门店的派单/接单数量 
	 * 
	 * **/
	@ResponseBody
	@RequestMapping("/orderStoreAcceptCount")
	public Response orderStoreAcceptCount(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::orderStoreAcceptCount',in:"+JSON.toJSONString(form)+"}");
		if (form.storeId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				int result=reportService.orderStoreAcceptCount(form.storeId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::orderStoreAcceptCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	
	
	/**
	 * 特权订金 OMS 派单给经销商数量
	 * **/
	@ResponseBody
	@RequestMapping("/prePayDistributCount")
	public Response prePayDistributCount(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::prePayDistributCount',in:"+JSON.toJSONString(form)+"}");
		if (form.agencyId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				int result=reportService.prePayDistributCount(form.agencyId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::prePayDistributCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	/**
	 * 特权定金，经销商接单数量
	 * **/
	@ResponseBody
	@RequestMapping("/prePayAgencyAcceptCount")
	public Response prePayAgencyAcceptCount(@RequestBody ReportForm form) {
		Response response = new  Response();
		LOG.info("{method:'ReportController::prePayAgencyAcceptCount',in:"+JSON.toJSONString(form)+"}");
		if ((StringUtils.isEmpty(form.agencyId) ||  StringUtils.isEmpty(form.startTime) || StringUtils.isEmpty(form.endTime))) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		}else {
			try {
				int result=reportService.prePayAgencyAcceptCount(form.agencyId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::prePayAgencyAcceptCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	
	/**
	 * 特权定金 门店的派单/接单数量
	 * **/
	@ResponseBody
	@RequestMapping("/prePayStoreAcceptCount")
	public Response prePayStoreAcceptCount(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::prePayStoreAcceptCount',in:"+JSON.toJSONString(form)+"}");
		if (form.storeId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				int result=reportService.prePayStoreAcceptCount(form.storeId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::prePayStoreAcceptCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	/**
	 * 特权订金 核销数量
	 * **/
	@ResponseBody
	@RequestMapping("/prePayWriteOffCount")
	public Response prePayWriteOffCount(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::prePayWriteOffCount',in:"+JSON.toJSONString(form)+"}");
		if (form.storeId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				int result=reportService.prePayWriteOffCount(form.storeId, form.startTime, form.endTime);
				response.data=result;
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::prePayWriteOffCount',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	

	/**
	 * 经销商对应的门店的接单数量 
	 * **/
	@ResponseBody
	@RequestMapping("/prePayStoreAcceptCountOfAgency")
	public Response prePayStoreAcceptCountOfAgency(@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::prePayStoreAcceptCountOfAgency',in:"+JSON.toJSONString(form)+"}");
		if (form.agencyId==null||form.startTime==null||form.endTime==null) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
		else {
			try {
				response.data=reportService.prePayStoreAcceptCountOfAgency(form.agencyId, form.startTime, form.endTime);
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::prePayStoreAcceptCountOfAgency',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	//------------------------------------------------新增-----------------------------------------------------------------
	
	/**
	 * 实物报表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderReportForm")
	public Response orderReportForm (@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::OrderReportForm',in:"+JSON.toJSONString(form)+"}");
		if(StringUtils.isEmpty(form.code)||StringUtils.isEmpty(form.startTime)||StringUtils.isEmpty(form.dateFormat)||
				StringUtils.isEmpty(form.endTime)||StringUtils.isEmpty(form.isStore)||StringUtils.isEmpty(form.roleCode)) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}else {
			try {
				ReportModel reportModel = new  ReportModel();
				List<String> agencyPks =new ArrayList<String>();
				agencyPks = reportService.getAppGroupInfo(form.code);//调用Hybris接口，返回agencyPks
				if ("Y".equals(form.isStore)) {//判断是否门店
					if (StringUtils.isEmpty(form.storeId)) {
						response.resultCode = ErrorCode.INVALID_PARAM;
						response.message="参数错误";
						return response;
					}else {
						reportModel = reportService.OrdersOfStore(form.roleCode,form.storeId, form.startTime, form.endTime,form.dateFormat);//
					}
				}else {
					reportModel = reportService.OrdersOfAgency(form.code,agencyPks, form.dateFormat,form.startTime, form.endTime);
				}
				
				response.data = reportModel;
				response.resultCode=ErrorCode.SUCCESS;
				
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
			
		}
		LOG.info("{method:'PrepayController::OrderReportForm',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	/**
	 * 特权定金报表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/prepayReportForm")
	public Response prepayReportForm (@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::prepayReportForm',in:"+JSON.toJSONString(form)+"}");
		if(StringUtils.isEmpty(form.code)||StringUtils.isEmpty(form.startTime)||
				StringUtils.isEmpty(form.endTime)||StringUtils.isEmpty(form.isStore)||StringUtils.isEmpty(form.roleCode)) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}else {
			try {
				ReportModel reportModel = new  ReportModel();
				List<String> agencyPks = reportService.getAppGroupInfo(form.code);//调用Hybris接口，返回agencyPks
				if ("Y".equals(form.isStore)) {//判断是否门店
					if (StringUtils.isEmpty(form.storeId)) {
						response.resultCode = ErrorCode.INVALID_PARAM;
						response.message="参数错误";
						return response;
					}else {
						reportModel = reportService.PrepaysOfStore(form.storeId, form.startTime, form.endTime,form.dateFormat);//
					}
				}else {
					reportModel = reportService.PrepaysOfAgency(agencyPks, form.startTime, form.endTime,form.code,form.dateFormat);//
				}
				
				response.data = reportModel;
				response.resultCode=ErrorCode.SUCCESS;
				
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::prepayReportForm',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
	/**
	 * 留资报表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saleLeadsReportForm")
	public Response saleLeadsReportForm (@RequestBody ReportForm form) {
		Response response=new Response();
		LOG.info("{method:'ReportController::saleLeadsReportForm',in:"+JSON.toJSONString(form)+"}");
		if(StringUtils.isEmpty(form.code)||StringUtils.isEmpty(form.startTime)||
				StringUtils.isEmpty(form.endTime)||StringUtils.isEmpty(form.isStore)||StringUtils.isEmpty(form.roleCode)) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}else {
			try {
				ReportModel reportModel = new  ReportModel();
				List<String> agencyPks = reportService.getAppGroupInfo(form.code);//调用Hybris接口，返回agencyPks
				if ("Y".equals(form.isStore)) {//判断是否门店
					if (StringUtils.isEmpty(form.storeId)) {
						response.resultCode = ErrorCode.INVALID_PARAM;
						response.message="参数错误";
						return response;
					}else {
						
						reportModel = reportService.SalesLeadesOfStore(form.storeId, form.startTime, form.endTime,form.dateFormat);//
					}
				}else {
					reportModel = reportService.SalesLeadesOfAgency(agencyPks, form.startTime, form.endTime,form.code,form.dateFormat);//
				}

				response.data = reportModel;
				response.resultCode=ErrorCode.SUCCESS;
				
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportController::saleLeadsReportForm',out:"+JSON.toJSONString(response)+"}");
		return response;
	}
	
}
