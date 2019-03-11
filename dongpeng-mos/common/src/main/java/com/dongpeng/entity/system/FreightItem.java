package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 费用项表 实体类
 */
public class FreightItem extends DataEntity<FreightItem> {
    @DataName("费用项名称")
    private String itemName;

    public FreightItem(){
        super();
        this.dataTableName="费用项";
    }

    public FreightItem(Long id){
        super(id);
    }

    public FreightItem(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "费用项名称必须介于 2 和 10 之间")
    @ExcelField(title = "费用项名称")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
