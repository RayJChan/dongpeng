package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * 职级表 实体类
 */
public class BlRank  extends TreeEntity<BlRank>  {

    @DataName("职级名称")
    private  String rankName; //职级名称

    @DataName("是否启用")
    private Boolean isEnable; //是否启用

    public BlRank(){
        super();
        this.dataTableName="职级";
    }

    public BlRank(Long id){
        super(id);
    }

    public BlRank(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 1, max = 10, message = "职级名称长度必须介于 1 和 10 之间")
    @ExcelField(title = "职级名称")
    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    @NotNull
    @ExcelField(title = "是否启用")
    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}
