package com.dongpeng.common.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 封装返回信息类
 */
public class ResponseResult implements Serializable {

    private int code = 200;//结果代码
    private String msg = "";// 提示信息
    private LinkedHashMap<String, Object> body = new LinkedHashMap();//封装json的map

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        if(null!=data){
            put("data", data);
        }
    }

    public ResponseResult(){

    }
    /**
     * 快速构建 无返回数据 的成功返回信息
     * @return
     */
    public static ResponseResult ok() {
        return new ResponseResult(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),null);
    }

    /**
     * 快速构建 有返回数据 的成功返回信息
     * @param data 需要返回的数据
     * @return
     */
    public static ResponseResult ok(Object data) {
        return new ResponseResult(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),data);
    }

    /**
     * 快速构建失败返回信息
     * @param code 错误码 <p>建议从{@link ResponseCode}中获取</p>
     * @param message 错误信息 <p>可以从{@link ResponseCode}中获取，也可以自定义</p>
     * @return
     */
    public static ResponseResult fail(int code,String message) {
        return new ResponseResult(code,message,null);
    }

    /**
     * 快速构建参数验证失败返回信息
     * @param message 错误信息 <p>可以从{@link ResponseCode#PARAM_ERROR}中获取，也可以自定义</p>
     * @return
     */
    public static ResponseResult failByParam(String message){
        return  new ResponseResult(ResponseCode.PARAM_ERROR.getCode(), message, null);
    }

    /**
     * 快速构建业务处理失败返回信息
     * @param message 错误信息 <p>可以从{@link ResponseCode#BUSINESS_FAILE}中获取，也可以自定义</p>
     * @return
     */
    public static ResponseResult failByBusiness(String message){
        return  new ResponseResult(ResponseCode.BUSINESS_FAILE.getCode(), message, null);
    }


    public void put(String key, Object value) {//向json中添加属性，访问请调用data.map.key
        body.put(key, value);
    }

    public void remove(String key) {
        body.remove(key);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LinkedHashMap<String, Object> getBody() {
        return body;
    }

    public void setBody(LinkedHashMap<String, Object> body) {
        this.body = body;
    }
}
