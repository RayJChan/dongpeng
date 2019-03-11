package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;

/**
 * 操作记录日志 实体类
 */
public class OperatingRecord extends DataEntity<OperatingRecord> {
    private static final long serialVersionUID = 1L;

    private Long relevanceId;     //关联订单id
    private String relevanceCode; //关联订单单号
    private String handledescribe;//操作描述
    private String handlerecord;  //操作记录

    public OperatingRecord(){
        super();
        this.dataTableName="操作记录日志";
    }

    public OperatingRecord(Long id){
        super(id);
    }

    public OperatingRecord(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    public OperatingRecord(Long relevanceId,String relevanceCode,String handledescribe,String handlerecord){
        super();
        this.relevanceId=relevanceId;
        this.relevanceCode=relevanceCode;
        this.handledescribe=handledescribe;
        this.handlerecord=handlerecord;
    }

    public OperatingRecord(Long relevanceId,String relevanceCode){
        super();
        this.relevanceId=relevanceId;
        this.relevanceCode=relevanceCode;
    }

    public OperatingRecord(String handledescribe,String handlerecord){
        super();
        this.handledescribe=handledescribe;
        this.handlerecord=handlerecord;
    }

    @ExcelField(title = "关联订单id")
    public Long getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Long relevanceId) {
        this.relevanceId = relevanceId;
    }

    @ExcelField(title = "关联订单单号")
    public String getRelevanceCode() {
        return relevanceCode;
    }

    public void setRelevanceCode(String relevanceCode) {
        this.relevanceCode = relevanceCode;
    }

    @ExcelField(title = "操作描述")
    public String getHandledescribe() {
        return handledescribe;
    }

    public void setHandledescribe(String handledescribe) {
        this.handledescribe = handledescribe;
    }

    @ExcelField(title = "操作记录")
    public String getHandlerecord() {
        return handlerecord;
    }

    public void setHandlerecord(String handlerecord) {
        this.handlerecord = handlerecord;
    }
}
