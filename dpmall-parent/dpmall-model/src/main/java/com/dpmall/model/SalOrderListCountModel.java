package com.dpmall.model;

import java.io.Serializable;

/**
 * 留资订单列表数量
 *
 * @author cwj
 * @since 2018-05-05
 */
public class SalOrderListCountModel implements Serializable {

    /**
     * 待接单数量
     **/
    private String waittingCount;

    /**
     * 跟进中数量
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
