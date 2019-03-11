package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

import java.util.Date;

/**
 * 组织服务范围
 */
public class OrgArea extends DataEntity<OrgArea> {
    private static final long serialVersionUID = 1L;
    //组织id
    private Long orgId;
    //组织名称
    private String orgName;
    //区域id
    private Long regionId;
    //区域名称
    private String regionName;

    public OrgArea (){

    }

    /**
     * @param orgId 组织id
     */
    public OrgArea(Long orgId) {
        this.orgId=orgId;
    }

    /**
     * @param orgId 组织id
     * @param regionId 区域id
     */
    public OrgArea(Long orgId, Long regionId) {
        this.orgId=orgId;
        this.regionId=regionId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }
}