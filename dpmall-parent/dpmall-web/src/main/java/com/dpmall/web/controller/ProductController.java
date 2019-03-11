package com.dpmall.web.controller;

import com.dpmall.datasvr.api.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.PictureForm;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final Logger LOG = LoggerFactory.getLogger(ProductController.class);


	@Autowired
	private IProductService productService2;


	/**
	 * 获取产品信息
	 */
	@RequestMapping(value = "/getProductInfo", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Response getProductInfo(@RequestBody PictureForm form) {
		LOG.info("{method:'ProductController::getProductInfo',in:" + JSON.toJSONString(form) + "}");
		Response response = new Response();
		if (form.getPageSize() == null || form.getStartNum() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = productService2.getProductInfo(form.getStartNum(), form.getPageSize());
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
			}
		}
		LOG.info("{method:'ProductController::getProductInfo',out:{res:'" + JSON.toJSONString(response) + "'}}");
		return response;
	}
	

}
