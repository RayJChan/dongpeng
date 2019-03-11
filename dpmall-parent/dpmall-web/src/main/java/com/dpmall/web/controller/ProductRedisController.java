package com.dpmall.web.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.LogUtil;
import com.dpmall.common.RedisKeyNamesUtils;
import com.dpmall.common.RedisUtils;
import com.dpmall.datasvr.api.IProductService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.ProductModel;
import com.dpmall.web.controller.form.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品缓存
 * @author cwj
 *
 */
@Controller
@RequestMapping("/productRedis")
public class ProductRedisController {


	@Autowired
	private IProductService productService2;


	/**
	 * 获取产品信息
	 *  productCode ：产品型号
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/getProductInfo", method = RequestMethod.POST)
	public Response getProductInfo( @RequestBody Map<String,String> form) {
		
		LogUtil.controllerLogInfo("{method:'ProductRedisController::getProductInfo'");

		Response res = new Response();
		Jedis jedis = RedisUtils.getClient();
		if (StringUtils.isEmpty(form.get("productCode"))) {
			res.resultCode = ErrorCode.INVALID_PARAM;
			res.message = "参数错误";
		} else {
			try {
				ProductModel out = new ProductModel();
				String productCode = form.get("productCode").toUpperCase().trim();
				String info = jedis.hget(RedisKeyNamesUtils.getProductInfo(), productCode);
				if (info == null) {// 不存在缓存时
					out= productService2.getInfoByProductCode(productCode);
					if (out.getProductCode() == null) {
						res.message="产品型号错误";
						res.resultCode= ErrorCode.INVALID_PARAM;
						return res;
					}
					// 将信息存入redis数据
					jedis.hset(RedisKeyNamesUtils.getProductInfo(), productCode, JSON.toJSONString(out));
				} else {// 存在缓存时
					Object object = JSON.parseObject(info, ProductModel.class);
					out = (ProductModel) object;
				}

				res.resultCode = ErrorCode.SUCCESS;
				res.message = "获取数据成功";
				res.data = out;

			} catch (Exception e) {
				res.resultCode = ErrorCode.INTERNAL_ERR;
				res.message = "系统错误";
				LogUtil.controllerLogError(e.getMessage(), e);
			} finally {
				RedisUtils.returnResource(jedis);
			}
		}

		LogUtil.controllerLogInfo("{method:'ProductRedisController::getProductInfo',out:{res'" + JSON.toJSONString(res) + "'}}");
		return res;
	}


	/**
	 * 获取产品型号
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProductCodeList", method = RequestMethod.POST)
	public Response getProductCodeList(@RequestBody Map<String, String> form) {

		LogUtil.controllerLogInfo("{method:'ProductRedisController::getProductCodeList'");

		Response res = new Response();
		Jedis jedis = RedisUtils.getClient();
		if (StringUtils.isEmpty(form.get("search"))) {
//			res.resultCode = ErrorCode.INVALID_PARAM;
//			res.message = "参数错误";
			form.put("search", "F");//若为空的时候赋值

		}
		try {
			List<String> list = new ArrayList<>();
			StringBuffer buffer = new StringBuffer();
			buffer.append(form.get("search").toUpperCase().trim());
			buffer.append("*");
			ScanParams scanParams = new ScanParams();
			scanParams.match(buffer.toString());
			scanParams.count(10000);//只返回10条

			ScanResult<String> result = jedis.sscan("productCodes", "0", scanParams);
			while (true) {
				int cursor = result.getCursor();
				if (CollectionUtils.isEmpty(result.getResult())) {
					if (cursor == 0) {
						break;
					}
					result = jedis.sscan("productCodes", cursor, scanParams);
				} else {
					break;
				}
			}
			int size = result.getResult().size();
			if (size == 0) {
				res.data = list;
				res.resultCode = ErrorCode.SUCCESS;
				res.message = "获取数据成功";
				return res;
			}
			if (size < 10) {
				list.addAll(result.getResult());
			} else {
				list.addAll(result.getResult().subList(0, 9));
			}

			res.data = list;

			res.resultCode = ErrorCode.SUCCESS;
			res.message = "获取数据成功";


		} catch (Exception e) {
			res.resultCode = ErrorCode.INTERNAL_ERR;
			res.message = "系统错误";
			LogUtil.controllerLogError(e.getMessage(), e);
		} finally {
			RedisUtils.returnResource(jedis);
		}


		LogUtil.controllerLogInfo("{method:'ProductRedisController::getProductCodeList',out:{res'" + JSON.toJSONString(res) + "'}}");
		return res;
	}


}
