package com.dongpeng.common.db.service;

//import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.springframework.beans.factory.annotation.Value;

/**
 * LCN 分布式事务管理 配置
 * <br/>用于读取lcn配置
 * 暂时不支持spring cloud 2.X
 */
@Deprecated
public class TxManagerTxUrlServiceImpl //implements TxManagerTxUrlService
{

    @Value("${tm.manager.url}")
    private String url;

    //@Override
    public String getTxUrl() {
        System.out.println("load tm.manager.url ");
        return url;
    }
}
