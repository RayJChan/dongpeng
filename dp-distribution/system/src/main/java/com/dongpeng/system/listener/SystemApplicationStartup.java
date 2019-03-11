package com.dongpeng.system.listener;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.system.service.DictionaryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;

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
