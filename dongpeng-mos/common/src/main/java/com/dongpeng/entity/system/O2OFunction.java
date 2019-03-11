package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

import java.util.Date;

public class O2OFunction extends DataEntity<O2OFunction> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String clientAutomaticDistribute;

    private String materialAutomaticDistribute;

    private String privilegeAutomaticDistribute;

    private String clientDistributeCharge;

    private String privilegeAutomaticCharge;

    private String clientNotTime;

    private String privilegeNotTime;

    private String materialNotTime;

    private String clientRevokeTime;

    private String privilegeRevokeTime;

    private String materialRevokeTime;

    public O2OFunction(){
        super();
        this.dataTableName="o2o功能设置";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientAutomaticDistribute() {
        return clientAutomaticDistribute;
    }

    public void setClientAutomaticDistribute(String clientAutomaticDistribute) {
        this.clientAutomaticDistribute = clientAutomaticDistribute == null ? null : clientAutomaticDistribute.trim();
    }

    public String getMaterialAutomaticDistribute() {
        return materialAutomaticDistribute;
    }

    public void setMaterialAutomaticDistribute(String materialAutomaticDistribute) {
        this.materialAutomaticDistribute = materialAutomaticDistribute == null ? null : materialAutomaticDistribute.trim();
    }

    public String getPrivilegeAutomaticDistribute() {
        return privilegeAutomaticDistribute;
    }

    public void setPrivilegeAutomaticDistribute(String privilegeAutomaticDistribute) {
        this.privilegeAutomaticDistribute = privilegeAutomaticDistribute == null ? null : privilegeAutomaticDistribute.trim();
    }

    public String getClientDistributeCharge() {
        return clientDistributeCharge;
    }

    public void setClientDistributeCharge(String clientDistributeCharge) {
        this.clientDistributeCharge = clientDistributeCharge == null ? null : clientDistributeCharge.trim();
    }

    public String getPrivilegeAutomaticCharge() {
        return privilegeAutomaticCharge;
    }

    public void setPrivilegeAutomaticCharge(String privilegeAutomaticCharge) {
        this.privilegeAutomaticCharge = privilegeAutomaticCharge == null ? null : privilegeAutomaticCharge.trim();
    }

    public String getClientNotTime() {
        return clientNotTime;
    }

    public void setClientNotTime(String clientNotTime) {
        this.clientNotTime = clientNotTime == null ? null : clientNotTime.trim();
    }

    public String getPrivilegeNotTime() {
        return privilegeNotTime;
    }

    public void setPrivilegeNotTime(String privilegeNotTime) {
        this.privilegeNotTime = privilegeNotTime == null ? null : privilegeNotTime.trim();
    }

    public String getMaterialNotTime() {
        return materialNotTime;
    }

    public void setMaterialNotTime(String materialNotTime) {
        this.materialNotTime = materialNotTime == null ? null : materialNotTime.trim();
    }

    public String getClientRevokeTime() {
        return clientRevokeTime;
    }

    public void setClientRevokeTime(String clientRevokeTime) {
        this.clientRevokeTime = clientRevokeTime == null ? null : clientRevokeTime.trim();
    }

    public String getPrivilegeRevokeTime() {
        return privilegeRevokeTime;
    }

    public void setPrivilegeRevokeTime(String privilegeRevokeTime) {
        this.privilegeRevokeTime = privilegeRevokeTime == null ? null : privilegeRevokeTime.trim();
    }

    public String getMaterialRevokeTime() {
        return materialRevokeTime;
    }

    public void setMaterialRevokeTime(String materialRevokeTime) {
        this.materialRevokeTime = materialRevokeTime == null ? null : materialRevokeTime.trim();
    }
}