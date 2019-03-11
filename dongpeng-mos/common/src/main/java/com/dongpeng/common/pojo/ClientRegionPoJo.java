package com.dongpeng.common.pojo;


import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 经销商负责客户显示信息
 */
public class ClientRegionPoJo implements Serializable {
    private Long id;

    private Long ClientId;

    @DataName("客户编号")
    private String clientCode;

    @DataName("客户名称")
    private String clientName;

    @DataName("接单比例")
    private int receiptProportion;

    /** 实体数据表名称 **/
    @JsonIgnore
    protected String dataTableName;

    /** 导出总表 **/
    private static final int GROUPS_INDEX_ALL=1;
    public ClientRegionPoJo(){
        super();
        this.dataTableName="现货派单客户";
    }

    public Long getClientId() {
        return ClientId;
    }

    public void setClientId(Long clientId) {
        ClientId = clientId;
    }

    @ExcelField(title = "接单比例",sort = 50,groups = {GROUPS_INDEX_ALL})
    public int getReceiptProportion() {
        return receiptProportion;
    }

    public void setReceiptProportion(int receiptProportion) {
        this.receiptProportion = receiptProportion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ExcelField(title = "客户编号",sort = 50,groups = {GROUPS_INDEX_ALL})
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @ExcelField(title = "客户名称",sort = 50,groups = {GROUPS_INDEX_ALL})
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDataTableName() {
        return dataTableName;
    }

    public void setDataTableName(String dataTableName) {
        this.dataTableName = dataTableName;
    }
}
