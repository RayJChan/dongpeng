package com.dongpeng.common.db.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.entity.system.OperatingRecord;

/**
 * 操作记录dao
 * <p>因各分支项目都需要日志记录，故把该dao放到 common-db 项目中</p>
 */
@MyBatisDao
public interface OperatingRecordDao extends BaseCrudDao<OperatingRecord> {
}
