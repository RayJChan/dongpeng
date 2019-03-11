package com.dpmall.web.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.LogUtil;
import com.dpmall.datasvr.api.IOssTokenService;
import com.dpmall.datasvr.api.ISaleLeadsOrderService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.SaleLeadsOrderModel;
import com.dpmall.web.controller.form.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 ossToken
 */
@Controller
@RequestMapping("/oss")
public class OssTokenController {

    @Autowired
    private IOssTokenService ossTokenService;


    /**
     * 获取ossToken
     */
    @RequestMapping(value = "/getToken", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public Response getToken(@RequestBody DistributeForm form) {
        LogUtil.controllerLogInfo("{method:'OssTokenController::getToken',in:" + JSON.toJSONString(form) + "}");
        Response res = new Response();
        try {
            if (StringUtils.isEmpty(form.operatorBy)) {
                res.resultCode = ErrorCode.INVALID_PARAM;
                res.message = "参数错误";
                return res;
            }
            res.data = ossTokenService.getOssToken("write",form.operatorBy);
            res.resultCode = ErrorCode.SUCCESS;
        } catch (Exception e) {
            res.resultCode = ErrorCode.INTERNAL_ERR;
            res.message = "系统错误";
            LogUtil.controllerLogError(e.getMessage(), e);
        }

        LogUtil.controllerLogInfo("{method:'OssTokenController::getToken',in:" + JSON.toJSONString(res) + "}");
        return res;
    }


}
