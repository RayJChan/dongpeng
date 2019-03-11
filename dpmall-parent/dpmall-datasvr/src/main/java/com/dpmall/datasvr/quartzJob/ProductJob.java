package com.dpmall.datasvr.quartzJob;

import com.alibaba.fastjson.JSON;
import com.dpmall.datasvr.api.IProductService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务执行类
 */
@Component
public class ProductJob  {
    @Autowired
    private IProductService productService;

    @Scheduled(cron = "0 0 3 * * ?")   //每天凌晨3点运行        */3 * * * * ?

    public void execute() {
        Thread thread = new Thread(){
            public void run(){
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
                System.out.println("\n"+ JSON.toJSONString(productService.getProductCode())+"\n");
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
            }
        };

        thread.start();



    }
}
