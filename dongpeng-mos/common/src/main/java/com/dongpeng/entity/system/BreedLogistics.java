package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 品类物流表 实体类
 */
public class BreedLogistics extends DataEntity<BreedLogistics> {
    /** 品类ID **/
    //private Long breedId;
    /** 物流公司ID **/
    //private Long logisticsId;

    /** 品类 **/
    private Breed breed;

    /** 物流公司 **/
    private Logistics logistics;

    public BreedLogistics(){
        super();
        this.dataTableName="品类物流";
    }

    public BreedLogistics(Long id){
        super(id);
    }

    public BreedLogistics(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull(message = "品类不能为空")
    @ExcelField(title = "品类",value = "breed.breedName")
    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    @NotNull(message = "物流不能为空")
    @ExcelField(title = "物流",value = "logistics.logisticsName")
    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }
}
