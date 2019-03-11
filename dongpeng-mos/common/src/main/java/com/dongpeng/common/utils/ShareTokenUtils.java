package com.dongpeng.common.utils;

import com.alibaba.fastjson.JSON;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.ShareToken;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class ShareTokenUtils {

    private static final RedissonClient redisson = SpringUtil.getBean(Redisson.class);
    private static String sign(ShareToken shareToken){
        return DigestsUtils.string2MD5(new Gson().toJson(shareToken)+Global.SHARE_SECRET);
    }

    public static String getToken(ShareToken shareToken){
        String sign = sign(shareToken);
        RBucket<ShareToken> rBucket = redisson.getBucket(sign);
        rBucket.set(shareToken);
        return sign;
    }

    public static ShareToken cheakToken(String token) throws IllegalArgumentException{
        RBucket<ShareToken> rBucket = redisson.getBucket(token);
        Preconditions.checkState(rBucket.isExists(),"错误的token");
        return rBucket.get();
    }
}
