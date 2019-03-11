package com.dongpeng.schedulejob.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.ScheduleJob;

@MyBatisDao
public interface ScheduleJobDao extends BaseCrudDao<ScheduleJob> {
    /**
     * 物理删除定时任务
     * @param scheduleJob
     * @return
     */
    public int delete(ScheduleJob scheduleJob);

}
