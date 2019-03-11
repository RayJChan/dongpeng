package com.dongpeng.common.db.service;

//import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
//import com.lorne.core.framework.utils.http.HttpUtils;

/**
 * LCN 分布式事务管理 配置
 * <BR/>调用方需要添加此类
 * 暂时不支持spring cloud 2.x
 */
//@Service
    @Deprecated
public class TxManagerHttpRequestServiceImpl //implements TxManagerHttpRequestService
{
    //@Override
    public String httpGet(String url) {
        System.out.println("httpGet-start");
        //String res = HttpUtils.get(url);
        System.out.println("httpGet-end");
        return null;
    }

    //@Override
    public String httpPost(String url, String params) {
        System.out.println("httpPost-start");
        //String res = HttpUtils.post(url,params);
        System.out.println("httpPost-end");
        return null;
    }
}
