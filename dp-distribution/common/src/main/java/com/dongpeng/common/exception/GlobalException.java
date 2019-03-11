package com.dongpeng.common.exception;

/**
 * 全局异常
 */
public class GlobalException extends Exception{
    private static final long serialVersionUID = -5701182284190108797L;

    /** 异常代码 **/
    private int code;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public GlobalException(GlobalException e) {
        super(e.getMessage());
        this.code = e.getCode();
    }

    /**
     *
     * @param message 异常消息
     */
    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }
}
