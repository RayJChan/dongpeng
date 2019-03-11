package com.dpmall.model.prePay;

import java.io.Serializable;

/**
 * 列表数量
 *
 * @author cwj
 * @since 2018-05-05
 */
public class OthersPrepayListCountModel implements Serializable {

    /**
     * 待接单数量
     **/
    private String waittingCount;

    /**
     * 已接单数量
     **/
    private String fllowingCount;

    /**
     * 已完成数量
     **/
    private String completedCount;

    public String getWaittingCount() {
        return waittingCount;
    }

    public void setWaittingCount(String waittingCount) {
        this.waittingCount = waittingCount;
    }

    public String getFllowingCount() {
        return fllowingCount;
    }

    public void setFllowingCount(String fllowingCount) {
        this.fllowingCount = fllowingCount;
    }

    public String getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(String completedCount) {
        this.completedCount = completedCount;
    }
}
