package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * 运费折扣表 实体类
 */
public class FreightDiscount extends DataEntity<FreightDiscount> {
    @DataName("项目费用ID")
    private Long  freighttId;
    @DataName("总金额")
    private Double amount;
    @DataName("折扣")
    private Double discount;

    public FreightDiscount(){
        super();
        this.dataTableName="运费折扣";
    }

    public FreightDiscount(Long id){
        super(id);
    }

    public FreightDiscount(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull(message = "项目费用ID不能为空")
    @ExcelField(title = "项目费用ID")
    public Long getFreighttId() {
        return freighttId;
    }

    public void setFreighttId(Long freighttId) {
        this.freighttId = freighttId;
    }

    @NotNull(message = "总金额不能为空")
    @DecimalMin(value = "0.0",message = "总金额不能小于0")
    @ExcelField(title = "总金额")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @NotNull(message = "折扣不能为空")
    //@DecimalMin(value = "0.0",message = "折扣不能小于0")
    //@DecimalMax(value = "100.0",message = "折扣不能大于100" )
    @Range(min=1,max = 100,message = "折扣范围为 1 - 100")
    @ExcelField(title = "折扣")
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
