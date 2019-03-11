package com.dongpeng.common.db.exception;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.exception.GlobalException;

/**
 * 乐观锁异常
 */
public class OptimisticLockException extends GlobalException {

    private static final long serialVersionUID = 4066485840242028945L;

    private BaseCrudService baseCrudService;
    private DataEntity entity;

    public BaseCrudService getBaseCrudService() {
        return baseCrudService;
    }

    public void setBaseCrudService(BaseCrudService baseCrudService) {
        this.baseCrudService = baseCrudService;
    }

    public DataEntity getEntity() {
        return entity;
    }

    public void setEntity(DataEntity entity) {
        this.entity = entity;
    }

    public OptimisticLockException(BaseCrudService baseCrudService, DataEntity entity){
        super(entity.getModifierName()+" 用户正在操作该数据，请稍后再试", ResponseCode.BUSINESS_FAILE.getCode());
        this.baseCrudService=baseCrudService;
        this.entity=entity;
    }


    public OptimisticLockException(String message){
        super(message, ResponseCode.BUSINESS_FAILE.getCode());
    }

    public OptimisticLockException(GlobalException e) {
        super(e);
    }


}
