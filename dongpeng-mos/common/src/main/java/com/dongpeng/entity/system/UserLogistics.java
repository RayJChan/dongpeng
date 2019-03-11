package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 用户物流表 实体类
 */
public class UserLogistics extends DataEntity<UserLogistics> {
    private static final long serialVersionUID = 1L;
    private Long userId; //用户id
    private Long logisticsId; //物流id

    public UserLogistics(){
        super();
    }

    public UserLogistics(Long userId, Long logisticsId){
        super();
        this.userId=userId;
        this.logisticsId=logisticsId;
    }

    public UserLogistics(Long userId){
        super();
        this.userId=userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

}
