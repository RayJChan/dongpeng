package com.dpmall.web.controller;

import java.util.ArrayList;
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

import com.dpmall.datasvr.api.IStoreService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.StoreModel;
import com.dpmall.web.controller.form.Response;
import com.dpmall.web.controller.form.StoreForm;

/**
 * 门店接口服务
 * @author river
 * @since 2017-07-10
 */
@Controller
@RequestMapping("/store")
public class StoreController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private IStoreService storeService;
	
    /**
     * 获取经销商所有门店
     * @return
     */
    @RequestMapping(value="/listDistributorStores",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response listDistributorStores(@RequestBody StoreForm storeForm) {
    	Response res = new Response();
    	if (StringUtils.isEmpty(storeForm.distributorId)) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
		}
    	else {
    		 try{
				 List<StoreModel> data = new ArrayList<>();
				 //暂时取消
				 data = storeService.listDistributorStores(storeForm.distributorId, storeForm.storeName);
				 res.data = data;
				 res.resultCode = ErrorCode.SUCCESS;
    	        } catch(Throwable e){
    	        	LOG.error(e.getMessage(),e);
    	    	}
    	}
    	return res;
    }
}
