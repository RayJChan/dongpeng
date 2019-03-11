package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 公司表 实体类
 */
public class Company extends DataEntity<Company> {
    private static final long serialVersionUID = 1L;
    @DataName("公司编码")
    private String companyCode;   //公司编码
    @DataName("公司名称")
    private String companyName;   //公司名称
    @DataName("公司税号")
    private String companyDuty;   //公司税号
    @DataName("电子发票")
    private Boolean eleInvoice;  //电子发票
    @DataName("公司类型")
    private String companyType;   //公司类型

    public Company(){
        super();
        this.dataTableName="公司";
    }

    public Company(Long id){
        super(id);
    }

    public Company(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "公司编码必须介于 2 和 10 之间")
    @ExcelField(title = "公司编码",sort = 10)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "公司名称必须介于 2 和 20 之间")
    @ExcelField(title = "公司名称",sort = 20)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "公司税号必须介于 2 和 20 之间")
    @ExcelField(title = "公司税号",sort = 30)
    public String getCompanyDuty() {
        return companyDuty;
    }

    public void setCompanyDuty(String companyDuty) {
        this.companyDuty = companyDuty;
    }

    @ExcelField(title = "电子发票",sort = 40)
    public Boolean getEleInvoice() {
        return eleInvoice;
    }

    public void setEleInvoice(Boolean eleInvoice) {
        this.eleInvoice = eleInvoice;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "公司类型必须介于 2 和 10 之间")
    @ExcelField(title = "公司类型",sort = 50)
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @NotNull
    @Override
    @ExcelField(title = "是否有效",type = 1,sort = 1050)
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public void setDeleteFlag(Boolean deleteFlag)
    {
        this.deleteFlag =  deleteFlag;
    }
}
