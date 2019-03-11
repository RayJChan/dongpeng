package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 用户公司表 实体类
 */
public class UserCompany extends DataEntity<UserCompany> {
    private static final long serialVersionUID = 1L;
    private Long userId; //用户id
    private Long companyId; //公司id

    public UserCompany(){
        super();
    }

    public UserCompany(Long userId, Long companyId){
        super();
        this.userId=userId;
        this.companyId=companyId;
    }

    public UserCompany(Long userId){
        super();
        this.userId=userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
