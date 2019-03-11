package com.dongpeng.schedulejob.task;

import com.dongpeng.schedulejob.entity.BaseTask;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class TestTask2 extends BaseTask {
    @Override
    public void run() {
        System.out.println("我是任务2");
    }
}
