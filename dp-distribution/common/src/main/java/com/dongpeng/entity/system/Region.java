package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.entity.TreeEntity;
import com.dongpeng.common.utils.PinyinUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 省市区表 实体类
 */
public class Region extends TreeEntity<Region> {

    /*@DataName("省")
    private String province;
    @DataName("市")
    private String city;
    @DataName("区")
    private String district;*/
    @DataName("层级")
    private Integer level; //省=1 市=2 区=3
    @DataName("热度")
    private Integer hotDegreet;
    @DataName("名称")
    private String name;
    @DataName("拼音")
    private String pinyin;

    /******* 以下字段跟数据库无关 *******/
    private List<Region> children;
    private String label;

    /** 导出总表 **/
    private static final int GROUPS_INDEX_ALL=1;

    public Region(){
        super();
        this.dataTableName="省市区";
    }

    public Region(Long id){
        super(id);
    }

    public Region(Long id,boolean deleteFlag){
        this(id);
        this.deleteFlag=deleteFlag;
    }

    @Override
    public void preInsert() {
        super.preInsert();
        regionLevelAndPinyin();
        this.hotDegreet=this.hotDegreet==null?0:this.hotDegreet;//热度默认为0
    }

    @Override
    public void preUpdate() {
        super.preUpdate();
        regionLevelAndPinyin();
    }

    /**
     * 设置level和拼音
     */
    private void regionLevelAndPinyin(){
        if(null==this.parentId){
            this.level=0;
        }else {
            this.level=getParentIds().split(",").length;
        }
        this.pinyin=PinyinUtils.getPinyinToFirstUpper(this.name);
    }

   /* @NotNull
    @Length(min = 2,max = 20,message = "省份必须介于 2 和 20 之间")
    @ExcelField(title = "省",sort = 10,groups = {GROUPS_INDEX_ALL})
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    @Length(min = 2,max = 20,message = "市必须介于 2 和 20 之间")
    @ExcelField(title = "市",sort = 20,groups = {GROUPS_INDEX_ALL})
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Length(min = 2,max = 20,message = "区必须介于 2 和 20 之间")
    @ExcelField(title = "区",sort = 30,groups = {GROUPS_INDEX_ALL})
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }*/

    @ExcelField(title = "层级",sort = 40,type = 1,groups = {GROUPS_INDEX_ALL})
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @ExcelField(title = "热度",sort = 50,groups = {GROUPS_INDEX_ALL})
    public Integer getHotDegreet() {
        return hotDegreet;
    }

    public void setHotDegreet(Integer hotDegreet) {
        this.hotDegreet = hotDegreet;
    }

    @JsonProperty
    public String getPinyin() {
        return pinyin;
    }
    @JsonIgnore
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @JsonProperty
    public List getChildren() {
        return children;
    }

    @JsonIgnore
    public void setChildren(List children) {
        this.children = children;
    }

    @JsonProperty
    public String getLabel() {
        return label;
    }

    @JsonIgnore
    public void setLabel(String label) {
        this.label = label;
    }

    @NotNull
    @Length(min = 2,max = 64,message = "名称必须介于 2 和 64 之间")
    @ExcelField(title = "名称",sort = 10,groups = {GROUPS_INDEX_ALL})
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
