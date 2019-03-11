package com.dongpeng.common.db.cache;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.utils.J2CacheUtils;
import org.apache.ibatis.cache.Cache;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于j2cache实现了 MyBatis 的缓存接口
 */
public class J2CacheAdapter implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;

    public J2CacheAdapter(String id) {
        if (id == null)
            id = Global.DEFAULT_REGION;
        this.id = id;
    }

    public void setId(String id) {
        if (id == null)
            id = Global.DEFAULT_REGION;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        J2CacheUtils.cache.set(this.id, key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        return J2CacheUtils.cache.get(this.id, key.toString()).getValue();
    }

    @Override
    public Object removeObject(Object key) {
        Object obj = J2CacheUtils.cache.get(this.id, key.toString()).getValue();
        if (obj != null)
            J2CacheUtils.cache.evict(this.id, (String)key);
        return obj;
    }

    @Override
    public void clear() {
        J2CacheUtils.cache.clear(this.getId());
    }

    @Override
    public int getSize() {
        Collection<String> keys = J2CacheUtils.cache.keys(this.getId());
        return keys != null ? keys.size() : 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
