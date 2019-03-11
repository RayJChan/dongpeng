package com.dongpeng.common.entity;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.utils.SnowflakeIdUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 树形Entity公用字段父类
 */
public abstract class TreeEntity<T> extends DataEntity<T>  {

    @DataName("所有父类ID")
    protected String parentIds;          //所有父类ID

    @DataName("上级id")
    protected Long parentId; //上级id

    @DataName("上级名称")
    protected String parentName; //上级名称

    public TreeEntity() {
        super();
    }

    public TreeEntity(Long id) {
        super(id);
    }


    @JsonIgnore
    @Length(min=1, max=2000)
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @ExcelField(title = "上级名称",sort = 0)
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
