package com.dpmall.web.controller;


import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
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
import com.dpmall.datasvr.api.IPrepayService;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.AppOrderForm;
import com.dpmall.web.controller.form.AppPrepayForm;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/prepay")
public class PrepayController {

	private static final Logger LOG = LoggerFactory.getLogger(PrepayController.class);
	
	@Autowired
	private IPrepayService prepayService;
	
	
	
	/**
	 * 特权定金销商商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param startNum 上一次加载的最后项
	 * @param pageSize 页的大小
	 * @return 特权定金销商商订单状态列表
	 */
	@RequestMapping(value="/getOnePage4Distribute",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4Distribute(@RequestBody AppPrepayForm form){
    	LOG.info("{method:'PrepayController::getOnePage4Distribute',in:"+JSON.toJSONString(form)+"}");
		Response response = new Response();
		if (StringUtils.isEmpty(form.distributorId) || StringUtils.isEmpty(form.status)) {
    		response.resultCode = ErrorCode.INVALID_PARAM;
    		response.message="参数错误";
    	}else {
    		try {
				response.data = prepayService.getOnePage4Distribute(form.distributorId, form.status,form.search, form.startNum, form.pageSize,form.statusSearch);
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
				LOG.error(e.getMessage(),e);
			}
    	}
		LOG.info("{method:'PrepayController:getOnePage4Distribute',out:"+JSON.toJSONString(response)+"}");
		return response;
    }
    
    /**
     * 特权定金经销商订单状态条数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 特权定金经销商订单状态条数
     */
    @RequestMapping(value="/get2DistributeCount",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get2DistributeCount(@RequestBody AppPrepayForm form){
    	Response response=new Response();
    	if (StringUtils.isEmpty(form.distributorId)||StringUtils.isEmpty(form.status)) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
    	else {
    		try {
				response.data=prepayService.get2DistributeCount(form.distributorId, form.status);
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="未知错误";
			}
    	}
    	return response;
    }
    
    /**
	 * 特权定金门店订单状态列表
	 * @param storeId 门店Id
	 * @param status 状态
	 * @param startNum 上一次加载的最后项
	 * @param pageSize 页的大小
	 * @return 特权定金门店订单状态列表
	 */
    @RequestMapping(value="/getOnePage4StoreId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4StoreId(@RequestBody AppPrepayForm form){
    	LOG.info("{method:'PrepayController::getOnePage4StoreId',in:"+JSON.toJSONString(form)+"}");
		Response response = new Response();
		if (StringUtils.isEmpty(form.storeId) ) {
    		response.resultCode = ErrorCode.INVALID_PARAM;
    		response.message="参数错误";
    	}else {
    		try {
				response.data = prepayService.getOnePage4StoreId(form.storeId, form.status,form.search, form.startNum, form.pageSize);
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
				LOG.error(e.getMessage(),e);
			}
    	}
		LOG.info("{method:'PrepayController:getOnePage4StoreId',out:"+JSON.toJSONString(response)+"}");
		return response;
    }
    
    /**
     * 特权定金门店订单状态条数
     * @param storeId 门店ID
     * @param status 状态
     * @return 特权定金门店订单状态条数
     */
    @RequestMapping(value="/get2StoreCount",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get2StoreCount(@RequestBody AppPrepayForm form){
    	Response response=new Response();
    	if (StringUtils.isEmpty(form.storeId)||StringUtils.isEmpty(form.status)) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
    	else {
    		try {
				response.data=prepayService.get2StoreCount(form.storeId, form.status);
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="未知错误";
			}
    	}
    	return response;
    }
    
    /**
	 * 特权定金导购员状态列表
	 * @param acceptorId 导购员Id
	 * @param status 状态
	 * @param startNum 上一次加载的最后项
	 * @param pageSize 页的大小
	 * @return 特权定金导购员状态列表
	 */
    @RequestMapping(value="/getOnePage4AcceptorId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4AcceptorId(@RequestBody AppPrepayForm form){
    	LOG.info("{method:'PrepayController::getOnePage4AcceptorId',in:" + JSON.toJSONString(form) + "}");
     	
     	Response res = new Response();
         try{
         	res.data = prepayService.getOnePage4AcceptorId(form.acceptorId, form.status,form.search, form.startNum, form.pageSize,form.statusSearch);
         } catch(Throwable e){
        	 e.printStackTrace();
         	res.resultCode = ErrorCode.INTERNAL_ERR;
         	LOG.error(e.getMessage(),e);
     	}
         
 		LOG.info("{method:'PrepayController::getOnePage4AcceptorId',out:{res'" + JSON.toJSONString(res) + "'}}");
     	return res;
    }
    
    /**
     * 特权定金导购员状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 特权定金导购员状态条数
     */
    @RequestMapping(value="/get2AcceptorCount",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get2AcceptorCount(@RequestBody AppPrepayForm form){
        LOG.info("{method:'PrepayController::get2AcceptorCount',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
        try{
        	res.data = prepayService.get2AcceptorCount(form.acceptorId, form.status);
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'PrepayController::get2AcceptorCount',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    
    /**
     * 特权定金获取单据明细
     * @param consignmentId 发货单ID
     * @return 特权定金获取单据明细
     */
    @RequestMapping(value="/get4ConsignmentId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get4ConsignmentId(@RequestBody AppPrepayForm form){
    	LOG.info("{method:'PrepayController::get4ConsignmentId',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
        try{
        	res.data = prepayService.get4ConsignmentId(form.consignmentId);
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'PrepayController::get4ConsignmentId',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    
//    /**
//     * 特权定金经销商接单/下派
//     * @param distributorId 经销商ID
//     * @param orderCode 发货单id
//     * @param storeId 店铺ID
//     * @param remark 备注
//     * @return 成功返回200
//     */
//    @RequestMapping(value="/distribute",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
//    @ResponseBody
//    public Response distribute(@RequestBody List<AppOrderForm> formList){
//
//    	Response response = new Response();
//    	StringBuffer buffer = new StringBuffer();
//    	int successCount = 0;//记录"下派成功"的数量
//    	int errorCount = 0;//记录"下派失败"的数量
//    	//遍历formList
//    	for(int i=0;i<formList.size();i++) {//遍历formList
//    		AppOrderForm form = new AppOrderForm();
//    		form = formList.get(i);
//    		if (StringUtils.isEmpty(form.distributorId) || StringUtils.isEmpty(form.orderCode) ) {//参数为空
//    			int index = i+1;//记录第几条数据"参数错误"
//    			if (buffer.length() == 0) {
//					buffer.append(index+"参数错误");
//				}else {
//					buffer.append(","+index+"参数错误");
//				}
//				errorCount ++;
//        	}else {//参数不为空
//    			try {//成功
//    				int success = prepayService.distribute(form.distributorId, form.orderCode, form.storeId, form.remark);//调用下派方法
//    				successCount += success;
//    			} catch (Exception e) {//失败
//    				if (buffer.length() == 0) {
//    					buffer.append(form.orderCode);
//    				}else {
//    					buffer.append(","+form.orderCode);
//    				}
//    				errorCount ++;
//    			}
//    		}
//    	}
//    	//输出结果Response
//    	if (errorCount == 0) {
//    		response.resultCode = ErrorCode.SUCCESS;
//    		response.message = "下派完成";
//    	}else {
//    		response.resultCode = ErrorCode.INTERNAL_ERR;
//    		response.message = buffer.toString();
//    	}
//    	response.data = successCount;//成功的数量
//
//    	return response;
//
//    }
//
    /**
     * 特权定金编辑订单
     * @param orderCode 发货单id
     * @param status 状态
     * @param remark 备注
     * @return 成功返回200
     */
    @RequestMapping(value="/updateOrder",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response updateOrder(@RequestBody AppPrepayForm form){
    	Response response = new Response();
    	String status[]= {"DISTRIBUTED","ALLOCATED","BOOKED","SHIPPED","DPRECEIVE","STOREWAIT","COMPLETED","ACCEPT","TOSTORE","NOTTRADED"};
    	if (!ArrayUtils.contains(status, form.status)||StringUtils.isEmpty(form.orderCode)) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
    	else {
    		response.resultCode=ErrorCode.SUCCESS;
    		response.data=prepayService.updateOrder(form.orderCode, form.status, form.remark);
    	}
    	return response;
    }
    
    
    /**
     * 特权定金被动查询订单
     * @param phone 电话号码
     * @param storeId 门店ID
     * @param acceptorId 导购ID
     * @return 特权定金被动查询订单
     */
    @RequestMapping(value="/get4Search",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get4Search(@RequestBody AppPrepayForm form){
        LOG.info("{method:'PrepayController::get4Search',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
        try{
        	res.data = prepayService.get4Search(form.phone);
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'PrepayController::get4Search',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    /**
     * 特权定金获取拒单原因
     * @param orderStyle 订单类型
     * @return 特权定金获取拒单原因
     */
    @RequestMapping(value="/getReason4Order",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getReason4Order(@RequestBody AppPrepayForm form){
    	return null;
    }
    
    /**
     * 核销码获取单据明细
     * @param priDepositCode 核销码
     * @return 特权定金获取单据明细
     */
    @RequestMapping(value="/get4priDepositCode",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get4priDepositCode(@RequestBody AppPrepayForm form){
    	LOG.info("{method:'PrepayController::get4priDepositCode',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
    	if (StringUtils.isEmpty(form.priDepositCode)) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
			return res;
		}
        try{
        	res.data = prepayService.get4priDepositCode(form.priDepositCode);
        	res.resultCode=ErrorCode.SUCCESS;
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'PrepayController::get4priDepositCode',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    

}
