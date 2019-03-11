package com.dpmall.web.controller.form;

import java.util.List;
import java.util.Map;


public class DistributeForm extends RequestForm {
    /**经销商账户ID*/
    public String distributorId;

    /**留资单=》店铺ID*/
    public Map<String,String> saleLeadsId2shopId;

    /**经销商备注**/
    public String agencyRemark;

    /**经销商备注**/
    public String storeRemark;

    /**状态列表**/
    public String status;

    /**店铺ID**/
    public String storeId;

    /**接单人ID**/
    public String acceptorId;

    /**查询条件**/
    public String search;

    //------新增-----

    /**留资单id*/
    public List<String> saleLeadsOrderIds;

    /**经销商账户ID*/
    public String agencyId;

    /**列表状态*/
    public String listStatus;

    /**页数**/
    public Integer pageNum;

    /**页的大小**/
    public Integer pageSize;

    /**状态搜索**/
    public String statusSearch;



}
