package com.dpmall.web.controller.form;

import com.dpmall.err.ErrorCode;

/**
 * 通用结果
 * @author river
 * @date 2017-07-18
 */
public class Response {
	/**返回码*/
    public Integer resultCode = ErrorCode.SUCCESS;
    
    /**结果信息*/
    public String message;
    
    /**结果数据*/
    public Object data;
}
