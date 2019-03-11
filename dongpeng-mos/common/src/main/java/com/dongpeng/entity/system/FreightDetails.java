package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


/**
 * 运费价格明细表 实体类
 */
public class FreightDetails extends DataEntity<FreightDetails> {
    @DataName("项目id")
    private Long projectId;
    @DataName("地区id")
    private Long areaId;
    @DataName("省")
    private String province;
    @DataName("市")
    private String city;
    @DataName("区")
    private String district;
    @DataName("体积单价")
    private Double volumePrice;
    @DataName("重量单价")
    private Double weightPrice;
    @DataName("续重单价")
    private Double continuewPrice;
    @DataName("件数单价")
    private Double piecePrice;
    @DataName("送货单价")
    private Double deliveryPrice;
    @DataName("上楼费单价")
    private Double upstairsPrice;
    @DataName("到货时间")
    private Double arrivalTime;
    @DataName("考核时间")
    private Double assessTime;

    public FreightDetails(){
        super();
        this.dataTableName="运费价格明细";
    }

    public FreightDetails(Long id){
        super(id);
    }

    public FreightDetails(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull(message = "运费项目ID不能为空")
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @NotNull(message = "地区ID不能为空")
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @ExcelField(title = "省")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @ExcelField(title = "市")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ExcelField(title = "区")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @NotNull(message = "体积单价不能为空")
    @DecimalMin(value = "0.0",message = "体积单价不能小于0")
    @ExcelField(title = "体积单价")
    public Double getVolumePrice() {
        return volumePrice;
    }

    public void setVolumePrice(Double volumePrice) {
        this.volumePrice = volumePrice;
    }

    @NotNull(message = "重量单价不能为空")
    @DecimalMin(value = "0.0",message = "重量单价不能小于0")
    @ExcelField(title = "重量单价")
    public Double getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(Double weightPrice) {
        this.weightPrice = weightPrice;
    }

    @NotNull(message = "续重单价不能为空")
    @DecimalMin(value = "0.0",message = "续重单价不能小于0")
    @ExcelField(title = "续重单价")
    public Double getContinuewPrice() {
        return continuewPrice;
    }

    public void setContinuewPrice(Double continuewPrice) {
        this.continuewPrice = continuewPrice;
    }

    @NotNull(message = "件数单价不能为空")
    @DecimalMin(value = "0.0",message = "件数单价不能小于0")
    @ExcelField(title = "件数单价")
    public Double getPiecePrice() {
        return piecePrice;
    }

    public void setPiecePrice(Double piecePrice) {
        this.piecePrice = piecePrice;
    }

    @NotNull(message = "送货单价不能为空")
    @DecimalMin(value = "0.0",message = "送货单价不能小于0")
    @ExcelField(title = "送货单价")
    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @NotNull(message = "上楼费单价不能为空")
    @DecimalMin(value = "0.0",message = "上楼费单价不能小于0")
    @ExcelField(title = "上楼费单价")
    public Double getUpstairsPrice() {
        return upstairsPrice;
    }

    public void setUpstairsPrice(Double upstairsPrice) {
        this.upstairsPrice = upstairsPrice;
    }

    @NotNull(message = "到货时间不能为空")
    @DecimalMin(value = "0.0",message = "到货时间不能小于0")
    @ExcelField(title = "到货时间")
    public Double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @NotNull(message = "考核时间不能为空")
    @DecimalMin(value = "0.0",message = "考核时间不能小于0")
    @ExcelField(title = "考核时间")
    public Double getAssessTime() {
        return assessTime;
    }

    public void setAssessTime(Double assessTime) {
        this.assessTime = assessTime;
    }
}
