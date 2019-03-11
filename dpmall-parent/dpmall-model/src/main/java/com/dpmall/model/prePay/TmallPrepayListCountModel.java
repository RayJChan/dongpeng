package com.dpmall.model.prePay;

import java.io.Serializable;

/**
 * 列表数量
 *
 * @author cwj
 * @since 2018-05-05
 */
public class TmallPrepayListCountModel implements Serializable {

    /**
     * 待接单数量
     **/
    private String waittingCount;

    /**
     * 已接单数量
     **/
    private String accepetedCount;

    /**
     * 已核销数量
     **/
    private String writeOffedCount;

    /**
     * 已关闭数量
     **/
    private String closedCount;


    public String getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(String closedCount) {
        this.closedCount = closedCount;
    }

    public String getWaittingCount() {
        return waittingCount;
    }

    public void setWaittingCount(String waittingCount) {
        this.waittingCount = waittingCount;
    }

    public String getAccepetedCount() {
        return accepetedCount;
    }

    public void setAccepetedCount(String accepetedCount) {
        this.accepetedCount = accepetedCount;
    }

    public String getWriteOffedCount() {
        return writeOffedCount;
    }

    public void setWriteOffedCount(String writeOffedCount) {
        this.writeOffedCount = writeOffedCount;
    }
}
