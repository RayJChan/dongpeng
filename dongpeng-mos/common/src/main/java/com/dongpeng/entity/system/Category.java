package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 品类表 实体类
 */
public class Category extends DataEntity<Category> {

    @DataName("品类编码")
    private String categoryCode;

    @DataName("一级品类")
    private String firstCategoryName;

    @DataName("二级品类")
    private String secodCategoryName;

    @DataName("三级品类")
    private String thirdCategoryName;

    public Category(){
        super();
        this.dataTableName="品类";
    }

    public Category(Long id){
        super(id);
    }

    public Category(Long id, boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }


    @NotNull
    @Length(min = 2, max = 20, message = "品类编码不允许为空")
    @ExcelField(title = "品类编码",sort = 10)
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "一级品类不能为空")
    @ExcelField(title = "一级品类",sort = 10)
    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }
    @NotNull
    @Length(min = 2, max = 20, message = "二级品类不能为空")
    @ExcelField(title = "二级品类",sort = 10)
    public String getSecodCategoryName() {
        return secodCategoryName;
    }

    public void setSecodCategoryName(String secodCategoryName) {
        this.secodCategoryName = secodCategoryName;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "三级品类不能为空")
    @ExcelField(title = "三级品类",sort = 10)
    public String getThirdCategoryName() {
        return thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }


}
