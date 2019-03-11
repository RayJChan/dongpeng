package com.dpmall.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.dpmall.datasvr.api.IReportDetailService;
import com.dpmall.datasvr.api.IReportService;
import com.dpmall.web.controller.form.ReportDetailForm;

@Controller
@RequestMapping("/jsp")
public class ReportDetailOfJspController {
	
	@Autowired
	private IReportDetailService reportDetailService;
	
	@Autowired
	private IReportService reportService; 
	
	private final Logger LOG=LoggerFactory.getLogger(ReportDetailOfJspController.class);
	
	
	
	/**
	 * 传参数到JSP（排名）
	 * 
	 */
	private ReportDetailForm getHttpRuquestParam(HttpServletRequest request) {
		ReportDetailForm form = new ReportDetailForm();
		form.setCode(request.getParameter("code"));
		form.setIndex(Integer.parseInt(request.getParameter("index")));
		form.setRoleCode(request.getParameter("roleCode"));
		form.setDateFormat(request.getParameter("dateFormat"));
		form.setStartTime(request.getParameter("startTime"));
		form.setEndTime(request.getParameter("endTime"));
		form.setStartNum(Integer.parseInt(request.getParameter("startNum")));
		form.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		form.setPastRowNum(Integer.parseInt(request.getParameter("pastRowNum")));
		form.setPastResult(new BigDecimal(request.getParameter("pastResult")));
		form.setAccessId(request.getParameter("accessId"));
		form.setToken(request.getParameter("token"));
		return form;
	}
	

	/**
	 * 跳转jsp页面（排名）
	 * @param form
	 * @param apiName
	 * @return
	 */
//	@RequestMapping("/sortOfJsp")
	public ModelAndView sortOfJsp( ReportDetailForm form,String apiName ) {
		String apiNameReturn = "";
		String url = "";
		if ("orderDistributedSortOfJsp".equals(apiName)) {
			apiNameReturn = "派单数";
			url = "orderDistributedSortOfData";
			
		}else if ("orderDistributeAVGSortOfJsp".equals(apiName)) {
			apiNameReturn = "派单平均单值";
			url = "orderDistributeAVGSortOfData";
			
		}else if ("orderAcceptAVGSortOfJsp".equals(apiName)) {
			apiNameReturn = "接单数";
			url = "orderAcceptAVGSortOfData";
			
		}else if ("orderAcceptRateSortOfJsp".equals(apiName)) {
			apiNameReturn = "累计接单率";
			url = "orderAcceptRateSortOfData";
			
		}else if ("orderDeliverAVGTimeSortOfJsp".equals(apiName)) {
			apiNameReturn = "平均发货时长";
			url = "orderDeliverAVGTimeSortOfData";
			
		}else if ("prepayWriteOffAVGSortOfJsp".equals(apiName)) {
			apiNameReturn = "核销平均值";
			url = "prepayWriteOffAVGSortOfData";
			
		}else if ("prepayWriteOffRateSortOfJsp".equals(apiName)) {
			apiNameReturn = "核销率";
			url = "prepayWriteOffRateSortOfData";
			
		}else if ("prepayWriteOffAVGTimeSortOfJsp".equals(apiName)) {//需要显示暂无数据
			apiNameReturn = "核销平均时长";
			url = "prepayWriteOffAVGTimeSortOfData";
			
		}else if ("saleConversionAVGSortOfJsp".equals(apiName)) {
			apiNameReturn = "转化平均值";
			url = "saleConversionAVGSortOfData";
			
		} else if ("saleConversionAVGTimeSortOfJsp".equals(apiName)) {
			apiNameReturn = "平均转化时长";
			url = "saleConversionAVGTimeSortOfData";
			
		}else if ("saleConversionRateSortOfJsp".equals(apiName)) {
			apiNameReturn = "转化率";
			url = "saleConversionRateSortOfData";
		}
		
		ModelMap modelMap = new ModelMap();
		
		modelMap.addAttribute("inParam", form);
		modelMap.addAttribute("apiName", apiNameReturn);
		modelMap.addAttribute("url", url);
		
		ModelAndView modelAndView = new ModelAndView("sort/sort",modelMap);
		return modelAndView;
		
	}
	
	
	
	
	/**
	 * 实物 派单数 排名跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/orderDistributedSortOfJsp")
	public ModelAndView orderDistributedSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::orderDistributedSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "orderDistributedSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::orderDistributedSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 实物 派单平均单值 排名  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/orderDistributeAVGSortOfJsp")
	public ModelAndView orderDistributeAVGSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::orderDistributeAVGSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "orderDistributeAVGSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::orderDistributeAVGSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	
	/**
	 * 实物 接单平均单值排名  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/orderAcceptAVGSortOfJsp")
	public ModelAndView orderAcceptAVGSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::orderAcceptAVGSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "orderAcceptAVGSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::orderAcceptAVGSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 实物累计接单率 排名  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/orderAcceptRateSortOfJsp")
	public ModelAndView orderAcceptRateSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::orderAcceptRateSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "orderAcceptRateSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::orderAcceptRateSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 平均发货时长 排名  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/orderDeliverAVGTimeSortOfJsp")
	public ModelAndView orderDeliverAVGTimeSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "orderDeliverAVGTimeSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::orderDeliverAVGTimeSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	
	/**
	 * 特权订金 核销平均值  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/prepayWriteOffAVGSortOfJsp")
	public ModelAndView prepayWriteOffAVGSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "prepayWriteOffAVGSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 特权订金 核销率  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/prepayWriteOffRateSortOfJsp")
	public ModelAndView prepayWriteOffRateSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "prepayWriteOffRateSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::prepayWriteOffRateSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 核销平均时长  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/prepayWriteOffAVGTimeSortOfJsp")
	public ModelAndView prepayWriteOffAVGTimeSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "prepayWriteOffAVGTimeSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::prepayWriteOffAVGTimeSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	
	

	/**
	 * 留资 转化平均值  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/saleConversionAVGSortOfJsp")
	public ModelAndView saleConversionAVGSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::saleConversionAVGSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "saleConversionAVGSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::saleConversionAVGSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 留资 转化率  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/saleConversionRateSortOfJsp")
	public ModelAndView saleConversionRateSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::saleConversionRateSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "saleConversionRateSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::saleConversionRateSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	/**
	 * 留资平均转化时长  跳转jsp
	 * 
	 * @param form
	 * @return
	 */

	@RequestMapping("/saleConversionAVGTimeSortOfJsp")
	public ModelAndView saleConversionAVGTimeSortOfJsp(HttpServletRequest request) {
		
		ReportDetailForm form = this.getHttpRuquestParam(request);
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeSortOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		ModelAndView modelAndView = sortOfJsp(form, "saleConversionAVGTimeSortOfJsp");
		
		LOG.info("{method:'ReportDetailController::saleConversionAVGTimeSortOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");

		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	//-----------------top5---------------
	
	/**
	 * 跳转Top5的jsp
	 */
	@ResponseBody
	@RequestMapping("/distributeCategoryTop52")
	public ModelAndView distributeCategoryTop5(@RequestBody ReportDetailForm form) {
		LOG.info("{method:'ReportDetailController::distributeCategoryTop5',in:" + JSON.toJSONString(form) + "}");
		// 传参数
		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("code", form.getCode());
		modelMap.addAttribute("startTime", form.getStartTime());
		modelMap.addAttribute("endTime", form.getEndTime());
		modelMap.addAttribute("category", form.getCategory());
		modelMap.addAttribute("isStore", form.getIsStore());
		modelMap.addAttribute("storeId", form.getStoreId());
		modelMap.addAttribute("agencyId", form.getAgencyId());
		modelMap.addAttribute("parameter", form);

		List<String > x =new ArrayList<>(); 
		x.add("String");
		x.add("11");
		x.get(x.size()-1);
		// 调用jsp页面
		ModelAndView modelAndView = new ModelAndView("top5", modelMap);
		LOG.info("{method:'ReportDetailController::distributeCategoryTop5',out:" + JSON.toJSONString(modelAndView)
				+ "}");
		return modelAndView;
	}
	
	
	
	/**
	 * 跳转Top5的jsp 
	 */
	public ModelAndView top5OfJsp(ReportDetailForm form,String apiName) {
		LOG.info("{method:'ReportDetailController::distributeCategoryTop5',in:" + JSON.toJSONString(form) + "}");
		String url = "";
		if ("distributeCategoryTop5OfJsp".equals(apiName)) {
			url = "distributeTop5OfData";
			
		}else if ("acceptCategoryTop5OfJsp".equals(apiName)) {
			url = "acceptCategoryTop5OfData";
		}
		//传参数
		ModelMap modelMap = new ModelMap();
		
		modelMap.addAttribute("inParam", form);
		modelMap.addAttribute("url", url);
		
		//调用jsp页面
		ModelAndView modelAndView = new ModelAndView("top5/top5", modelMap);
		LOG.info("{method:'ReportDetailController::distributeCategoryTop5',out:" + JSON.toJSONString(modelAndView) + "}");
		return modelAndView;
	}
	
	
	
	
	
	/**
	 * 跳转Top5的jsp (派单 各品类销售Top5)
	 */
	@ResponseBody
	@RequestMapping("/distributeCategoryTop5OfJsp")
	public ModelAndView distributeCategoryTop5(HttpServletRequest request) {
		ReportDetailForm form = new ReportDetailForm();
		form.setCode(request.getParameter("code"));
		form.setStartTime(request.getParameter("startTime"));
		form.setEndTime(request.getParameter("endTime"));
		form.setCategory(request.getParameter("category"));
		form.setIsStore(request.getParameter("isStore"));
		form.setStoreId(request.getParameter("storeId"));
		form.setAccessId(request.getParameter("accessId"));
		form.setToken(request.getParameter("token"));
		 
		LOG.info("{method:'ReportDetailController::distributeCategoryTop5OfJsp',in:" + JSON.toJSONString(form) + "}");
	
		
		
		//调用jsp页面
		ModelAndView modelAndView = top5OfJsp(form,"distributeCategoryTop5OfJsp");
		
		LOG.info("{method:'ReportDetailController::distributeCategoryTop5OfJsp',out:" + JSON.toJSONString(modelAndView) + "}");
		return modelAndView;
	}
	
	
	
	/**
	 * 跳转Top5的jsp (派单 各品类销售Top5)
	 */
	@ResponseBody
	@RequestMapping("/acceptCategoryTop5OfJsp")
	public ModelAndView acceptCategoryTop5OfJsp(HttpServletRequest request) {
		ReportDetailForm form = new ReportDetailForm();
		form.setCode(request.getParameter("code"));
		form.setStartTime(request.getParameter("startTime"));
		form.setEndTime(request.getParameter("endTime"));
		form.setCategory(request.getParameter("category"));
		form.setIsStore(request.getParameter("isStore"));
		form.setStoreId(request.getParameter("storeId"));
		form.setAccessId(request.getParameter("accessId"));
		form.setToken(request.getParameter("token"));
		LOG.info("{method:'ReportDetailController::acceptCategoryTop5OfJsp',in:" + JSON.toJSONString(form) + "}");

		//调用jsp页面
		ModelAndView modelAndView = top5OfJsp(form,"acceptCategoryTop5OfJsp");
		
		LOG.info("{method:'ReportDetailController::acceptCategoryTop5OfJsp',out:" + JSON.toJSONString(modelAndView) + "}");
		return modelAndView;
	}
	
	
	//--------------------------历史趋势---------------------------
	
	/**
	 * 跳转历史趋势的jsp 
	 */
	@RequestMapping("/historyOfJsp")
	public ModelAndView historyOfJsp(HttpServletRequest request/*ReportDetailForm form,String apiName*/ ) {
		//赋值参数2
		ReportDetailForm form = new ReportDetailForm();
		form.setCode(request.getParameter("code"));
		form.setDateFormat(request.getParameter("dateFormat"));
		form.setStartTime(request.getParameter("startTime"));
		form.setEndTime(request.getParameter("endTime"));
		form.setStartNum(Integer.parseInt(request.getParameter("startNum")));
		form.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		form.setIsStore(request.getParameter("isStore"));
		form.setStoreId(request.getParameter("storeId"));
		form.setAccessId(request.getParameter("accessId"));
		form.setToken(request.getParameter("token"));
		LOG.info("{method:'ReportDetailController::historyOfJsp',in:" + JSON.toJSONString(form) + "}");
		
		String url = "";
		String apiNameReturn = "";
		String apiName = request.getParameter("apiName");
		
		//赋值参数1
		if ("orderDistributeHistory".equals(apiName)) {
			url = "orderDistributeHistoryOfData";
			apiNameReturn = "派单数";
		}else if ("orderDistributeAVGHistory".equals(apiName)) {
			url = "orderDistributeAVGHistoryOfData";
			apiNameReturn = "派单平均单值";
		}else if ("orderAcceptHistory".equals(apiName)) {
			url = "orderAcceptHistoryOfData";
			apiNameReturn = "接单数";
		}else if ("orderAcceptAVGHistory".equals(apiName)) {
			url = "orderAcceptAVGHistoryOfData";
			apiNameReturn = "接单平均单值";
		}else if ("orderAcceptRateHistory".equals(apiName)) {
			url = "orderAcceptRateHistoryOfData";
			apiNameReturn = "累计接单率";
		}else if ("orderDeliverAVGTimeHistory".equals(apiName)) {
			url = "orderDeliverAVGTimeHistoryOfData";
			apiNameReturn = "发货平均时长";
		}else if ("prepayDistributeHistory".equals(apiName)) {
			url = "prepayDistributeHistoryOfData";
			apiNameReturn = "派单数";
		}else if ("prepayWriteOffCountHistory".equals(apiName)) {
			url = "prepayWriteOffCountHistoryOfData";
			apiNameReturn = "核销数";
		}else if ("prepayWriteOffRateHistory".equals(apiName)) {
			url = "prepayWriteOffRateHistoryOfData";
			apiNameReturn = "核销率";
		} else if ("prepayWriteOffAVGHistory".equals(apiName)) {
			url = "prepayWriteOffAVGHistoryOfData";
			apiNameReturn = "核销平均值";
		} else if ("prepayWriteOffAVGTimeHistory".equals(apiName)) {
			url = "prepayWriteOffAVGTimeHistoryOfData";
			apiNameReturn = "核销平均时长";
		} else if ("saleConversionCountHistory".equals(apiName)) {
			url = "saleConversionCountHistoryOfData";
			apiNameReturn = "转化数";
		} else if ("saleConversionAVGHistory".equals(apiName)) {
			url = "saleConversionAVGHistoryOfData";
			apiNameReturn = "转化平均值";
		} else if ("saleConversionRateHistory".equals(apiName)) {
			url = "saleConversionRateHistoryOfData";
			apiNameReturn = "转化率";
		} else if ("saleConversionAVGTimeHistory".equals(apiName)) {
			url = "saleConversionAVGTimeHistoryOfData";
			apiNameReturn = "转化平均时长";
		}
		
		
		
		//返回参数至jsp
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("inParam", form);
		modelMap.addAttribute("url", url);
		modelMap.addAttribute("apiNameReturn", apiNameReturn);
		
		//调用jsp页面
		ModelAndView modelAndView = new ModelAndView("history/history", modelMap);
		LOG.info("{method:'ReportDetailController::historyOfJsp',out:" + JSON.toJSONString(modelAndView) + "}");
		return modelAndView;
	}
	
	
	
	
	
}
