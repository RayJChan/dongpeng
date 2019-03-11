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
import com.dpmall.datasvr.api.IReportDetailService;
import com.dpmall.datasvr.api.IReportService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.ReportDetailModel;
import com.dpmall.web.controller.form.ReportDetailForm;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/reportDetail")
public class ReportDetailController {

	@Autowired
	private IReportDetailService reportDetailService;

	@Autowired
	private IReportService reportService;
	
	private static final String URL = "https://sitoms.dpmall.com/dpmall-web/jsp/";
//	private static final String URL = "localhost:8080/dpmall-web/jsp/";

	private final Logger LOG = LoggerFactory.getLogger(ReportDetailController.class);

	// ------------------------------------------历史趋势--------------------------------------------------
		//----------------------jsp------------------------------------
		

		/**
		 * 请求jsp传参内容
		 * @param form
		 * @param apiName：接口的名称
		 * @return
		 */
		private String getParamUrlOfHistory(ReportDetailForm form,String apiName) {
			return "?code="+form.getCode()+"&dateFormat="+form.getDateFormat()+"&startTime="+form.getStartTime() +"&endTime="+ form.getEndTime()
			+"&startNum="+form.getStartNum() +"&pageSize="+form.getPageSize()+"&isStore="+form.getIsStore()+"&storeId="+form.getStoreId()
			+"&accessId="+form.getAccessId()+"&token="+form.getToken()+"&apiName="+apiName;
		}
		

		/**
		 *实物 派单数 历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/orderDistributeHistory")
		public Response orderDistributeHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::orderDistributeHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId()))  {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"orderDistributeHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::orderDistributeHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		
		/**
		 *实物 派单平均单值 历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/orderDistributeAVGHistory")
		public Response orderDistributeAVGHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::orderDistributeAVGHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"orderDistributeAVGHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::orderDistributeAVGHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *实物 接单数 历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/orderAcceptHistory")
		public Response orderAcceptHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::orderAcceptHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"orderAcceptHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::orderAcceptHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *实物 接单平均单值 历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/orderAcceptAVGHistory")
		public Response orderAcceptAVGHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::orderAcceptAVGHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"orderAcceptAVGHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::orderAcceptAVGHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *实物 累计接单率 历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/orderAcceptRateHistory")
		public Response orderAcceptRateHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::orderAcceptRateHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"orderAcceptRateHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::orderAcceptRateHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *实物 平均发货时长 历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/orderDeliverAVGTimeHistory")
		public Response orderDeliverAVGTimeHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"orderDeliverAVGTimeHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *特权订金 派单历史趋势 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/prepayDistributeHistory")
		public Response prepayDistributeHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::prepayDistributeHistory',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"prepayDistributeHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::prepayDistributeHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		
		/**
		 *特权订金 核销数 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/prepayWriteOffCountHistory")
		public Response prepayWriteOffCountHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::preprepayWriteOffCountHistoryn:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"prepayWriteOffCountHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::prepayWriteOffCountHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *特权订金 核销数 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/prepayWriteOffAVGHistory")
		public Response prepayWriteOffAVGHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::preprepayWriteOffAVGHistoryn:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"prepayWriteOffAVGHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::prepayWriteOffAVGHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		
		/**
		 *特权订金 核销率 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/prepayWriteOffRateHistory")
		public Response prepayWriteOffRateHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::preprepayWriteOffRateHistoryn:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"prepayWriteOffRateHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::prepayWriteOffRateHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}

		
		/**
		 *特权订金 核销平均时长 (跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/prepayWriteOffAVGTimeHistory")
		public Response prepayWriteOffAVGTimeHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeHistory:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"prepayWriteOffAVGTimeHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *留资 转化笔数 历史趋势(跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/saleConversionCountHistory")
		public Response saleConversionCountHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::saleConversionCountHistory:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"saleConversionCountHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::saleConversionCountHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *留资 转化平均值(跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/saleConversionAVGHistory")
		public Response saleConversionAVGHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::saleConversionAVGHistory:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"saleConversionAVGHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::saleConversionAVGHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		

		/**
		 *留资 转化率(跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/saleConversionRateHistory")
		public Response saleConversionRateHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::saleConversionRateHistory:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"saleConversionRateHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::saleConversionRateHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
		/**
		 *留资平均转化时长 历史趋势(跳转jsp)
		 */
		@ResponseBody
		@RequestMapping("/saleConversionAVGTimeHistory")
		public Response saleConversionAVGTimeHistory(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::saleConversionAVGTimeHistory:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
					|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				try {
					response.data = URL+"historyOfJsp"+this.getParamUrlOfHistory(form,"saleConversionAVGTimeHistory");
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage(), e);
				}
			}
			LOG.info("{method:'ReportDetailController::saleConversionAVGTimeHistory',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
		
	
	
	// --------------------------------------实物------------------------------------------
	/**
	 * 实物 派单数 历史趋势
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderDistributeHistoryOfData")
	public Response orderDistributeHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributeHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
				|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty(form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.orderDistributedHistory(form.getCode(),
						form.getDateFormat(), form.getStartNum(), form.getPageSize(), form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderDistributeHistoryOfData',out:" + JSON.toJSONString(response) + "}");
		return response;
	}

	/**
	 * 实物 派单平均单值 历史趋势
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderDistributeAVGHistoryOfData")
	public Response orderDistributeAVGHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributeAVGHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
				|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.orderDistributeAVGHistory(form.getCode(),
						form.getDateFormat(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::orderDistributeAVGHistoryOfData',out:" + JSON.toJSONString(response) + "}");
		return response;
	}

	/**
	 * 实物 接单数 历史趋势
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderAcceptHistoryOfData")
	public Response orderAcceptHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
				|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> reportDetailModel = new ArrayList<ReportDetailModel>();
				reportDetailModel = reportDetailService.orderAcceptHistory(form.getCode(), form.getDateFormat(), form.getStartNum(),
						form.getPageSize(),  form.getEndTime());// 调用接口

				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 接单平均单值 历史趋势
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderAcceptAVGHistoryOfData")
	public Response orderAcceptAVGHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptAVGHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
				|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.orderAcceptAVGHistory(form.getCode(),
						form.getDateFormat(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptAVGHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 累计接单率 历史趋势
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderAcceptRateHistoryOfData")
	public Response orderAcceptRateHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptRateHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
				|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.orderAcceptRateHistory(form.getCode(),
						form.getDateFormat(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptRateHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 平均发货时长 历史趋势
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderDeliverAVGTimeHistoryOfData")
	public Response orderDeliverAVGTimeHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || form.getStartNum() == null
				|| form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.orderDeliverAVGTimeHistory(form.getCode(),
						form.getDateFormat(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeHistoryOfData',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}

	// --------------------特权订金----------------------------------------------------
	/**
	 * 特权订金 派单 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayDistributeHistoryOfData")
	public Response prepayDistributeHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayDistributeHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.prepayDistributeHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}

		}
		LOG.info("{method:'ReportDetailController::prepayDistributeHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 特权订金 核销数 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffCountHistoryOfData")
	public Response prepayWriteOffCountHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffCountHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.prepayWriteOffCountHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffCountHistoryOfData',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}

	/**
	 * 特权订金 核销平均值 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffAVGHistoryOfData")
	public Response prepayWriteOffAVGHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.prepayWriteOffAVGHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 特权订金 核销率 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffRateHistoryOfData")
	public Response prepayWriteOffRateHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.prepayWriteOffRateHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::prepayWriteOffRateHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 特权订金 核销平均时长 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffAVGTimeHistoryOfData")
	public Response prepayWriteOffAVGTimeHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.prepayWriteOffAVGTimeHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeHistoryOfData',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}

	// --------------------------------------留资--------------------------------------------

	/**
	 * 留资 转化笔数 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionCountHistoryOfData")
	public Response saleConversionCountHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionCountHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.saleConversionCountHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionCountHistoryOfData',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}

	/**
	 * 留资 转化平均值 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionAVGHistoryOfData")
	public Response saleConversionAVGHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionAVGHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.saleConversionAVGHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionAVGHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 留资 转化率 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionRateHistoryOfData")
	public Response saleConversionRateHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionRateHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.saleConversionRateHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::saleConversionRateHistoryOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 留资 平均转化时长 历史趋势
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionAVGTimeHistoryOfData")
	public Response saleConversionAVGTimeHistoryOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeHistoryOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty(form.getIsStore())
				|| form.getStartNum() == null || form.getPageSize() == null || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty( form.getEndTime())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> reportDetailModel = reportDetailService.saleConversionAVGTimeHistory(form.getCode(),
						form.getDateFormat(), form.getStoreId(), form.getIsStore(), form.getStartNum(), form.getPageSize(),  form.getEndTime());// 调用接口
				response.data = reportDetailModel;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeHistoryOfData',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}
	
	
	

	// ----------------------------------------------------------排名------------------------------------------------------------
	
	
	//---------------------------jsp------------
	
	/**
	 * 返回传参的路径
	 * @param form
	 * @return
	 */
	private String getParamUrlOfSort(ReportDetailForm form) {
		return "?code="+form.getCode()+"&roleCode="+form.getRoleCode()+"&startTime="+form.getStartTime() +"&endTime="+ form.getEndTime()
		+"&dateFormat="+form.getDateFormat()+"&index="+form.getIndex()+"&startNum="+form.getStartNum() +"&pageSize="+form.getPageSize()
		+"&pastRowNum="+form.getPastRowNum()+"&pastResult="+form.getPastResult()+"&accessId="+form.getAccessId()+"&token="+form.getToken();
	}
	
	
	//-------接受参数 返回jsp地址-- ----  实物------
	
	/**
	 * 实物 派单数 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderDistributedSort")
	public Response orderDistributedSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributedSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"orderDistributedSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderDistributedSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 实物 派单平均单值(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderDistributeAVGSort")
	public Response orderDistributeAVGSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributeAVGSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"orderDistributeAVGSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderDistributeAVGSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 实物接单平均单值排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderAcceptAVGSort")
	public Response orderAcceptAVGSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptAVGSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"orderAcceptAVGSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptAVGSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 实物累计接单率 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderAcceptRateSort")
	public Response orderAcceptRateSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptRateSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"orderAcceptRateSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptRateSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 实物  平均发货时长 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderDeliverAVGTimeSort")
	public Response orderDeliverAVGTimeSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"orderDeliverAVGTimeSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 *特权订金 核销平均值 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffAVGSort")
	public Response prepayWriteOffAVGSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"prepayWriteOffAVGSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 特权订金 核销率 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffRateSort")
	public Response prepayWriteOffRateSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"prepayWriteOffRateSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 核销平均时长 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffAVGTimeSort")
	public Response prepayWriteOffAVGTimeSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"prepayWriteOffAVGTimeSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 留资 转化平均值  (传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionAVGSort")
	public Response saleConversionAVGSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionAVGSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"saleConversionAVGSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionAVGSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 留资 转化率 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionRateSort")
	public Response saleConversionRateSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionRateSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"saleConversionRateSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionRateSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	/**
	 * 留资 转化率 排名(传参数接口)
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionAVGTimeSort")
	public Response saleConversionAVGTimeSort(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null || StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = URL+"saleConversionAVGTimeSortOfJsp"+this.getParamUrlOfSort(form);
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}
	
	
	
	//---------------------------实物      调用数据库------------
	/**
	 * 实物 派单数 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderDistributedSortOfData")
	public Response orderDistributedSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributedSort',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum() == null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderDistributedSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderDistributedSort',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	

	
	/**
	 * 实物 派单平均单值 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderDistributeAVGSortOfData")
	public Response orderDistributeAVGSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributeAVGSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderDistributeAVGSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderDistributeAVGSortOfData',out:" + JSON.toJSONString(response) + "}");
		return response;
	}

	/**
	 * 实物 接单平均单值 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderAcceptAVGSortOfData")
	public Response orderAcceptAVGSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptAVGSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderAcceptAVGSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptAVGSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 累计接单率 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderAcceptRateSortOfData")
	public Response orderAcceptRateSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptRateSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderAcceptRateSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptRateSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 平均发货时长 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/orderDeliverAVGTimeSortOfData")
	public Response orderDeliverAVGTimeSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderDeliverAVGTimeSort(form.getCode(), form.getIndex(), form.getRoleCode(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(),
						form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	// -------------------特权订金---------------------
	/**
	 * 特权订金 核销平均值 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffAVGSortOfData")
	public Response prepayWriteOffAVGSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.prepayWriteOffAVGSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 特权订金 核销率 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffRateSortOfData")
	public Response prepayWriteOffRateSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.prepayWriteOffRateSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 特权订金 核销平均时长 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/prepayWriteOffAVGTimeSortOfData")
	public Response prepayWriteOffAVGTimeSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.prepayWriteOffAVGTimeSort(form.getCode(), form.getIndex(), form.getRoleCode(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(),
						form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::prepayWriteOffAVGTimeSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	// -------------------留资---------------------
	/**
	 * 留资 转化平均值 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionAVGSortOfData")
	public Response saleConversionAVGSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionAVGSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.saleConversionAVGSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionAVGSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 留资 转化率 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionRateSortOfData")
	public Response saleConversionRateSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionRateSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.saleConversionRateSort(form.getCode(), form.getIndex(), form.getRoleCode(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(), form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleConversionRateSortOfData',out:" + JSON.toJSONString(response) + "}");
		return response;
	}

	/**
	 * 留资 平均转化时长 排名
	 * 
	 * @param form
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/saleConversionAVGTimeSortOfData")
	public Response saleConversionAVGTimeSortOfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeSortOfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getRoleCode()) || StringUtils.isEmpty(form.getStartTime())
				|| StringUtils.isEmpty(form.getDateFormat()) || StringUtils.isEmpty( form.getEndTime()) || form.getIndex() == null
				|| form.getStartNum() == null || form.getPageSize() == null || form.getPastRowNum()== null
				|| form.getPastResult() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.saleConversionAVGTimeSort(form.getCode(), form.getIndex(), form.getRoleCode(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getDateFormat(), form.getPastRowNum(),
						form.getPastResult());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::saleConversionAVGTimeSortOfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	// ----------------------区域分布---------------------
	/**
	 * 实物 派单 省区域分布
	 */
	@ResponseBody
	@RequestMapping("/orderDistributeProvinceRegion")
	public Response orderDistributeProvinceRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributeProvinceRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null || form.getPageSize() == null
				|| form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderDistributeProvinceRegion(form.getCode(), form.getIsStore(), form.getStoreId(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(), form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'ReportDetailController::orderDistributeProvinceRegion',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}

	/**
	 * 实物 派单 市区域分布
	 */
	@ResponseBody
	@RequestMapping("/orderDistributeCityRegion")
	public Response orderDistributeCityRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderDistributeCityRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getProvinceName()) || StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null
				|| form.getPageSize() == null || form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderDistributeCityRegion(form.getCode(), form.getProvinceName(), form.getIsStore(),
						form.getStoreId(), form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(),
						form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::orderDistributeCityRegion',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 接单 省区域分布
	 */
	@ResponseBody
	@RequestMapping("/orderAcceptProvinceRegion")
	public Response orderAcceptProvinceRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptProvinceRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null || form.getPageSize() == null
				|| form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderAcceptProvinceRegion(form.getCode(), form.getIsStore(), form.getStoreId(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(), form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info(
				"{method:'ReportDetailController::orderAcceptProvinceRegion',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 实物 接单 市区域分布
	 */
	@ResponseBody
	@RequestMapping("/orderAcceptCityRegion")
	public Response orderAcceptCityRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::orderAcceptCityRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getProvinceName()) || StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null
				|| form.getPageSize() == null || form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.orderAcceptCityRegion(form.getCode(), form.getProvinceName(), form.getIsStore(),
						form.getStoreId(), form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(),
						form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::orderAcceptCityRegion',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 特权定金 省区域分布
	 */
	@ResponseBody
	@RequestMapping("/prePayProvinceRegion")
	public Response prePayProvinceRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prePayProvinceRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null || form.getPageSize() == null
				|| form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.prePayProvinceRegion(form.getCode(), form.getIsStore(), form.getStoreId(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(), form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prePayProvinceRegion',out:" + JSON.toJSONString(response)
				+ "}");

		return response;
	}

	/**
	 * 特权定金 市区域分布
	 */
	@ResponseBody
	@RequestMapping("/prePayCityRegion")
	public Response prePayCityRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::prePayCityRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getProvinceName()) || StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null
				|| form.getPageSize() == null || form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.prePayCityRegion(form.getCode(), form.getProvinceName(), form.getIsStore(), form.getStoreId(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(), form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::prePayCityRegion',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 留资 省区域分布
	 */
	@ResponseBody
	@RequestMapping("/saleProvinceRegion")
	public Response saleProvinceRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleProvinceRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null || form.getPageSize() == null
				|| form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.saleProvinceRegion(form.getCode(), form.getIsStore(), form.getStoreId(), form.getStartTime(),
						 form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(), form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleProvinceRegion',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 留资 市区域分布
	 */
	@ResponseBody
	@RequestMapping("/saleCityRegion")
	public Response saleCityRegion(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::saleCityRegion',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getProvinceName()) || StringUtils.isEmpty(form.getIsStore()) || form.getStartNum() == null
				|| form.getPageSize() == null || form.getPastResult() == null || form.getPastRowNum()== null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.saleCityRegion(form.getCode(), form.getProvinceName(), form.getIsStore(), form.getStoreId(),
						form.getStartTime(),  form.getEndTime(), form.getStartNum(), form.getPageSize(), form.getPastResult(), form.getPastRowNum());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::saleCityRegion',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	// --------------------------------各品类销售Top5------------------------------
	
	
		/**
		 * 返回传参的路径
		 * @param form
		 * @return
		 */
	private String getParamUrlOfTo5(ReportDetailForm form) {
		return "?code=" + form.getCode() + "&startTime=" + form.getStartTime() + "&endTime=" + form.getEndTime()
				+ "&category=" + form.getCategory() + "&isStore=" + form.getIsStore() + "&storeId=" + form.getStoreId()
				+ "&accessId=" + form.getAccessId() + "&token=" + form.getToken();
	}
	
	
		/**
		 * 派单 各品类销售Top5（传参数到jsp）
		 */
		@ResponseBody
		@RequestMapping("/distributeCategoryTop5")
		public Response distributeTop5(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::distributeCategoryTop5',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getCategory()) || StringUtils.isEmpty(form.getIsStore())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId()))  {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				if ("Y".equals(form.getIsStore())) {
					if (StringUtils.isEmpty(form.getStoreId())) {
						response.resultCode = ErrorCode.INVALID_PARAM;
						response.message = "参数错误,门店id不能为空";
						return response;
					}
				}
				try {
					response.data = URL+"distributeCategoryTop5OfJsp"+this.getParamUrlOfTo5(form);
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage());
				}
			}
			LOG.info("{method:'ReportDetailController::distributeCategoryTop5',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
	
		
		/**
		 * 派单 各品类销售Top5 （传参数到jsp）
		 */
		@ResponseBody
		@RequestMapping("/acceptCategoryTop5")
		public Response acceptCategoryTop5(@RequestBody ReportDetailForm form) {
			Response response = new Response();
			LOG.info("{method:'ReportDetailController::acceptCategoryTop5',in:" + JSON.toJSONString(form) + "}");
			if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
					|| StringUtils.isEmpty(form.getCategory()) || StringUtils.isEmpty(form.getIsStore())
					|| StringUtils.isEmpty(form.getToken())|| StringUtils.isEmpty(form.getAccessId()))  {
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
			} else {
				if ("Y".equals(form.getIsStore())) {
					if (StringUtils.isEmpty(form.getStoreId())) {
						response.resultCode = ErrorCode.INVALID_PARAM;
						response.message = "参数错误,门店id不能为空";
						return response;
					}
				}
				try {
					response.data = URL+"acceptCategoryTop5OfJsp"+this.getParamUrlOfTo5(form);
					response.resultCode = ErrorCode.SUCCESS;

				} catch (Exception e) {
					response.resultCode = ErrorCode.INTERNAL_ERR;
					response.message = "系统错误";
					LOG.error(e.getMessage());
				}
			}
			LOG.info("{method:'ReportDetailController::acceptCategoryTop5',out:" + JSON.toJSONString(response) + "}");

			return response;
		}
	
	
	
	/**
	 * 派单 各品类销售Top5 （与数据库交互）
	 */
	@ResponseBody
	@RequestMapping("/distributeTop5OfData")
	public Response distributeTop5OfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::distributeTop5OfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getCategory()) || StringUtils.isEmpty(form.getIsStore())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}
			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.distributeCategoryTop5(form.getCode(), form.getCategory(), form.getIsStore(), form.getStoreId(),
						form.getStartTime(),  form.getEndTime());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::distributeTop5OfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

	/**
	 * 接单 各品类销售Top5 （数据库交互）
	 */
	@ResponseBody
	@RequestMapping("/acceptCategoryTop5OfData")
	public Response acceptCategoryTop5OfData(@RequestBody ReportDetailForm form) {
		Response response = new Response();
		LOG.info("{method:'ReportDetailController::acceptCategoryTop5OfData',in:" + JSON.toJSONString(form) + "}");
		if (StringUtils.isEmpty(form.getCode()) || StringUtils.isEmpty(form.getStartTime()) || StringUtils.isEmpty( form.getEndTime())
				|| StringUtils.isEmpty(form.getCategory()) || StringUtils.isEmpty(form.getIsStore())) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			if ("Y".equals(form.getIsStore())) {
				if (StringUtils.isEmpty(form.getStoreId())) {
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误,门店id不能为空";
					return response;
				}
			}

			try {
				List<ReportDetailModel> model = new ArrayList<ReportDetailModel>();
				model = reportDetailService.acceptCategoryTop5(form.getCode(), form.getCategory(), form.getIsStore(), form.getStoreId(),
						form.getStartTime(),  form.getEndTime());
				response.data = model;
				response.resultCode = ErrorCode.SUCCESS;

			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "系统错误";
				LOG.error(e.getMessage());
			}
		}
		LOG.info("{method:'ReportDetailController::acceptCategoryTop5OfData',out:" + JSON.toJSONString(response) + "}");

		return response;
	}

}
