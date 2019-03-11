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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupStore() {
        return groupStore;
    }

    public void setGroupStore(String groupStore) {
        this.groupStore = groupStore;
    }

    public Set<String> getFuncList() {
        return funcList;
    }

    public void setFuncList(Set<String> funcList) {
        this.funcList = funcList;
    }

    public AppGroupModel getAppGroupModel() {
        return appGroupModel;
    }

    public void setAppGroupModel(AppGroupModel appGroupModel) {
        this.appGroupModel = appGroupModel;
    }
}
