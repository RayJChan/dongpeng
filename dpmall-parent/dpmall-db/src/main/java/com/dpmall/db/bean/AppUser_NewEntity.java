package com.dpmall.db.bean;

/**
 * @author cwj
 *
 */
public class AppUser_NewEntity {
	/**逻辑主键*/
	public Long id;
	
	/**用户名*/
    public String userName;
    
    /**密码**/
    public String password;
    
    /**中文名*/
    public String cnName;
     
    /**角色编码*/
    public String role;
    
    /**关系表id*/
    public Long orgId;
    
    /**客户id*/
    public Long kehuId;
    
    /**分组id**/
    public String groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getKehuId() {
        return kehuId;
    }

    public void setKehuId(Long kehuId) {
        this.kehuId = kehuId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
