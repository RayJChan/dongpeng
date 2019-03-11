package com.dpmall.param;

import java.io.Serializable;

/**
 * 统计参数
 * @author river
 * @date 2017-07-18
 */
public class SaleLeadStatisticParam implements Serializable{
	private static final long serialVersionUID = 2718302718019889798L;

	/**门店ID*/
    public Long storeId;
    
    /**导购员用户名*/
    public String acceptorName;
    
    /**商品品类*/
    public String productCatelog;
    
    /**统计开始时间*/
    public String fromTime;
    
    /**统计时间*/
    public String toTime;
}
