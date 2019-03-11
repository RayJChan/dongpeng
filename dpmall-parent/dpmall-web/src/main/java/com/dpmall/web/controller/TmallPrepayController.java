package com.dpmall.web.controller;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.LogUtil;
import com.dpmall.datasvr.api.IPrepayService;
import com.dpmall.datasvr.api.ITmallPrepayService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.in.TmallOrderInfoModelIn;
import com.dpmall.model.prePay.TmallPrePayModel;
import com.dpmall.web.controller.form.AppOrderForm;
import com.dpmall.web.controller.form.AppPrepayForm;
import com.dpmall.web.controller.form.Response;
import com.dpmall.web.controller.form.prePay.TmallPrepayForm;
import com.dpmall.web.controller.form.prePay.PrepayListForm;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tmallPrepay")
public class TmallPrepayController {


    @Autowired
    private ITmallPrepayService tmallPrepayService;

    @Autowired
    private HybrisUtils hybrisUtils;

    /**
     * 天猫 特权定金 列表（经销商）
     */
    @RequestMapping(value = "/getListOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListOfAgency(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getAgencyId()) || StringUtils.isEmpty(form.getStatus())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = tmallPrepayService.getListOfAgency(form.getAgencyId(), form.getStatus(), form.getPageNum(), form.getPageSize(),form.getSearch(), form.getOrderId(),form.getClientName(),form.getClientTel(),form.getStaRevisitTime(),form.getEndRevisitTime());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 天猫 特权定金 列表（门店）
     */
    @RequestMapping(value = "/getListOfStore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListOfStore(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListOfStore',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getStoreId()) || StringUtils.isEmpty(form.getStatus())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = tmallPrepayService.getListOfStore(form.getStoreId(), form.getStatus(), form.getPageNum(), form.getPageSize(),form.getSearch(), form.getOrderId(),form.getClientName(),form.getClientTel(),form.getStaRevisitTime(),form.getEndRevisitTime());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListOfStore',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 天猫 特权定金 数量（经销商）
     */
    @RequestMapping(value = "/getListCountOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListCountOfAgency(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListCountOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getAgencyId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = tmallPrepayService.getListCountOfAgency(form.getAgencyId());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListCountOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }

    /**
     * 天猫 特权定金 数量（门店）
     */
    @RequestMapping(value = "/getListCountOfStore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListCountOfStore(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListCountOfStore',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getStoreId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = tmallPrepayService.getListCountOfStore(form.getStoreId());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getListCountOfStore',out:" + JSON.toJSONString(response) + "}");
        return response;
    }

    /**
     * 天猫 特权定金 获取详情
     */
    @RequestMapping(value = "/getDetails", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getDetails(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getDetails',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = tmallPrepayService.getDetails(form.getPrePayId());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::getDetails',out:" + JSON.toJSONString(response) + "}");
        return response;
    }



    /**
     * 天猫 接单（经销商）
     */
    @RequestMapping(value = "/acceptOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response acceptOfAgency(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::acceptOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getStoreId()) || CollectionUtils.isEmpty(form.getPrePayIdList())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                int i = tmallPrepayService.accept(form.getPrePayIdList(),form.getStoreId(),"Y");
                response.data =i;
//                if (i>0){
//                    for(String prePayId:form.getPrePayIdList()){
//                        refreshStatus("Consignment",prePayId);//刷新状态~
//                        refreshStatus("O2OConsignmentItems",tmallPrepayService.getO2oIdByConsignmentId(prePayId));//刷新状态~
//                    }
//                      }
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::acceptOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 天猫 接单（门店）
     */
    @RequestMapping(value = "/acceptOfStore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response acceptOfStore(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::acceptOfStore',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if ( CollectionUtils.isEmpty(form.getPrePayIdList())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                int i= tmallPrepayService.accept(form.getPrePayIdList(),"","N");
                response.data  = i;
                response.resultCode = ErrorCode.SUCCESS;
//                if (i>0){
//                    for(String prePayId:form.getPrePayIdList()){
//                        refreshStatus("Consignment",prePayId);//刷新状态~
//                        refreshStatus("O2OConsignmentItems",tmallPrepayService.getO2oIdByConsignmentId(prePayId));//刷新状态~
//                    }
//                }

            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::acceptOfStore',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 天猫 撤单（经销商）
     */
    @RequestMapping(value = "/withdrawOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response withdrawOfAgency(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::withdrawOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                int i  = tmallPrepayService.withdraw(form.getPrePayId(),"Y");
                response.data = i;
                response.resultCode = ErrorCode.SUCCESS;
//                if (i>0){
//                    refreshStatus("Consignment",form.getPrePayId());//刷新状态~
//                    refreshStatus("O2OConsignmentItems",tmallPrepayService.getO2oIdByConsignmentId(form.getPrePayId()));//刷新状态~
//                }
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::withdrawOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 天猫 添加备注
     */
    @RequestMapping(value = "/addRemarks", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response addRemarks(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::addRemarks',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = tmallPrepayService.addRemarks(form.getPrePayId(), form.getAgencyRemark(), form.getStoreRemark(), form.getOperatorBy());
                response.resultCode = ErrorCode.SUCCESS;
//                //刷新状态~
//                refreshStatus("Consignment", form.getPrePayId());
//                refreshStatus("O2OConsignmentItems",tmallPrepayService.getO2oIdByConsignmentId(form.getPrePayId()));//刷新状态~
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::addRemarks',out:" + JSON.toJSONString(response) + "}");
        return response;
    }

    /**
     * 刷新状态
     */
    private   Object refreshStatus(String tableName,String pk) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", tableName);
        params.put("pk", pk);

        // 调用hybris方法
        String resultStr = hybrisUtils.refreshStatus(params);
        JSONObject jsonObject = JSON.parseObject(resultStr);
        Map<String, Object> resultMap = (Map) jsonObject;
        Object result = resultMap.get("resultCode");
        return result;
    }


    /**
     * 更新订单进度
     */
    @RequestMapping(value="/updateOrderProgress",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response updateOrderProgress(@RequestBody TmallPrepayForm form){
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::updateOrderProgress',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) || StringUtils.isEmpty(form.getStatusName()) || StringUtils.isEmpty(form.getOperatorBy())) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            int i = tmallPrepayService.updateOrderProgress(form.getPrePayId(),form.getStatusName(),form.getAgencyRemark(),form.getFailTypeId(), form.getOperatorBy(),form.getIsLoss(),form.getOrderEvaluation(),form.getRevisitTime());
            res.data = i;
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::updateOrderProgress',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     *  填写订单信息(提交)
     */
    @RequestMapping(value="/submitTmallOrderInfo",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response updateSalOrderInfo(@RequestBody TmallOrderInfoModelIn form){
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::updateTmallOrderInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.getTmallOrderId()==null||form.getPictureNames()==null||StringUtils.isEmpty(form.getOperatorBy())||StringUtils.isEmpty(form.getPayPrice())) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            int i = tmallPrepayService.updateTmallOrderInfo(form);
            res.data = i;
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::submitTmallOrderInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     * 填写订单信息(只保存不提交)
     */
    @RequestMapping(value="/saveTmallOrderInfo",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response saveSalOrderInfo(@RequestBody TmallOrderInfoModelIn form){
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::saveTmallOrderInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
       /* if (form.getTmallOrderId()==null||form.getPictureNames()==null||StringUtils.isEmpty(form.getOperatorBy())) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }*/
        try{
            int i = tmallPrepayService.saveSalOrderInfo(form);
            res.data = i;
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.data = 0;
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误 ";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'TmallPrepayController::saveTmallOrderInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }

    /**
     * 刷新状态
     */
    private   Object refreshStatus(String pk) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "SalesLeadsOrder");
        params.put("pk", pk);

        // 调用hybris方法
        String resultStr = hybrisUtils.refreshStatus(params);
        JSONObject jsonObject = JSON.parseObject(resultStr);
        Map<String, Object> resultMap = (Map) jsonObject;
        Object result = resultMap.get("resultCode");
        return result;
    }
}
