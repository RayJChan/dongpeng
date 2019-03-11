package com.dongpeng.schedulejob.task;

import com.dongpeng.schedulejob.entity.BaseTask;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class TestTask extends BaseTask {
    @Override
    public void run() {
        System.out.println("这是测试任务TestTask。");
    }
}
