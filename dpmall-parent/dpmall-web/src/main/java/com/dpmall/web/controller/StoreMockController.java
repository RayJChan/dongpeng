package com.dpmall.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dpmall.model.StoreModel;
import com.dpmall.web.controller.form.Response;

/**
 * 门店接口服务
 * @author river
 * @since 2017-07-10
 */
@Controller
@RequestMapping("/storemock")
public class StoreMockController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StoreMockController.class);
	
    /**
     * 获取经销商所有门店
     * @param distributorId 经销商ID
     * @return
     */
    @RequestMapping(value="/listDistributorStores",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
    @ResponseBody
    public Response listDistributorStores(String distributorId,String token) {
    	Response res = new Response();
        try{
        	List<StoreModel> data = new ArrayList<StoreModel>();
        	for(int i = 0; i<10 ;i++){
        		StoreModel tmp = new StoreModel();
        		tmp.storeId = ""+i;
        		tmp.storeName = "平哥店"+i;
        		tmp.storeAddr = "美国国贸大厦1楼";
        		data.add(tmp);
        	}
        	res.data = data;
        } catch(Throwable e){
        	LOG.error(e.getMessage(),e);
    	}
    	

    	return res;
    }
}
