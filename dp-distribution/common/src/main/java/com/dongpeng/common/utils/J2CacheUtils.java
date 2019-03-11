package com.dongpeng.common.utils;

import com.dongpeng.common.config.Global;
import com.google.common.collect.Lists;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * j2cache 缓存工具类
 * <br/>
 * J2Cache —— 基于内存和 Redis 的两级 Java 缓存框架
 */
public class J2CacheUtils {
    private static final Logger logger = LoggerFactory.getLogger(J2CacheUtils.class);

    public static final  CacheChannel cache=SpringUtil.getBean(CacheChannel.class);

    /**
     * 写入缓存
     * @param region
     * @param key
     * @param value
     */
    public static void put(String region,String key, Object value) {
        cache.set(region, key, value);
    }

    /**
     * 获取缓存
     * @param region
     * @param key
     * @return
     */
    public static Object get(String region,String key) {
        return cache.get(region, key).getValue();
    }

    /**
     * 从缓存中移除
     * @param region
     * @param key
     */
    public static void remove(String region,String key) {
        Object obj = J2CacheUtils.cache.get(region, key).getValue();
        if (obj != null){
            cache.evict(region, key);
        }
    }

    /**
     * 清除缓存
     * @param region
     */
    public static void clear(String region) {
        J2CacheUtils.cache.clear(region);
    }

    /**
     * 获取缓存中的keys
     * @param region
     */
    public static List<String> getKeysByRegion(String region){
        return Lists.newArrayList(cache.keys(region)) ;
    }

    /**
     * 写入缓存 in default region
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        cache.set(Global.DEFAULT_REGION, key, value);
    }

    /**
     * 获取缓存 in default region
     * @param key
     * @return
     */
    public static Object get(String key) {
        return cache.get(Global.DEFAULT_REGION, key).getValue();
    }

    /**
     * 从缓存中移除 in default region
     * @param key
     */
    public static void remove(String key) {
        Object obj = J2CacheUtils.cache.get(Global.DEFAULT_REGION, key).getValue();
        if (obj != null){
            cache.evict(Global.DEFAULT_REGION, key);
        }
    }

    /**
     * 清除缓存 in default region
     */
    public static void clear() {
        J2CacheUtils.cache.clear(Global.DEFAULT_REGION);
    }

    /**
     * 获取缓存中的keys in default region
     */
    public static List<String> getKeysByRegion(){
        return Lists.newArrayList(cache.keys(Global.DEFAULT_REGION));
    }
}
