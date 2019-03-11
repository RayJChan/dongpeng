package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;

import java.util.Date;

public class MerchantCode extends DataEntity<MerchantCode> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long productId;

    private String productCode;

    private String productBrand;

    private String productDescribe;

    private String merchantCode;

    private String conversionLogicname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MerchantCode(){
        super();
        this.dataTableName="商家编码";
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    @ExcelField(title = "商品编码", sort = 1)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @ExcelField(title = "商品品牌", sort = 2)
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }
    @ExcelField(title = "商品描述", sort = 3)
    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }
    @ExcelField(title = "商家爱编码", sort = 4)
    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }
    @ExcelField(title = "换算逻辑", sort = 5)
    public String getConversionLogicname() {
        return conversionLogicname;
    }

    public void setConversionLogicname(String conversionLogicname) {
        this.conversionLogicname = conversionLogicname == null ? null : conversionLogicname.trim();
    }
}