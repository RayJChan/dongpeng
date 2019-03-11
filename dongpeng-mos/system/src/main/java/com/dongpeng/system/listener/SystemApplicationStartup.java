package com.dongpeng.system.listener;

import com.dongpeng.system.service.DictionaryService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * System项目启动监听器
 */
public class SystemApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static int initDictCacheNum=0;//initDictCacheNum执行次数
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //启动项目，加载字典,写入缓存
        DictionaryService dictionaryService=event.getApplicationContext().getBean(DictionaryService.class);
        if(initDictCacheNum==0){
            //因为ContextRefreshedEvent事件会多次触发，故只执行第一次
            dictionaryService.initDictCache();
            initDictCacheNum++;
        }

    }
}
