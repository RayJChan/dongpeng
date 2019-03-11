package com.dpmall.model;

import java.io.Serializable;

/**
 * 用户对象
 * @author river
 * @date 2017-07-19
 */
public class UserModel implements Serializable {
	private static final long serialVersionUID = -8212307574511449520L;

	/**逻辑主键*/
	public Long id;
	
	/**用户名*/
    public String username;
    
    /**中文名*/
    public String cnName;
    
    /**角色编码*/
    public String roleCode;
    
    /**经销商ID*/
    public Long agencyId;
    
    /**门店ID*/
    public Long storeId;
    
    /**手机号码**/
    public String telePhone;
    
    /**店铺名称**/
    public String storeName;
    
    /**店铺（经销商）地址**/
    public String storeAddress;
}
