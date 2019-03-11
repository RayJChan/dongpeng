package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 库位表 实体类
 */
public class StorageLocation extends DataEntity<StorageLocation> {

    private static final long serialVersionUID = 1L;
    @DataName("库位名称")
    private String storagelocationName;
    /** 库区id **/
    private Long warehouseId;
    @DataName("库区名称")
    private String warehouseName;

    public StorageLocation(){
        super();
        this.dataTableName="库位";
    }

    public StorageLocation(Long id){
        super(id);
    }

    public StorageLocation(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "库位名称必须介于 2 和 10 之间")
    @ExcelField(title = "库位名称")
    public String getStoragelocationName() {
        return storagelocationName;
    }

    public void setStoragelocationName(String storagelocationName) {
        this.storagelocationName = storagelocationName;
    }

    @NotNull
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @ExcelField(title = "库区名称")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
