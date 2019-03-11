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

    /**用户表的id*/
    public Long userId;
    
    
    
    /**角色权限范围*/
    public AppGroupEntity appGroupEntity;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AppGroupEntity getAppGroupEntity() {
        return appGroupEntity;
    }

    public void setAppGroupEntity(AppGroupEntity appGroupEntity) {
        this.appGroupEntity = appGroupEntity;
    }
}
