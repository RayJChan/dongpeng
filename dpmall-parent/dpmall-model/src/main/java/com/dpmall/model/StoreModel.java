package com.dpmall.model;

import java.io.Serializable;

/**
 * <p>
 * 分派到店铺
 * @author river
 * @since 2017-07-10
 */
public class StoreModel implements Serializable{
	private static final long serialVersionUID = 4667679293175450534L;
	
	/**店铺ID*/
	public String storeId;
	
	/**店铺名称*/
	public String storeName;
	
	/**经销商门店地址*/
	public String storeAddr;

}
