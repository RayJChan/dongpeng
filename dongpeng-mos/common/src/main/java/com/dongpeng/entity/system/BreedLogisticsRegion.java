package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 品类物流区域表 实体类
 */
public class BreedLogisticsRegion extends DataEntity<BreedLogisticsRegion> {
    /** 品类物流ID **/
    private Long breedLogisticsId;
    /** 区域ID **/
    private Long regionId;
    @DataName("省")
    private String province;
    @DataName("市")
    private String city;
    @DataName("区")
    private String district;
    @DataName("物流百分比")
    private Double percent;

    public BreedLogisticsRegion(){
        super();
        this.dataTableName="品类物流区域";
    }

    public BreedLogisticsRegion(Long id){
        super(id);
    }

    public BreedLogisticsRegion(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull(message = "品类物流ID不能为空")
    @ExcelField(title = "品类物流ID",sort = 10)
    public Long getBreedLogisticsId() {
        return breedLogisticsId;
    }

    public void setBreedLogisticsId(Long breedLogisticsId) {
        this.breedLogisticsId = breedLogisticsId;
    }

    @NotNull(message = "区域ID不能为空")
    @ExcelField(title = "区域ID",sort = 20)
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Length(min = 2,max = 20,message = "省份必须介于 2 和 20 之间")
    @ExcelField(title = "省",sort = 30)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Length(min = 2,max = 20,message = "市必须介于 2 和 20 之间")
    @ExcelField(title = "市",sort = 40)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Length(min = 2,max = 20,message = "区必须介于 2 和 20 之间")
    @ExcelField(title = "区",sort = 50)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @NotNull(message = "物流百分比不能为空")
    @Range(min=1,max = 100,message = "物流百分比范围为 1 - 100")
    @ExcelField(title = "物流百分比",sort = 60)
    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
