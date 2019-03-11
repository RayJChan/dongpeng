package com.dpmall.db.bean;


public class SendMsgEntiy {


    /**
     *
     */
    public  String code;
    /**
     * 短信模板
     */
    public  String msgtemplate;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMsgtemplate() {
        return msgtemplate;
    }

    public void setMsgtemplate(String msgtemplate) {
        this.msgtemplate = msgtemplate;
    }
}
