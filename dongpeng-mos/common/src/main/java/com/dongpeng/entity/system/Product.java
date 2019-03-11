package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.springframework.web.servlet.DispatcherServlet;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Product extends DataEntity<Product> {

    @ExcelField(title = "品牌ID")
    @NotNull
    private Long brandId;

    @ExcelField(title = "品牌名称")
    @NotNull
    private String brandName;

    @ExcelField(title = "商品编码")
    @NotNull
    private String commodityCode;

    @ExcelField(title = "厂家货号")
    private String itemCode;

    @ExcelField(title = "品类ID")
    @NotNull
    private Long categoryId;

    @ExcelField(title = "一级品类名称")
    private String firstCategoryName;

    @ExcelField(title = "二级品类名称")
    private String secondCategoryName;

    @ExcelField(title = "三级品类名称")
    @NotNull
    private String thirdCategoryName;

    @ExcelField(title = "重量")
    @NotNull
    private BigDecimal weight;

    @ExcelField(title = "体积")
    @NotNull
    private BigDecimal volume;

    @ExcelField(title = "面积")
    @NotNull
    private BigDecimal area;

    @ExcelField(title = "描述")
    @NotNull
    private String description;

    @ExcelField(title = "商品简称")
    @NotNull
    private String abbreviation;

    @ExcelField(title = "包装箱")
    @NotNull
    private Integer packBox;

    @ExcelField(title = "包装件")
    @NotNull
    private Integer packPc;

    @ExcelField(title = "规格")
    private String size;

    @ExcelField(title = "商品类型")
    private Integer commodityType;

    @ExcelField(title = "区分批次")
    private Boolean isBatch;

    @ExcelField(title = "区分工厂")
    private Boolean isFactory;

    @ExcelField(title = "标价")
    @NotNull
    private BigDecimal bidPrice;

    @ExcelField(title = "单位ID")
    private Long unitId;

    @ExcelField(title = "单位名称")
    @NotNull
    private String unitName;

    @ExcelField(title = "商品标识")
    private Long identifyId;

    @ExcelField(title = "商品标识名称")
    private String identifyName;

    @ExcelField(title = "是否定制")
    private Boolean custom;

    @ExcelField(title = "是否停产")
    private Boolean isDiscontinued;

    @ExcelField(title = "安全库存")
    private Integer safeStock;

    @ExcelField(title = "采购日期")
    private Integer procurementDate;

    @ExcelField(title = "图片链接")
    private String photograph;

    @ExcelField(title = "送货方式ID")
    private Long deliveryId;

    @ExcelField(title = "送货方式名称")
    private String deliveryName;

    @ExcelField(title = "条形码")
    private String barCode;

    public Product(){
        super();
        this.dataTableName="商品";
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public String getThirdCategoryName() {
        return thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }

    public Boolean getBatch() {
        return isBatch;
    }

    public void setBatch(Boolean batch) {
        isBatch = batch;
    }

    public Boolean getFactory() {
        return isFactory;
    }

    public void setFactory(Boolean factory) {
        isFactory = factory;
    }

    public Boolean getDiscontinued() {
        return isDiscontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        isDiscontinued = discontinued;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getPackBox() {
        return packBox;
    }

    public void setPackBox(Integer packBox) {
        this.packBox = packBox;
    }

    public Integer getPackPc() {
        return packPc;
    }

    public void setPackPc(Integer packPc) {
        this.packPc = packPc;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Integer getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
    }

    public Boolean getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(Boolean isBatch) {
        this.isBatch = isBatch;
    }

    public Boolean getIsFactory() {
        return isFactory;
    }

    public void setIsFactory(Boolean isFactory) {
        this.isFactory = isFactory;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(Long identifyId) {
        this.identifyId = identifyId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getIdentifyName() {
        return identifyName;
    }

    public void setIdentifyName(String identifyName) {
        this.identifyName = identifyName == null ? null : identifyName.trim();
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public Boolean getIsDiscontinued() {
        return isDiscontinued;
    }

    public void setIsDiscontinued(Boolean isDiscontinued) {
        this.isDiscontinued = isDiscontinued;
    }

    public Integer getSafeStock() {
        return safeStock;
    }

    public void setSafeStock(Integer safeStock) {
        this.safeStock = safeStock;
    }

    public Integer getProcurementDate() {
        return procurementDate;
    }

    public void setProcurementDate(Integer procurementDate) {
        this.procurementDate = procurementDate;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph == null ? null : photograph.trim();
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName == null ? null : deliveryName.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

}