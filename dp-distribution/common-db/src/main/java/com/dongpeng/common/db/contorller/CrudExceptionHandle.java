package com.dongpeng.common.db.contorller;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 数据库操作异常统一处理
 */
@ControllerAdvice
public class CrudExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(CrudExceptionHandle.class);

    /**
     * 乐观锁异常
     */
    @ExceptionHandler(OptimisticLockException.class)
    @ResponseBody
    public ResponseResult optimisticLockException(OptimisticLockException exception) {
        logger.warn("乐观锁异常: ", exception);
        if(null!=exception.getBaseCrudService() && null!=exception.getEntity()){
            if(exception.getBaseCrudService().compareVersion(exception.getEntity())){
                return ResponseResult.failByBusiness("更新失败");
            }else{
                return ResponseResult.failByBusiness(exception.getMessage());
            }
        }
        return ResponseResult.fail(exception.getCode(), exception.getMessage());
    }


}
