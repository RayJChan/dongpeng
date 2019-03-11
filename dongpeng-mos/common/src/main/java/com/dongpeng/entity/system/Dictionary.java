package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 字典项表 实体类
 */
public class Dictionary extends DataEntity<Dictionary> {
    private static final long serialVersionUID = 1L;
    @DataName("字典项名称")
    private String dictionaryName; //字典项名称
    @DataName("字典项明细名称")
    private String detailName;   //字典项明细名称
    private String detailCode;   //字典项明细code

    public Dictionary(){
        super();
        this.dataTableName="字典";
    }

    public Dictionary(Long id){
        super(id);
    }

    public Dictionary(Long id, boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "字典项名称必须介于 2 和 20 之间")
    @ExcelField(title = "字典项名称")
    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @NotNull
    @Length(min = 2, max = 255, message = "字典项明细名称必须介于 2 和 255 之间")
    @ExcelField(title = "字典项明细名称")
    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @NotNull
    @Length(min = 2, max = 64, message = "字典项明细code必须介于 2 和 64 之间")
    @ExcelField(title = "字典项明细code")
    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    @ExcelField(title = "是否有效",type = 1,sort = 1050)
    @Override
    @NotNull
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag =  deleteFlag;
    }


}
