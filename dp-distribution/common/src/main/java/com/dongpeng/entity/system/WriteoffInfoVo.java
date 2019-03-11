package com.dongpeng.entity.system;

import java.math.BigDecimal;

/**

* @Description:    java类作用描述
 * 核销情况

* @Author:         xc

* @CreateDate:     2018/11/16 13:30

* @UpdateUser:     xc

* @UpdateDate:     2018/11/16 13:30

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public class WriteoffInfoVo {
    private Long writeoffCount;

    private BigDecimal writeoffDealAmount;

    public Long getWriteoffCount() {
        return writeoffCount;
    }

    public void setWriteoffCount(Long writeoffCount) {
        this.writeoffCount = writeoffCount;
    }

    public BigDecimal getWriteoffDealAmount() {
        return writeoffDealAmount;
    }

    public void setWriteoffDealAmount(BigDecimal writeoffDealAmount) {
        this.writeoffDealAmount = writeoffDealAmount;
    }
}
