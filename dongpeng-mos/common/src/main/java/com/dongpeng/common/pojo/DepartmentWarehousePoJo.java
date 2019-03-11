package com.dongpeng.common.pojo;

import java.io.Serializable;

/**
 *  部门仓库关联返回信息
 */
public class DepartmentWarehousePoJo implements Serializable {
    public Long id;

    public Long warehouseId;

    public String warehouseName;

    public String warehouseSystem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseSystem() {
        return warehouseSystem;
    }

    public void setWarehouseSystem(String warehouseSystem) {
        this.warehouseSystem = warehouseSystem;
    }
}
