package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 用户部门表 实体类
 */
public class UserDepartment extends DataEntity<UserDepartment> {
    private static final long serialVersionUID = 1L;
    private Long userId; //用户id
    private Long departmentId; //部门id

    public UserDepartment(){
        super();
    }

    public UserDepartment(Long userId, Long departmentId){
        super();
        this.userId=userId;
        this.departmentId=departmentId;
    }

    public UserDepartment(Long userId){
        super();
        this.userId=userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

}
