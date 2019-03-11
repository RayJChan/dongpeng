package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 仓库表 实体类
 */
public class Warehouse extends TreeEntity<Warehouse> {
    private static final long serialVersionUID = 1L;
    @DataName("库区编号")
    private String warehouseCode;
    @DataName("库区名称")
    private String warehouseName;
    /**
     * 所属公司 ID
     **/
    private Long companyId;
    @DataName("所属公司名称")
    private String companyName;
    /**
     * ("父类id")
     */
    private Long parentId;
    /**
     * ("父类名称")
     */
    private String parentName;
    @DataName("仓库对应系统")
    private String warehouseSystem;
    @DataName("库区类型")
    private String warehouseType;
    @DataName("对应标识")
    private String corrIdentifying;
    @DataName("负库存")
    private Boolean inventory;
    @DataName("发货省")
    private String province;
    @DataName("发货市")
    private String city;
    @DataName("发货区")
    private String district;
    @DataName("发货地址")
    private String address;
    @DataName("是否启用库位")
    private Boolean isStorage;
    @DataName("是否启用工厂")
    private Boolean isFactory;


    @ExcelField(title = "是否启用库位")
    public Boolean getIsStorage() {
        return isStorage;
    }

    public void setIStorage(Boolean isStorage) {
        this.isStorage = isStorage;
    }

    @ExcelField(title = "是否启用工厂")
    public Boolean getIsFactory() {
        return isFactory;
    }

    public void setIsFactory(Boolean isFactory) {
        this.isFactory = isFactory;
    }




    public Warehouse() {
        super();
        this.dataTableName = "仓库";
    }

    public Warehouse(Long id) {
        super(id);
    }

    public Warehouse(Long id, boolean deleteFlag) {
        super(id);
        this.deleteFlag = deleteFlag;
    }









    @NotNull
    @Length(min = 2, max = 10, message = "编码必须介于 2 和 10 之间")
    @ExcelField(title = "编码")
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "名称必须介于 2 和 20 之间")
    @ExcelField(title = "名称")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @NotNull
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @ExcelField(title = "公司名称")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @ExcelField(title = "仓库对应系统")
    public String getWarehouseSystem() {
        return warehouseSystem;
    }

    public void setWarehouseSystem(String warehouseSystem) {
        this.warehouseSystem = warehouseSystem;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "库区类型介于 2 和 10 之间")
    @ExcelField(title = "库区类型")
    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    @ExcelField(title = "对应标识")
    public String getCorrIdentifying() {
        return corrIdentifying;
    }

    public void setCorrIdentifying(String corrIdentifying) {
        this.corrIdentifying = corrIdentifying;
    }

    @ExcelField(title = "负库存")
    public Boolean getInventory() {
        return inventory;
    }

    public void setInventory(Boolean inventory) {
        this.inventory = inventory;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "发货省必须介于 2 和 10 之间")
    @ExcelField(title = "发货省")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "发货市必须介于 2 和 10 之间")
    @ExcelField(title = "发货市")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "发货区必须介于 2 和 10 之间")
    @ExcelField(title = "发货区")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @ExcelField(title = "发货地址")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    @Override
    public String getParentName() {
        return parentName;
    }

    @Override
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
