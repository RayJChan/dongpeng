package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 运费项目表 实体类
 */
public class FreightProject extends DataEntity<FreightProject> {
    private static final long serialVersionUID = 1L;
    @DataName("运费项目名称")
    private String projectName;
    /** 品类ID **/
    private Long categoryId;
    @DataName("品类名称")
    private String categoryName;
    @DataName("有效期开始时间")
    private Date validityStart;
    @DataName("有效期结束时间")
    private Date validityEnd;

    public FreightProject(){
        super();
        this.dataTableName="运费项目";
    }

    public FreightProject(Long id){
        super(id);
    }

    public FreightProject(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "运费项目名称必须介于 2 和 20 之间")
    @ExcelField(title = "运费项目名称")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @NotNull
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @ExcelField(title = "品类名称")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "有效期开始时间不能为空")
    @ExcelField(title = "有效期开始时间")
    public Date getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(Date validityStart) {
        this.validityStart = validityStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "有效期结束时间不能为空")
    @ExcelField(title = "有效期结束时间")
    public Date getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(Date validityEnd) {
        this.validityEnd = validityEnd;
    }
}
