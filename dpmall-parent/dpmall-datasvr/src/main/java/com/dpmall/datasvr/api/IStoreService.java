package com.dpmall.datasvr.api;

import java.util.List;

import com.dpmall.model.StoreModel;

/**
 * 门店接口服务
 * @author river
 * @since 2017-07-10
 */
public interface IStoreService {
    /**
     * 获取经销商所有门店
     * @param distributorId 经销商ID
     * @return
     */
    public List<StoreModel> listDistributorStores(String distributorId,String storeName);

}
