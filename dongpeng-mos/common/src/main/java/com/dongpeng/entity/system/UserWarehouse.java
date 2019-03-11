package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 用户仓库表 实体类
 */
public class UserWarehouse extends DataEntity<UserWarehouse> {
    private static final long serialVersionUID = 1L;
    private Long userId; //用户id
    private Long warehouseId; //仓库id

    public UserWarehouse(){
        super();
    }

    public UserWarehouse(Long userId, Long warehouseId){
        super();
        this.userId=userId;
        this.warehouseId=warehouseId;
    }

    public UserWarehouse(Long userId){
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
        return warehouseId;
    }

    public void setDepartmentId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

}
