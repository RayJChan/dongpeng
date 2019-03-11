package com.dpmall.db.bean;

/**
 * @author xiecong
 *
 */
public class AppUserEntity {
	/**逻辑主键*/
	public Long id;
	
	/**用户名*/
    public String username;
    
    /**密码**/
    public String password;
    
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
    
    /**角色名称*/
    public String roleName;
    
    /**权限分组编码*/
    public String groupCode;
    
    /**权限分组名称*/
    public String groupName;
    
    /**权限分组类型*/
    public String groupType;
    
    /**权限分组直属门店*/
    public String groupStore;
    
    
    
    /**角色权限范围*/
    public AppGroupEntity appGroupEntity;
}
