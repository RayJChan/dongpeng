package com.dpmall.web.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.DateUtils;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.LogUtil;
import com.dpmall.datasvr.api.ISaleLeadsOrderService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.SalOrderItem4OmsModel;
import com.dpmall.model.SaleLeadsOrderModel;
import com.dpmall.model.SalesLeadsOperationModel;
import com.dpmall.model.in.SalOrderInfoModelIn;
import com.dpmall.web.controller.form.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 销售线索
 *
 * @author cwj
 * @date 2018-5-7
 */
@Controller
@RequestMapping("/saleLeadsOrder")
public class SaleLeadsOrderController {

    @Autowired
    private ISaleLeadsOrderService saleLeadsOrderService;

    @Autowired
    private HybrisUtils hybrisUtils;


    /**
     * 获取列表--经销商
     * agencyId 经销商Id
     * pageNum 页数
     * pageSize 页的大小
     */
    @RequestMapping(value = "/getList4Agency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getList4Agency(@RequestBody DistributeForm form) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getList4Agency',in:" + JSON.toJSONString(form) + "}");
        Response res = new Response();
        try {
            if (StringUtils.isEmpty(form.agencyId) || StringUtils.isEmpty(form.listStatus) || form.pageNum == null || form.pageSize == null) {
                res.resultCode = ErrorCode.INVALID_PARAM;
                res.message = "参数错误";
                return res;
            }
            res.data = saleLeadsOrderService.getList4Agency(form.agencyId, form.listStatus, form.pageNum, form.pageSize, form.search,form.statusSearch);
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }

        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getList4Agency',in:" + JSON.toJSONString(res) + "}");
        return res;
    }


    /**
     * 获取列表--门店
     * storeId 经销商Id
     * pageNum 页数
     * pageSize 页的大小
     */
    @RequestMapping(value = "/getList4Store", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getList4Store(@RequestBody DistributeForm form) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getList4Store',in:" + JSON.toJSONString(form) + "}");
        Response res = new Response();
        try {
            if (StringUtils.isEmpty(form.storeId) || StringUtils.isEmpty(form.listStatus) || form.pageNum == null || form.pageSize == null) {
                res.resultCode = ErrorCode.INVALID_PARAM;
                res.message = "参数错误";
                return res;
            }
            res.data = saleLeadsOrderService.getList4Store(form.storeId, form.listStatus, form.pageNum, form.pageSize, form.search,form.statusSearch);
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }

        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getList4Store',in:" + JSON.toJSONString(res) + "}");
        return res;
    }


    /**
     * 获取列表数量-经销商
     */
    @RequestMapping(value = "/getListConut4Agency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListConut4Agency(@RequestBody DistributeForm form) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getListConut4Agency',in:" + JSON.toJSONString(form) + "}");
        Response res = new Response();
        try {
            if (StringUtils.isEmpty(form.agencyId)  ) {
                res.resultCode = ErrorCode.INVALID_PARAM;
                res.message = "参数错误";
                return res;
            }
            res.data = saleLeadsOrderService.getListConut4Agency(form.agencyId);
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.data = "0";
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }

        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getListConut4Agency',in:" + JSON.toJSONString(res) + "}");
        return res;
    }


    /**
     * 获取列表数量-门店
     */
    @RequestMapping(value = "/getListConut4Store", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListConut4Store(@RequestBody DistributeForm form) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getListConut4Store',in:" + JSON.toJSONString(form) + "}");
        Response res = new Response();
        try {
            if (StringUtils.isEmpty(form.storeId) ) {
                res.resultCode = ErrorCode.INVALID_PARAM;
                res.message = "参数错误";
                return res;
            }
            res.data = saleLeadsOrderService.getListConut4Store(form.storeId);
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.data = "0";
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }

        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getListConut4Store',in:" + JSON.toJSONString(res) + "}");
        return res;
    }

    /**
     获取详情
     * @return
     */
    @RequestMapping(value="/getDetails",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response getDetails(@RequestBody Map<String ,String> param){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getDetails',in:" + JSON.toJSONString(param) + "}");

        Response res = new Response();
        if (StringUtils.isEmpty(param.get("saleLeadsOrderId"))||StringUtils.isEmpty(param.get("operatorById"))) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            res.data = saleLeadsOrderService.getDetails(param.get("saleLeadsOrderId"),param.get("operatorById"));
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getDetails',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }



    /**
     * 拒单
     */
    @RequestMapping(value = "/reject", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response reject(@RequestBody RejectBatchForm form) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::reject',in:" + JSON.toJSONString(form) + "}");
        Response res = new Response();
        try {
            if (StringUtils.isEmpty(form.rejectType)  || CollectionUtils.isEmpty(form.saleLeadsOrderIdList)) {
                res.resultCode = ErrorCode.INVALID_PARAM;
                res.message = "参数错误";
                return res;
            }

            int i = saleLeadsOrderService.reject(form.saleLeadsOrderIdList, form.rejectType, form.rejectRemark, form.operatorBy);

            //刷新hybris缓存
            for (String saleLeadsOrderId : form.saleLeadsOrderIdList) {
                // 调用hybris方法
                for (int j = 0;j<5;j++){
                    LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::reject---HybrisCount',result:{"+j+"}");
                    String result = refreshStatus(saleLeadsOrderId);
                    if ("200".equals(result)){
                        break;
                    }

                }

            }
            res.data = i;
            res.resultCode = ErrorCode.SUCCESS;

        } catch (Exception e) {
            res.data = 0;
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::reject',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


     /**
      *  经销商批量接单
     */
    @RequestMapping(value="/agencyAccept",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response agencyAccept(@RequestBody DistributeForm form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::agencyAccept',in:" + JSON.toJSONString(form) + "}");

    	Response res = new Response();
    	if ((form.saleLeadsOrderIds == null ||StringUtils.isEmpty(form.operatorBy)||StringUtils.isEmpty(form.storeId))) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
			return res;
		}
		try{
            int i =saleLeadsOrderService.agencyAccept(form.saleLeadsOrderIds,form.storeId,form.agencyRemark,form.operatorBy);
            // 刷新hybris状态
            for (String salId :form.saleLeadsOrderIds) {
                if(StringUtils.isEmpty(salId)){
                    continue;
                }
                for (int j = 0;j<5;j++){
                    LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::agencyAccept---HybrisCount',result:{"+j+"}");
                    String result = refreshStatus(salId);
                    if ("200".equals(result)){
                        break;
                    }

                }
        	}
            res.data = i;
            res.resultCode=ErrorCode.SUCCESS;

        } catch(Exception e){
            res.data = 0;
    	    res.resultCode=ErrorCode.INTERNAL_ERR;
        	res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
    	}
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::agencyAccept',out:{res:'" + JSON.toJSONString(res) + "'}}");
    	return res;
    }


    /**
     *  门店批量接单
     * @return
     */
    @RequestMapping(value="/storeAccept",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response storeAccept(@RequestBody AcceptBatchForm form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::storeAccept',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (CollectionUtils.isEmpty(form.saleLeadsOrderIds) ||StringUtils.isEmpty(form.operatorBy)) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            int i =saleLeadsOrderService.storeAccept(form.saleLeadsOrderIds,form.storeRemark,form.operatorBy);
            // 刷新hybris状态
            for(String salId :form.saleLeadsOrderIds) {
                for (int j = 0;j<5;j++){
                    LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::storeAccept---HybrisCount',result:{"+j+"}");
                    String result = refreshStatus(salId);
                    if ("200".equals(result)){
                        break;
                    }

                }
            }
            res.data = i;
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::storeAccept',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }




    /**
     添加备注
     * @return
     */
    @RequestMapping(value="/addRemarks",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response addRemarks(@RequestBody AddRemarksForm form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::addRemarks',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (StringUtils.isEmpty(form.operatorBy)||StringUtils.isEmpty(form.saleLeadsOrderId)) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            res.data = saleLeadsOrderService.addRemarks(form.saleLeadsOrderId,form.agencyRemark,form.storeRemark,form.operatorBy);
            for (int j = 0;j<5;j++){
                LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::addRemarks---HybrisCount',result:{"+j+"}");
                String result = refreshStatus(form.saleLeadsOrderId);
                if ("200".equals(result)){
                    break;
                }

            }
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::addRemarks',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     * 更新客户信息
     */
    @RequestMapping(value="/updateCustomerInfo",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response updateCustomerInfo(@RequestBody CustomerInfoForm form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateCustomerInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.getId() == null||StringUtils.isEmpty(form.operatorBy)||StringUtils.isEmpty(form.getClientName())||StringUtils.isEmpty(form.getClientTel())) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            SaleLeadsOrderModel model = new SaleLeadsOrderModel();
            BeanUtils.copyProperties(form,model);
            int i = saleLeadsOrderService.updateCustomerInfo(model,form.operatorBy);
            res.data = i;
            for (int j = 0;j<5;j++){
                LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateCustomerInfo---HybrisCount',result:{"+j+"}");
                String result = refreshStatus(form.getId().toString());
                if ("200".equals(result)){
                    break;
                }

            }
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateCustomerInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }

    /**
     * 更新订单进度
     */
    @RequestMapping(value="/updateOrderProgress",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response updateOrderProgress(@RequestBody SaleLeadOrderForm form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateOrderProgress',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.saleLeadsOrderId==null||StringUtils.isEmpty(form.statusName)||StringUtils.isEmpty(form.operatorBy)) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            int i = saleLeadsOrderService.updateOrderProgress(form.statusName,form.saleLeadsOrderId,form.remark,form.operatorBy,form.failTypeId);
            if (i==0) {
                res.data=i;
                res.resultCode=ErrorCode.INTERNAL_ERR;
                res.message="更新失败，请重新点击";
            }else{
                res.data = i;
                for (int j = 0;j<5;j++){
                    LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateCustomerInfo---HybrisCount',result:{"+j+"}");
                    String result = refreshStatus(form.saleLeadsOrderId.toString());
                    if ("200".equals(result)){
                        break;
                    }

                }
                res.resultCode=ErrorCode.SUCCESS;
            }

        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateOrderProgress',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }

    /**
     *  填写订单信息(提交)
     */
    @RequestMapping(value="/updateSalOrderInfo",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response updateSalOrderInfo(@RequestBody SalOrderInfoModelIn form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateSalOrderInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.getSaleLeadsOrderId()==null||form.getPictureNames()==null||StringUtils.isEmpty(form.getOperatorBy())) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            int i = saleLeadsOrderService.updateSalOrderInfo(form);
            res.data = i;
            for (int j = 0;j<5;j++){
                LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateSalOrderInfo---HybrisCount',result:{"+j+"}");
                String result = refreshStatus(form.getSaleLeadsOrderId().toString());
                if ("200".equals(result)){
                    break;
                }

            }
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::updateSalOrderInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     * 填写订单信息(只保存不提交)
     */
    @RequestMapping(value="/saveSalOrderInfo",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response saveSalOrderInfo(@RequestBody SalOrderInfoModelIn form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::saveSalOrderInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.getSaleLeadsOrderId()==null||form.getPictureNames()==null||StringUtils.isEmpty(form.getOperatorBy())) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            int i = saleLeadsOrderService.saveSalOrderInfo(form);
            res.data = i;
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::saveSalOrderInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     * 查看更新状态时的备注
     */
    @RequestMapping(value="/getUpdateStatusRemark",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response getUpdateStatusRemark(@RequestBody Map<String,String> form){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getUpdateStatusRemark',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (StringUtils.isEmpty(form.get("operationId"))) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            res.data = saleLeadsOrderService.getUpdateStatusRemark(form.get("operationId"));
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getUpdateStatusRemark',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**查询历史数据 oms**/
    @RequestMapping(value="/getHistory4Oms",method = {RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> getHistory4Oms(String salOrderCode, String agencyCode, String startTime, String endTime, int page, int rows){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getHistory4Oms',in:" + JSON.toJSONString(salOrderCode) + "}");
        Map<String,Object> res  = new HashMap<>();
        List<SalesLeadsOperationModel> modelList = new ArrayList<>();
        String total = "";

        try{
            modelList =  saleLeadsOrderService.getHistory4Oms(salOrderCode,agencyCode,startTime,endTime,page,rows);
            if(CollectionUtils.isEmpty(modelList)){
                total = "0";
            }else{
                total = modelList.get(0).getCount();
            }
            res.put("total",total);
            res.put("rows",modelList);

        } catch(Exception e){
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getHistory4Oms',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }

    /**查询订单明细 oms**/
    @RequestMapping(value="/getSalOrderItem4Oms",method = {RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> getSalOrderItem4Oms(String salesLeadsOrderId){
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getSalOrderItem4Oms',in:" + JSON.toJSONString(salesLeadsOrderId) + "}");
        Map<String,Object> res  = new HashMap<>();
        List<SalOrderItem4OmsModel> modelList = new ArrayList<>();
        int total = 0;

        if (StringUtils.isEmpty(salesLeadsOrderId)) {
            res.put("total",total);
            res.put("rows",modelList);
            return res;
        }
        try{
            modelList =  saleLeadsOrderService.getSalOrderItem4Oms(salesLeadsOrderId);
            if(CollectionUtils.isEmpty(modelList)){
                total = 0;
            }else{
                total = modelList.get(0).getCount();

            }
            res.put("total",total);
            res.put("rows",modelList);

        } catch(Exception e){
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getSalOrderItem4Oms',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     * 导出历史数据 oms
     **/
    @RequestMapping(value = "/exportHistory4Oms", method = {RequestMethod.GET})
    @ResponseBody
    public void exportHistory4Oms(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::exportHistory4Oms',start:"+ DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss:ms"));

        String excelName = "留资操作记录";
        String salOrderCode = request.getParameter("salOrderCode");
        String agencyCode = request.getParameter("agencyCode");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if (StringUtils.isEmpty(excelName) ) {
            return;
        }
        try {
            final String userAgent = request.getHeader("USER-AGENT");
            String finalFileName = null;
            if (StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(excelName.getBytes(), "ISO8859-1");
            }else {
                finalFileName = URLEncoder.encode(excelName,"UTF8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + finalFileName + ".xlsx");// 定义文件名
            response.setContentType("application/x-download; charset=UTF-8");
            OutputStream out = response.getOutputStream();
            String count = saleLeadsOrderService.exportHistory4Oms(out, salOrderCode, agencyCode, startTime, endTime);
        } catch (Exception e) {
            LogUtil.controllerLogError(e.getMessage(), e);

        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::exportHistory4Oms',end:"+ DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss:ms"));
    }


    /**
     * 查询操作状态时备注 oms
     **/
    @RequestMapping(value = "/getOperateRemark", method = {RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Map<String, Object> getOperateRemark(@RequestBody SalOrderRecoreForm param) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getOperaRemark',in:" + JSON.toJSONString(param) + "}");
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(param.getOrderCodeList())) {
            result.put("code", "501");
            result.put("data", "");
            return result;
        }
        try {
            Map<String, String> out = saleLeadsOrderService.getOperateRemark(param.getOrderCodeList());
            result.put("code", "200");
            result.put("data", out);
        } catch (Exception e) {
            LogUtil.controllerLogError(e.getMessage(), e);
            result.put("code", "500");
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getOperaRemark',out:" + JSON.toJSONString(result) + "}");
        return result;
    }

    /**
     * 查询还没操作的订单数量
     **/
    @RequestMapping(value = "/getCountUnOperate", method = {RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response getCountUnOperate(@RequestBody  Map<String, Object> params) {
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getCountUnOperate',in:" + JSON.toJSONString(params.get("agencyId")) + "}");
        Response result = new Response();
        if (StringUtils.isEmpty(params.get("agencyId").toString())) {
            result.resultCode = ErrorCode.INVALID_PARAM;
            result.message = "参数错误";
            return result;
        }
        try {
            int out = saleLeadsOrderService.getCountUnOperate(params.get("agencyId").toString());
            result.resultCode = ErrorCode.SUCCESS;
            result.data = out;
        } catch (Exception e) {
            LogUtil.controllerLogError(e.getMessage(), e);
            result.resultCode = ErrorCode.INTERNAL_ERR;
            result.message = "系统错误";
        }
        LogUtil.controllerLogInfo("{method:'SaleLeadsOrderController::getCountUnOperate',out:" + JSON.toJSONString(result) + "}");
        return result;
    }












    //------------------------------------------工具类------------------------------------------------

    /**
     * 刷新状态
     */
    private   String refreshStatus(String pk) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "SalesLeadsOrder");
        params.put("pk", pk);

        // 调用hybris方法
        String resultStr = hybrisUtils.refreshStatus(params);
        JSONObject jsonObject = JSON.parseObject(resultStr);
        Map<String, Object> resultMap = (Map) jsonObject;
        String result = resultMap.get("resultCode").toString();
        return result;
    }




}
