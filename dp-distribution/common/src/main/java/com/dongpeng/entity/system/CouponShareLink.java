package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

import java.util.Date;

public class CouponShareLink extends DataEntity<CouponShareLink> {
    private String cpnName;

    private Long cpnId;

    private Long fromId;

    private String fromName;

    private Long toId;

    private String toName;

    private String fromIds;

    public String getCpnName() {
        return cpnName;
    }

    public void setCpnName(String cpnName) {
        this.cpnName = cpnName == null ? null : cpnName.trim();
    }

    public Long getCpnId() {
        return cpnId;
    }

    public void setCpnId(Long cpnId) {
        this.cpnId = cpnId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName == null ? null : fromName.trim();
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName == null ? null : toName.trim();
    }

    public String getFromIds() {
        return fromIds;
    }

    public void setFromIds(String fromIds) {
        this.fromIds = fromIds == null ? null : fromIds.trim();
    }
}