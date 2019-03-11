package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 物流档案表 实体类
 */
public class Logistics extends DataEntity<Logistics> {
    private static final long serialVersionUID = 1L;

    @DataName("物流公司名称")
    private String logisticsName;
    @DataName("编码")
    private String logisticsCode;
    @DataName("发票税率")
    private Integer taxInvoice;
    @DataName("天猫回写编码")
    private String tmCode;
    @DataName("苏宁回写编码")
    private String snCode;
    @DataName("京东回写编码")
    private String jdCode;
    @DataName("是否启用接单功能")
    private Integer orderReceiving;

    public Logistics (){
        super();
        this.dataTableName="物流档案";
    }

    public Logistics(Long id){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    public Logistics(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "物流公司名称必须介于 2 和 10 之间")
    @ExcelField(title = "物流公司名称")
    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    @NotNull
    @Length(min = 2, max = 2, message = "编码必须长度2")
    @ExcelField(title = "编码")
    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    @NotNull(message = "发票税率不能为空")
    @ExcelField(title = "发票税率")
    public Integer getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(Integer taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    @NotNull
    @Length(min = 2, max = 5, message = "天猫回写编码名称必须介于 2 和 5 之间")
    @ExcelField(title = "天猫回写编码名称")
    public String getTmCode() {
        return tmCode;
    }

    public void setTmCode(String tmCode) {
        this.tmCode = tmCode;
    }

    @NotNull
    @Length(min = 2, max = 5, message = "苏宁回写编码名称必须介于 2 和 5 之间")
    @ExcelField(title = "苏宁回写编码名称")
    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    @NotNull
    @Length(min = 2, max = 5, message = "京东回写编码名称必须介于 2 和 5 之间")
    @ExcelField(title = "京东回写编码名称")
    public String getJdCode() {
        return jdCode;
    }

    public void setJdCode(String jdCode) {
        this.jdCode = jdCode;
    }

    @ExcelField(title = "是否启用接单功能")
    public Integer getOrderReceiving() {
        return orderReceiving;
    }

    public void setOrderReceiving(Integer orderReceiving) {
        this.orderReceiving = orderReceiving;
    }
}
