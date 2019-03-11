package com.dpmall.web.controller;

import java.util.List;

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
	 * 经销商获取待分配的实物订单
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param startNum 上一次加载的最后项ID
	 * @param pageSize 页的大小
	 * @return 经销商获取待分配的实物订单列表
	 */
    @RequestMapping(value="/getOnePage4Distribute",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4Distribute(@RequestBody AppOrderForm form){
    	LOG.info("{method:'OrderController::getOnePage4Distribute',in:" + JSON.toJSONString(form) + "}");
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
    	LOG.info("{method:'OrderController::getOnePage4Distribute',out:{res:'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    /**
     * 获取经销商待分配的实物订单数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
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
     * 经销商下派到店铺
     * @param distributorId 经销商ID
     * @param orderCode 订单编码
     * @param storeId 店铺ID
     * @param acceptComment 备注
     * @return 成功返回200
     */
    @RequestMapping(value="/distribute",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response distribute(@RequestBody List<AppOrderForm> formList){
    	
    	Response response = new Response();
    	StringBuffer buffer = new StringBuffer();  
    	int successCount = 0;//记录"下派成功"的数量
    	int errorCount = 0;//记录"下派失败"的数量
    	//遍历formList
    	for(int i=0;i<formList.size();i++) {//遍历formList
    		AppOrderForm form = new AppOrderForm();
    		form = formList.get(i);
    		if (StringUtils.isEmpty(form.distributorId) || StringUtils.isEmpty(form.orderCode) ) {//参数为空
    			int index = i+1;//记录第几条数据"参数错误"
    			if (buffer.length() == 0) {
					buffer.append(index+"参数错误");
				}else {
					buffer.append(","+index+"参数错误");
				}
				errorCount ++;
        	}else {//参数不为空
    			try {//成功
    				int success = orderService.distribute(form.distributorId, form.orderCode, form.storeId, form.remark);//调用下派方法
    				successCount += success;
    			} catch (Exception e) {//失败
    				if (buffer.length() == 0) {
    					buffer.append(form.orderCode);
    				}else {
    					buffer.append(","+form.orderCode);
    				}
    				errorCount ++;
    			}
    		}
    	}
    	//输出结果Response
    	if (errorCount == 0) {
    		response.resultCode = ErrorCode.SUCCESS;
    		response.message = "下派完成";
    	}else {
    		response.resultCode = ErrorCode.INTERNAL_ERR;
    		response.message = buffer.toString();
    	}
    	response.data = successCount;//成功的数量
    	
    	return response;
    }
    
    
//    /**
//     * 经销商拒单
//     * @param distributorId 经销商ID
//     * @param orderCode 销售线索ID
//     * @param rejectType 拒单类型
//     * @param rejectRemark 拒单备注
//     * @return 成功返回200
//     */
//    @RequestMapping(value="/reject",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
//    @ResponseBody
//    public Response reject(@RequestBody AppOrderForm form){
//    	return null;
//    }
    
    
//    /**
//     * 经销商获取待跟进的一页实物订单数据
//	 * @param distributorId 经销商Id
//	 * @param startNum 上一次加载的最后项ID
//	 * @param pageSize 页的大小
//     * @return 经销商待跟进的一页实物订单数据
//     */
//    @RequestMapping(value="/getOnePage4Followup",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
//    @ResponseBody
//    public Response getOnePage4Followup(@RequestBody AppOrderForm form){
//    	LOG.info("{method:'OrderController::getOnePage4Followup',in:" + JSON.toJSONString(form) + "}");
//
//    	Response res = new Response();
//        try{
//        	res.data = orderService.getOnePage4Followup(form.distributorId, form.offset, form.pageSize);
//        } catch(Throwable e){
//        	res.resultCode = ErrorCode.INTERNAL_ERR;
//        	LOG.error(e.getMessage(),e);
//    	}
//        
//    	LOG.info("{method:'OrderController::getOnePage4Followup',out:{res:'" + JSON.toJSONString(res) + "'}}");
//    	return res;
//    }
    
//    /**
//     * 根据条件查询已发货的实物订单
//     * @param distributorId 经销商Id
//     * @param distributeTime 订单下派时间
//     * @param storeId
//     * @param orderCode
//     * @param clientName
//     * @param clientTel
//     * @param startNum
//     * @param pageSize
//     * @return 根据条件查询已发货的实物订单
//     */
//    @RequestMapping(value="/getOnePageClosedOrder",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
//    @ResponseBody
//    public List<OrderModel> getOnePageClosedOrder(@RequestBody AppOrderForm form){
//    	return null;
//    }

    
	/**
	 * 店铺获取待接单的实物订单
	 * @param storeId 店铺ID
	 * @param status 状态
	 * @acceptorId 接单员ID
	 * @param startNum 上一次加载的最后项ID
	 * @param pageSize 页的大小
	 * @return 店铺获取待接单的实物订单列表
	 */
    @RequestMapping(value="/getOnePage4StoreId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response getOnePage4StoreId(@RequestBody AppOrderForm form){
    	LOG.info("{method:'OrderController::getOnePage4StoreId',in:" + JSON.toJSONString(form) + "}");
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
    	LOG.info("{method:'OrderController::getOnePage4StoreId',out:{res:'" + JSON.toJSONString(res) + "'}}");
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
		LOG.info("{method:'OrderController::get2AcceptCount',in:" + JSON.toJSONString(form) + "}");
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
		LOG.info("{method:'OrderController::get2AcceptCount',out:{res:'" + JSON.toJSONString(res) + "'}}");
		return res;
	}
    
  
    
    
    /**
     * 导购员接单
     * @param acceptorId 导购员ID
     * @param orderCode 订单编码
     * @return 成功返回200
     */
    @RequestMapping(value="/accept",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response accept(@RequestBody AppOrderForm form){
        LOG.info("{method:'OrderController::accept',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
        try{
        	res.data = orderService.accept(form.acceptorId, form.orderCode, form.acceptComment);
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'OrderController::accept',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }
    
    
//    /**
//     * 确认发货
//     * @param model
//     * @return 成功返回200
//     */
//    @RequestMapping(value="/deliver",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
//    @ResponseBody
//    public Response deliver(@RequestBody AppOrderForm form){
//    	LOG.info("{method:'OrderController::deliver',in:"+JSON.toJSONString(form)+"}");
//    	Response res = new Response();
//    	if(form.orderCode == null) {//orderCode为空返回错误码500
//    		res.resultCode = ErrorCode.INVALID_PARAM;
//    		return res;
//    	}
//    	try {
//    		res.data = orderService.deliver(form.orderCode == null? null : String.valueOf(form.orderCode));
//		} catch (Exception e) {
//			LOG.error(e.getMessage(),e);
//		}
//    	LOG.info("{method:'OrderController::deliver',in:"+JSON.toJSONString(res)+"}");
//    	return res;
//    }
 

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
     * 实物类获取单据明细
     * @param consignmentId 发货单ID
     * @return 实物类获取单据明细
     */
    @RequestMapping(value="/getOrderDetails",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
	public Response getOrderDetails(@RequestBody AppOrderForm form) {
        LOG.info("{method:'OrderController::getOrderDetails',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
        try{
        	res.data = orderService.getOrderDetails(form.consignmentId);
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'OrderController::getOrderDetails',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
	}
    
    /**
     * 实物类退货单明细
     * @param consignmentId 发货单ID
     * @return 实物类退货单明细
     */
    @RequestMapping(value="/getReturnRequestDetails",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
	public Response getReturnRequestDetails(@RequestBody AppOrderForm form) {
    	LOG.info("{method:'OrderController::getOrderDetails',in:" + JSON.toJSONString(form) + "}");
    	
    	Response res = new Response();
        try{
        	res.data = orderService.getOrderDetails(form.consignmentId);
        } catch(Throwable e){
        	res.resultCode = ErrorCode.INTERNAL_ERR;
        	LOG.error(e.getMessage(),e);
    	}
        
		LOG.info("{method:'OrderController::getReturnRequestDetails',out:{res'" + JSON.toJSONString(res) + "'}}");
    	return res;
	}
   
    
    
    
    
    

}
