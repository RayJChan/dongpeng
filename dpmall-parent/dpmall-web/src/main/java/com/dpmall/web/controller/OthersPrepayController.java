package com.dpmall.web.controller;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.LogUtil;
import com.dpmall.datasvr.api.IOthersPrepayService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.in.OtherPrePayInfoModelIn;
import com.dpmall.model.in.PrepayModelIn;
import com.dpmall.model.prePay.OtherPrePayOperationDetailModel;
import com.dpmall.model.prePay.OtherPrepayOperationModel;
import com.dpmall.model.prePay.OthersPrePayDetailModel;
import com.dpmall.web.controller.form.Response;
import com.dpmall.web.controller.form.prePay.OtherPrepayForm;
import com.dpmall.web.controller.form.prePay.PrepayListForm;
import com.dpmall.web.controller.form.prePay.TmallPrepayForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
//暂时 注释
//@RequestMapping("/othersPrepay")
public class OthersPrepayController {


    @Autowired
    private IOthersPrepayService othersPrepayService;

    @Autowired
    private HybrisUtils hybrisUtils;


    /**
     * 其他 特权定金 列表（经销商）
     */
    @RequestMapping(value = "/getListOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListOfAgency(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getAgencyId()) || StringUtils.isEmpty(form.getStatus())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.getListOfAgency(form.getAgencyId(),form.getStatus(),form.getPageNum(),form.getPageSize(),form.getSearch());

                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 其他 特权定金 列表（门店）
     */
    @RequestMapping(value = "/getListOfStore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListOfStore(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListOfStore',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getStoreId()) || StringUtils.isEmpty(form.getStatus())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.getListOfStore(form.getStoreId(),form.getStatus(),form.getPageNum(),form.getPageSize(),form.getSearch());

                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListOfStore',out:" + JSON.toJSONString(response) + "}");
        return response;
    }



    /**
     * 其他 特权定金 数量（经销商)
     */
    @RequestMapping(value = "/getListCountOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListCountOfAgency(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListCountOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getAgencyId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.getListCountOfAgency(form.getAgencyId());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListCountOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }

    /**
     * 其他 特权定金 数量（经销商)
     */
    @RequestMapping(value = "/getListCountOfStore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getListCountOfStore(@RequestBody PrepayListForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListCountOfStore',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getStoreId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.getListCountOfStore(form.getStoreId());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getListCountOfStore',out:" + JSON.toJSONString(response) + "}");
        return response;
    }

    /**
     * 其他 特权定金 获取详情
     */
    @RequestMapping(value = "/getDetails", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getDetails(@RequestBody Map<String,String> form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getDetails',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.get("prepayId")) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.getDetails(form.get("prepayId"));
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getDetails',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 其他 接单（经销商）
     */
    @RequestMapping(value = "/acceptOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response acceptOfAgency(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::acceptOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getStoreId()) || CollectionUtils.isEmpty(form.getPrePayIdList())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.agencyAccept(form.getPrePayIdList(),form.getStoreId(),form.getOperatorBy());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::acceptOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 其他 接单（门店）
     */
    @RequestMapping(value = "/acceptOfStore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response acceptOfStore(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::acceptOfStore',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if ( CollectionUtils.isEmpty(form.getPrePayIdList())){
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.storeAccept(form.getPrePayIdList(),form.getOperatorBy());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::acceptOfStore',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 其他 撤单（经销商）
     */
    @RequestMapping(value = "/withdrawOfAgency", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response withdrawOfAgency(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::withdrawOfAgency',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.withdraw(form.getPrePayId(),form.getOperatorBy());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::withdrawOfAgency',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 更新客户信息
     */
    @RequestMapping(value = "/updateCustomInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response updateCustomInfo(@RequestBody PrepayModelIn form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::updateCustomInfo',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) || StringUtils.isEmpty(form.getRegion()) || StringUtils.isEmpty(form.getCustomerName()) || StringUtils.isEmpty(form.getPhone())) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.updateCustomInfo(form,form.getOperatorBy());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::updateCustomInfo',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 更新订单进度
     */
    @RequestMapping(value = "/updateOrderProgress", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response updateOrderProgress(@RequestBody OtherPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::updateOrderProgress',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) || StringUtils.isEmpty(form.getStatusName()) || StringUtils.isEmpty(form.getOperatorBy()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.updateOrderProgress(form.getStatusName(),form.getPrePayId(),form.getRemark(),form.getOperatorBy(),form.getFailTypeId());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::updateOrderProgress',out:" + JSON.toJSONString(response) + "}");
        return response;
    }


    /**
     * 填写订单信息(提交)
     */
    @RequestMapping(value = "/updatePrePayOrderInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response updateSalOrderInfo(@RequestBody OtherPrePayInfoModelIn form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::updatePrePayOrderInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.getPrePayId() == null || form.getPictureNames() == null || StringUtils.isEmpty(form.getOperatorBy())) {
            res.resultCode = ErrorCode.INVALID_PARAM;
            res.message = "参数错误";
            return res;
        }
        try {
            int i = othersPrepayService.updatePrePayOrderInfo(form,form.getOperatorBy());
            res.resultCode = ErrorCode.SUCCESS;
            res.data = i ;
            if (i>0){
                refreshStatus("Consignment",form.getPrePayId());//刷新状态~
                refreshStatus("AbstractOrder",othersPrepayService.getIdByConsignmentId(form.getPrePayId()));//刷新状态~
            }
        } catch (Exception e) {
            res.data = 0;
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::updatePrePayOrderInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }


    /**
     * 填写订单信息(只保存不提交)
     */
    @RequestMapping(value = "/savePrePayOrderInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response savePrePayOrderInfo(@RequestBody OtherPrePayInfoModelIn form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::savePrePayOrderInfo',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (form.getPrePayId() == null || form.getPictureNames() == null || StringUtils.isEmpty(form.getOperatorBy())) {
            res.resultCode = ErrorCode.INVALID_PARAM;
            res.message = "参数错误";
            return res;
        }
        try {
            int i = othersPrepayService.savePrePayOrderInfo(form);
            res.data = i;
            if (i>0){
                refreshStatus("Consignment",form.getPrePayId());//刷新状态~
                refreshStatus("AbstractOrder",othersPrepayService.getIdByConsignmentId(form.getPrePayId()));//刷新状态~
            }
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.data = 0;
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::savePrePayOrderInfo',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }

    /**
     * 其他 添加备注
     */
    @RequestMapping(value = "/addRemarks", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response addRemarks(@RequestBody TmallPrepayForm form) {
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::addRemarks',in:" + JSON.toJSONString(form) + "}");
        Response response = new Response();
        if (StringUtils.isEmpty(form.getPrePayId()) ) {
            response.resultCode = ErrorCode.INVALID_PARAM;
            response.message = "参数错误";
        } else {
            try {
                response.data = othersPrepayService.addRemarks(form.getPrePayId(),form.getAgencyRemark(),form.getStoreRemark(),form.getOperatorBy());
                response.resultCode = ErrorCode.SUCCESS;
            } catch (Exception e) {
                response.resultCode = ErrorCode.INTERNAL_ERR;
                response.message = "系统错误";
                LogUtil.controllerLogError(e.getMessage(), e);
            }
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::addRemarks',out:" + JSON.toJSONString(response) + "}");
        return response;
    }

    /**
     * 查看更新状态时的备注
     */
    @RequestMapping(value="/getUpdateStatusRemark",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public Response getUpdateStatusRemark(@RequestBody Map<String,String> form){
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getUpdateStatusRemark',in:" + JSON.toJSONString(form) + "}");

        Response res = new Response();
        if (StringUtils.isEmpty(form.get("operationId"))) {
            res.resultCode=ErrorCode.INVALID_PARAM;
            res.message="参数错误";
            return res;
        }
        try{
            res.data = othersPrepayService.getUpdateStatusRemark(form.get("operationId"));
            res.resultCode=ErrorCode.SUCCESS;
        } catch(Exception e){
            res.resultCode=ErrorCode.INTERNAL_ERR;
            res.message="系统错误";
            LogUtil.controllerLogError(e.getMessage(),e);
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::getUpdateStatusRemark',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
    }





    /**
     * 判断核销码
     */
    @RequestMapping(value = "/judgeWriteOffCode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response judgeWriteOffCode(@RequestBody Map<String,String> form){
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::judgeWriteOffCode',in:{prePayId:" + JSON.toJSONString(form) + "}");
        Response res = new Response();

        if (StringUtils.isEmpty(form.get("prePayId"))) {
            res.resultCode = ErrorCode.INVALID_PARAM;
            res.message = "参数错误";
            return res;
        }
        try {
            res.data = othersPrepayService.judgeWriteOffCode(form.get("prePayId"),form.get("writeOffCode"));
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.data = 0;
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.data = false;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }
        LogUtil.controllerLogInfo("{method:'OthersPrepayController::judgeWriteOffCode',out:{res:'" + JSON.toJSONString(res) + "'}}");
        return res;
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



}
