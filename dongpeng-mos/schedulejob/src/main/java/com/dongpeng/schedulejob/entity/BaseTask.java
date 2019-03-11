package com.dongpeng.schedulejob.entity;

import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.entity.system.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务工作基础类
 * <p>说明：禁止并发执行</p>
 * <p>说明：所有定时任务类都要继承该类</p>
 */
@DisallowConcurrentExecution
public abstract class BaseTask implements Job {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ScheduleJob scheduleJob = (ScheduleJob)jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        Date actionDate=new Date();
        //推送消息
        if(!(null==scheduleJob.getNotifyUser())){
            //TODO 推送到指定用户
           //systemInfoSocketHandler().sendMessageToUser("admin", "任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行");
        }else {
            //TODO 推送到全部用户
            //systemInfoSocketHandler().sendMessageToAllUsers("任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行");
        }

        run();

        logger.info("任务名称 = [" + scheduleJob.getTaskName() + "]"+ " 在 " + DateUtils.formatDateTime(actionDate) +" 时运行");
    }

    /**
     * 实际需要执行的任务逻辑
     */
    public abstract void run();
}
