package com.dpmall.datasvr.api;

import com.dpmall.db.bean.StoreOfSalEntity;

/**
 * 发送短信
 */
public interface ISendMsgService {

    /**
     * 发送短信（留资接单发送核销码）
     */
    public boolean sendMsgSalAccept(StoreOfSalEntity storeOfSalEntity);

    /**
     * 发送短信（留资接单已完成）
     */
    public boolean sendMsgSalSuccess(String phoneNumber,String payPrice);


    /**
     * 发送短信（留资接单未完成）
     */
    public boolean sendMsgSalFailed(String phoneNumber,String storeName);

}
