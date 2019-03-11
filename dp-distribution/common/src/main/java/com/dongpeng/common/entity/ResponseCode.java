package com.dongpeng.common.entity;

/**
 * 处理结果枚举
 */
public enum  ResponseCode {
    /** 处理成功 **/
    SUCCESS(200,"处理成功"),

    /** 业务处理失败 **/
    BUSINESS_FAILE(208,"业务处理失败"),

    /** 参数错误 **/
    PARAM_ERROR(400,"参数错误"),

    /** token 过期 **/
    TOKEN_TIMEOUT(402,"token过期"),

    /** 禁止访问 **/
    NO_AUTH(403,"禁止访问"),

    /** 资源没找到 **/
    NOT_FOUND(404,"资源没找到"),

    /** 服务器错误 **/
    SERVER_ERROR(500,"服务器错误");

    private int code;
    private String msg;

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg=msg;
    }
}
