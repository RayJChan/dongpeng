package com.dongpeng.common.db.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础父service
 */
@Transactional(readOnly = true)
public abstract class BaseService<T extends DataEntity<T>> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 构建数据范围过滤sql
     * <p>说明：如不需过滤，返回 null 即可</p>
     * @param entity
     * @return
     */
    public abstract String createDataScopeSql(T entity);

    /**
     * 数据范围过滤
     * @param entity 当前过滤的实体类
     */
    public void dataScopeFilter(T entity) {

        entity.setCurrentUser();

        // 如果是超级管理员，则不过滤数据
        if (null!=entity.getCurrentId() && Global.ADMINISTRATOR_ID==entity.getCurrentId()) {
            return;
        }

        // 数据范围
        entity.setDataScope(createDataScopeSql(entity));
    }
}
