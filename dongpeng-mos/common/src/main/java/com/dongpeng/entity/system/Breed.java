package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 品类表 实体类
 */
public class Breed extends TreeEntity<Breed> {
    private static final long serialVersionUID = 1L;
    @DataName("品类名称")
    private String breedName;
    @DataName("品类标识")
    private String breedCode;
    //上级id
   /* private Long upId;
    @DataName("上级品类")
    private String upName;*/

    public Breed(){
        super();
        this.dataTableName="品类";
    }

    public Breed(Long id){
        super(id);
    }

    public Breed(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 20, message = "品类名称必须介于 2 和 20 之间")
    @ExcelField(title = "品类名称",sort = 10)
    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "品类标识必须介于 2 和 10 之间")
    @ExcelField(title = "品类标识",sort = 20)
    public String getBreedCode() {
        return breedCode;
    }

    public void setBreedCode(String breedCode) {
        this.breedCode = breedCode;
    }

   /* public Long getUpId() {
        return upId;
    }

    public void setUpId(Long upId) {
        this.upId = upId;
    }

    @ExcelField(title = "上级品类",sort = 30)
    public String getUpName() {
        return upName;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }*/
}
