package com.dpmall.datasvr.api;


import com.dpmall.model.FirstPageModel;

/**
 * 获取首页数据
 * @author cwj
 */
public interface IFirstPageService {
    /**
     * 获取首页数据
     * @return
     */
    public FirstPageModel getFirstPageOfAgency(String code);
    
    /**
     * 获取首页数据
     * @return
     */
    public FirstPageModel getFirstPageOfStore(String storeId);
    
}
