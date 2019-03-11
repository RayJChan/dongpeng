package com.dongpeng.common.controller;

import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.StringPlusUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 公用异常统一处理
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    @ResponseBody
    public ResponseResult bindException(BindException exception) {
        logger.error("参数绑定异常: ", exception);
        return ResponseResult.failByParam("参数绑定异常: "+ (null==exception.getCause()?exception.getMessage():exception.getCause().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult serverErrorException(Exception exception) {
        logger.error("服务器异常: ", exception);
        return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), (null==exception.getCause()?exception.getMessage():exception.getCause().getMessage()));
    }
}
