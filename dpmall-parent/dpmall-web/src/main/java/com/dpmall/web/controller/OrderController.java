package com.dpmall.web.controller;

import java.util.List;

import com.dpmall.common.LogUtil;
import com.dpmall.model.in.OrderDistributeModelIn;
import com.dpmall.model.in.OrderEditStatusModelIn;

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
import com.dpmall.datasvr.api.IOrderService;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.AppOrderForm;
import com.dpmall.web.controller.form.Response;

/**
 * 实物订单服务接口
 * @author river
 * @since 2017-07-10
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private IOrderService orderService;



	/**
	 * 经销商接单
	 */
	@RequestMapping(value="/distribute",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response distribute(@RequestBody List<OrderDistributeModelIn> formList){
		LogUtil.controllerLogInfo("{method:'OrderController::distribute',in:" + JSON.toJSONString(formList) + "}");
		Response response = new Response();
		//遍历formList
		try {
			int i = 0;
			for ( i=0 ; i < formList.size(); i++) {//遍历formList
				OrderDistributeModelIn form = formList.get(i);
				if (StringUtils.isEmpty(form.getConsignmentId()) || StringUtils.isEmpty(form.getStoreId())) {//参数为空
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误";
					return response;
				}
				orderService.acceptOfAgency(form);//调用下派方法
			}
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "成功";
			response.data = i;

		} catch (Exception e) {
			LogUtil.logError(e.getMessage(), e);
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		}
		LogUtil.controllerLogInfo("{method:'OrderController::reject',out:" + JSON.toJSONString(response) + "}");
		return response;
	}


	/**
	 * 经销商拒单
	 */
	@RequestMapping(value="/reject",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response reject(@RequestBody List<OrderDistributeModelIn> formList ){
		LogUtil.controllerLogInfo("{method:'OrderController::reject',in:" + JSON.toJSONString(formList) + "}");
		Response response = new Response();
		//遍历formList
		try {
			int i = 0;
			for ( i=0 ; i < formList.size(); i++) {//遍历formList
				OrderDistributeModelIn form = formList.get(i);
				if (StringUtils.isEmpty(form.getConsignmentId())) {//参数为空
					response.resultCode = ErrorCode.INVALID_PARAM;
					response.message = "参数错误";
					return response;
				}
				orderService.rejectOfAgency(form);//调用下派方法
				i++;
			}
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "成功";
			response.data = i;

		} catch (Exception e) {
			LogUtil.logError(e.getMessage(), e);
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "程序错误";
		}
		LogUtil.controllerLogInfo("{method:'OrderController::reject',out:" + JSON.toJSONString(response) + "}");
		return response;
	}


	/**
	 * 经销商获取待分配的实物订单
	 */
    @RequestMapping(value="/getOnePage4Distribute",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4Distribute(@RequestBody AppOrderForm form){
		LogUtil.controllerLogInfo("{method:'OrderController::getOnePage4Distribute',in:" + JSON.toJSONString(form) + "}");
    	Response res = new Response();
    	if (StringUtils.isEmpty(form.distributorId)||StringUtils.isEmpty(form.status)||form.startNum==null||form.pageSize==null) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
		}
    	else {
    		try{
            	res.resultCode=ErrorCode.SUCCESS;
            	res.data = orderService.getOnePage4Distribute(form.distributorId, form.status,form.search,form.startNum, form.pageSize,form.statusSearch);
            } catch(Throwable e){
            	res.resultCode = ErrorCode.INTERNAL_ERR;
            	res.message="系统错误";
            	LOG.error(e.getMessage(),e);
        	}  	
    	}
		LogUtil.controllerLogInfo("{method:'OrderController::getOnePage4Distribute',out:{res:'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    /**
     * 获取经销商待分配的实物订单数
     */
    @RequestMapping(value="/get2DistributeCount",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response get2DistributeCount(@RequestBody AppOrderForm form){
    	LOG.info("{method:'OrderController::get2DistributeCount',in:" + JSON.toJSONString(form) + "}");
    	Response response=new Response();
    	if (StringUtils.isEmpty(form.distributorId)||StringUtils.isEmpty(form.status)) {
			response.resultCode=ErrorCode.INVALID_PARAM;
			response.message="参数错误";
		}
    	else {
			try {
				response.data = orderService.get2DistributeCount(form.distributorId,form.status);
				response.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode=ErrorCode.INTERNAL_ERR;
				response.message="未知错误";
			}
		}
    	LOG.info("{method:'OrderController::get2DistributeCount',out:{res:'" + JSON.toJSONString(response) + "'}}");
    	return response;
    }



	/**
	 * 门店接单
	 */
	@RequestMapping(value="/accept",method = {RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response accept(@RequestBody OrderDistributeModelIn form){
		LogUtil.controllerLogInfo("{method:'OrderController::accept',in:" + JSON.toJSONString(form) + "}");
		Response response = new Response();
		//遍历formList
		try {
			if (StringUtils.isEmpty(form.getConsignmentId())) {//参数为空
				response.resultCode = ErrorCode.INVALID_PARAM;
				response.message = "参数错误";
				return response;
			}
			orderService.acceptOfStore(form);//接单
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "成功";

		} catch (Exception e) {
			LogUtil.logError(e.getMessage(), e);
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "程序错误";
		}
		LogUtil.controllerLogInfo("{method:'OrderController::accept',out:" + JSON.toJSONString(response) + "}");
		return response;
	}



    
	/**
	 */
    @RequestMapping(value="/getOnePage4StoreId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4StoreId(@RequestBody AppOrderForm form){
		LogUtil.controllerLogInfo("{method:'OrderController::getOnePage4StoreId',in:" + JSON.toJSONString(form) + "}");
    	Response res = new Response();
    	if (StringUtils.isEmpty(form.storeId)||StringUtils.isEmpty(form.status)||form.startNum==null||form.pageSize==null) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
		}
    	else {
    		try{
            	res.resultCode=ErrorCode.SUCCESS;
            	res.data = orderService.getOnePage4StoreId(form.storeId, form.status,form.acceptorId,form.search,form.startNum, form.pageSize,form.statusSearch);
            } catch(Throwable e){
            	res.resultCode = ErrorCode.INTERNAL_ERR;
            	res.message="系统错误";
            	LOG.error(e.getMessage(),e);
        	}  	
    	}
		LogUtil.controllerLogInfo("{method:'OrderController::getOnePage4StoreId',out:{res:'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    /**
     * 获取店铺待接单的实物订单数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
    @RequestMapping(value="/get2StoreCount",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
	public Response get2StoreCount(@RequestBody AppOrderForm form) {
		LogUtil.controllerLogInfo("{method:'OrderController::get2AcceptCount',in:" + JSON.toJSONString(form) + "}");
		Response res = new Response();
		if (form.storeId == null) {
			res.resultCode = ErrorCode.INVALID_PARAM;
			return res;
		}
		try {
			res.data = orderService.get2StoreCount(form.storeId, form.acceptorId,form.status);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		}
		LogUtil.controllerLogInfo("{method:'OrderController::get2AcceptCount',out:{res:'" + JSON.toJSONString(res) + "'}}");
		return res;
	}

	/**
	 * 实物类获取单据明细
	 */
	@RequestMapping(value="/getOrderDetails",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response getOrderDetails(@RequestBody AppOrderForm form) {
		LogUtil.controllerLogInfo("{method:'OrderController::getOrderDetails',in:" + JSON.toJSONString(form) + "}");

		Response res = new Response();
		try{
			res.data = orderService.getOrderDetails(form.consignmentId);
		} catch(Throwable e){
			res.resultCode = ErrorCode.INTERNAL_ERR;
			LOG.error(e.getMessage(),e);
		}

		LogUtil.controllerLogInfo("{method:'OrderController::getOrderDetails',out:{res'" + JSON.toJSONString(res) + "'}}");
		return res;
	}


	/**
	 * 实物类退货单明细
	 */
	@RequestMapping(value="/getReturnRequestDetails",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response getReturnRequestDetails(@RequestBody AppOrderForm form) {
		LogUtil.controllerLogInfo("{method:'OrderController::getReturnRequestDetails',in:" + JSON.toJSONString(form) + "}");

		Response res = new Response();
		try{
			res.data = orderService.getOrderDetails(form.consignmentId);
		} catch(Throwable e){
			res.resultCode = ErrorCode.INTERNAL_ERR;
			LOG.error(e.getMessage(),e);
		}

		LogUtil.controllerLogInfo("{method:'OrderController::getReturnRequestDetails',out:{res'" + JSON.toJSONString(res) + "'}}");
		return res;
	}



    /**
     * 确认发货
     * @param model
     * @return 成功返回200
     */
    @RequestMapping(value="/deliver",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response deliver(@RequestBody AppOrderForm form){
    	LOG.info("{method:'OrderController::deliver',in:"+JSON.toJSONString(form)+"}");
    	Response res = new Response();
    	if(form.orderCode == null) {//orderCode为空返回错误码500
    		res.resultCode = ErrorCode.INVALID_PARAM;
    		return res;
    	}
    	try {
    		res.data = orderService.deliver(form.orderCode == null? null : String.valueOf(form.orderCode));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
    	LOG.info("{method:'OrderController::deliver',in:"+JSON.toJSONString(res)+"}");
    	return res;
    }
 

    /**
	 * 实物类导购员订单状态列表
	 * @param acceptorId 导购员ID
	 * @param status 状态
	 * @param startNum 上一次加载的最后项ID
	 * @param pageSize 页的大小
	 * @return 实物类导购员订单状态列表
	 */
    @RequestMapping(value="/getOnePage4AcceptorId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4AcceptorId(@RequestBody AppOrderForm form){
    	 LOG.info("{method:'OrderController::getOnePage4AcceptorId',in:" + JSON.toJSONString(form) + "}");
     	
     	Response res = new Response();
         try{
         	res.data = orderService.getOnePage4AcceptorId(form.acceptorId, form.status, form.startNum, form.pageSize);
         } catch(Throwable e){
         	res.resultCode = ErrorCode.INTERNAL_ERR;
         	LOG.error(e.getMessage(),e);
     	}
         
 		LOG.info("{method:'OrderController::getOnePage4AcceptorId',out:{res'" + JSON.toJSONString(res) + "'}}");
     	return res;
    }
    
    /**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 实物类导购员订单状态条数
     */
    @RequestMapping(value="/get2AcceptorCount",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
	public Response get2AcceptorCount(@RequestBody AppOrderForm form) {
		LOG.info("{method:'OrderController::get2AcceptorCount',in:"+JSON.toJSONString(form)+"}");
		Response response = new Response();
		if (StringUtils.isEmpty(form.acceptorId) ) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		}else {
			try {
				response.data = orderService.get2AcceptorCount(form.acceptorId, form.status);
			}catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
				LOG.error(e.getMessage(),e);
			}
		}
		LOG.info("{method:'OrderController::get2AcceptorCount',out:"+JSON.toJSONString(response)+"}");
		return response;
		
	}



	/**
	 * 实物类导购员订单状态条数

	 */
	@RequestMapping(value="/editStatus",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response editStatus(@RequestBody OrderEditStatusModelIn form) {
		LogUtil.controllerLogInfo("{method:'OrderController::editStatus',in:"+JSON.toJSONString(form)+"}");
		Response response = new Response();

		if (StringUtils.isEmpty(form.getConsignmentCode()) && StringUtils.isEmpty(form.getStatus())) {// 发货单号不为空
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		}else {
			try {
				int result = orderService.editStatus(form);
				response.resultCode = ErrorCode.SUCCESS;
				response.message = "更新成功";
				response.data = result;
				if (result==0){
					response.resultCode = ErrorCode.SUCCESS;
					response.message = "订单不存在";
				}
			}catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
				LOG.error(e.getMessage(),e);
			}
		}
		LogUtil.controllerLogInfo("{method:'OrderController::editStatus',out:"+JSON.toJSONString(response)+"}");
		return response;

	}
    


   
    
    
    
    
    

}
