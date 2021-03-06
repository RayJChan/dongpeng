package com.dpmall.db.bean;

import java.util.Date;

/**
 * 商品统计表
 * table:T_PRODUCT_STATISTICS
 * @author river
 * @since 2017-06-23
 */
public class ProductStatisticEntity {
	
	/**PK**/
    public Long id;
    
    /**FROM_TIME*/
    public Date fromTime;
    
    /**END_TIME*/
    public Date endTime;
    
    /**PRODUCT_ID*/
    public Long productId;
    
    /**PRODUCT_CODE*/
    public String productCode;
    
    /**TTL_SALE*/
    public Long saleTotal;
    
    /**TTL_AMOUNT*/
    public Long totalAmount;
    
    /**TTL_DEALS*/
    public Long totalDeals;
    
    /**TTL_ACCESS*/
    public Long totalAccess;
    
    /**CREATE_TIME*/
    public Date createTime;
    
    /**UPDATE_TIME*/
    public Date updateTime;
    
}
