package com.dpmall.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录返回结果
 * @author river
 * @date 2017-7-19-
 */
public class LoginResModel implements Serializable {
	
	private static final long serialVersionUID = 2238092228853902778L;

	/**用户逻辑主键*/
	public Long id;
	
	/**账户*/
    public String username;
    
    /**中文名称*/
    public String cnName;
    
    /**角色编码*/
    public String roleCode;
    
    /**代理商ID*/
    public Long agencyId;
    
    /**门店ID*/
    public Long storeId;
    
    /**登录token*/
    public String token;
    
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
    
    /**功能列表*/
    public Set<String> funcList;
    
    /**登陆者所属权限范围*/
    public AppGroupModel appGroupModel;
}
