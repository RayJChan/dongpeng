package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * 运费计算规则表 实体类
 */
public class FreightRule extends DataEntity<FreightRule> {
    /** 运费项ID **/
    private Long itemId;
    @DataName("费用名称")
    private String itemName;
    @DataName("区间公式一")
    private String sectionOne;
    @DataName("区间阀值")
    private Double sectionValue;
    @DataName("区间公式二")
    private String sectionTwo;
    @DataName("最低值")
    private Double minValue;
    @DataName("最高值")
    private Double maxValue;

    public FreightRule(){
        super();
        this.dataTableName="运费计算规则";
    }

    public FreightRule(Long id){
        super(id);
    }

    public FreightRule(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull(message = "运费项ID不能为空")
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @ExcelField(title = "费用名称")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @NotNull
    @Length(min = 1, max = 50, message = "区间公式一必须介于 1 和 50 之间")
    @ExcelField(title = "区间公式一")
    public String getSectionOne() {
        return sectionOne;
    }

    public void setSectionOne(String sectionOne) {
        this.sectionOne = sectionOne;
    }

    @NotNull(message = "区间阀值不能为空")
    @DecimalMin(value = "0.0",message = "区间阀值不能小于0")
    @ExcelField(title = "区间阀值")
    public Double getSectionValue() {
        return sectionValue;
    }

    public void setSectionValue(Double sectionValue) {
        this.sectionValue = sectionValue;
    }

    @NotNull
    @Length(min = 1, max = 50, message = "区间公式二必须介于 1 和 50 之间")
    @ExcelField(title = "区间公式二")
    public String getSectionTwo() {
        return sectionTwo;
    }

    public void setSectionTwo(String sectionTwo) {
        this.sectionTwo = sectionTwo;
    }

    @NotNull(message = "最低值不能为空")
    @DecimalMin(value = "0.0",message = "最低值不能小于0")
    @ExcelField(title = "最低值")
    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    @DecimalMin(value = "0.0",message = "最高值不能小于0")
    @ExcelField(title = "最高值")
    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
}
