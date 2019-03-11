package com.dpmall.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dpmall.datasvr.api.IFirstPageService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.FirstPageModel;
import com.dpmall.web.controller.form.FirstPageForm;
import com.dpmall.web.controller.form.Response;

/**
 * 首页数据
 * 
 * @author cwj
 * @since 2017-12-13
 */
@Controller
@RequestMapping("/firstPage")
public class FirstPageController {
	private static final Logger LOG = LoggerFactory.getLogger(FirstPageController.class);

	@Autowired
	private IFirstPageService firstPageService;

	/**
	 * 获取助销宝首页数据
	 */
	@RequestMapping(value = "/getFirstPageTest", method = { RequestMethod.GET,	RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Response getFirstPageTest(@RequestBody FirstPageForm form) {
		LOG.info("{method:'OrderController::getOnePage4Distribute',in:" + JSON.toJSONString(form) + "}");
		Response res = new Response();
		if (StringUtils.isEmpty(form.code) || StringUtils.isEmpty(form.storeId) || StringUtils.isEmpty(form.roleCode)) {
			res.resultCode = ErrorCode.INVALID_PARAM;
			res.message = "参数错误";
		} else {
			try {

				FirstPageModel model = new FirstPageModel();
				res.resultCode = ErrorCode.SUCCESS;
				model.salesInfo.put("salesWaitAcceptCount", "93");
				model.salesInfo.put("salesFollowingCount", "101");
				model.salesInfo.put("salesFinishedCount", "56");
				model.salesInfo.put("salesComparison", "UP");
				model.salesInfo.put("time", "2017年11月数据");

				model.orderInfo.put("orderWaitAcceptCount", "63");
				model.orderInfo.put("orderFollowingCount", "99");
				model.orderInfo.put("orderWaifForRefundCount", "88");
				model.orderInfo.put("orderFinishedCount", "74");
				model.orderInfo.put("orderComparison", "DOWN");
				model.orderInfo.put("time", "2017年11月数据");

//				model.prePayInfo.put("prePayWaitAcceptCount", "56");
//				model.prePayInfo.put("prePayFollowingCount", "79");
//				model.prePayInfo.put("prePayFinishedCount", "88");
//				model.prePayInfo.put("prePayComparison", "EQUAL");
//				model.prePayInfo.put("time", "2017年11月数据");

				model.reportInfo.put("conversionRate", "67");
				model.reportInfo.put("conversionRateComparison", "UP");
				model.reportInfo.put("acceptRate", "89");
				model.reportInfo.put("acceptRateComparison", "EQUAL");
				model.reportInfo.put("writeOffRate", "78");
				model.reportInfo.put("writeOffRateComparison", "DOWN");
				model.reportInfo.put("time", "2017年11月数据");

//				model.pictures.add("https://sit.dpmall.com/dpmall-web/images/firstPageHead/1513134983817.png");

				res.data = model;

			} catch (Throwable e) {
				res.resultCode = ErrorCode.INTERNAL_ERR;
				res.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'FirstPageController::getFirstPageTest',out:{res:'" + JSON.toJSONString(res) + "'}}");
		return res;
	}

	/**
	 * 获取助销宝首页数据
	 */
	@RequestMapping(value = "/getFirstPage", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Response getFirstPage(@RequestBody FirstPageForm form) {
		LOG.info("{method:'FirstPageController::getFirstPage',in:" + JSON.toJSONString(form) + "}");
		Response res = new Response();
		if (StringUtils.isEmpty(form.code) || StringUtils.isEmpty(form.storeId) || StringUtils.isEmpty(form.roleCode)) {
			res.resultCode = ErrorCode.INVALID_PARAM;
			res.message = "参数错误";
		} else {
			try {
				FirstPageModel model = new FirstPageModel();
				if ("clerk".equals(form.roleCode) || "manager".equals(form.roleCode)) {//门店账号
					model = firstPageService.getFirstPageOfStore(form.storeId);
				}else if ("agency".equals(form.roleCode)){//非门店账号
					model = firstPageService.getFirstPageOfAgency(form.code);
				}else {
					res.resultCode = ErrorCode.SUCCESS;
					res.message = "该账号没有登录权限，请联系东鹏客服";
					return res;
				}
				res.data = model;

			} catch (Throwable e) {
				res.resultCode = ErrorCode.INTERNAL_ERR;
				res.message = "系统错误";
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info("{method:'FirstPageController::getFirstPage',out:{res:'" + JSON.toJSONString(res) + "'}}");
		return res;
	}

}
