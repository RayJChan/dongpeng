package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;


/**
 *  部门关联仓库实体类
 */
public class DepartmentWarehouse extends DataEntity<DepartmentWarehouse> {
    private Long id;

    private Long departmentId;

    private Long warehouseId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}