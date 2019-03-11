package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 用户组织
 */
public class UserOrg extends DataEntity<UserOrg> {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    private Long orgId;

    private String orgName;

    public UserOrg(){

    }

    public UserOrg(Long userId) {
        this.userId=userId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

}