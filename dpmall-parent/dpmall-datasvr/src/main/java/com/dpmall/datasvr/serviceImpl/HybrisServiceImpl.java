package com.dpmall.datasvr.serviceImpl;

import com.dpmall.common.HybrisUtils;
import com.dpmall.common.RedisUtils;
import com.dpmall.datasvr.api.IHybrisService;
import com.dpmall.db.dao.HybrisDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service(value = "hybrisService")
public class HybrisServiceImpl implements IHybrisService {

    @Autowired
    private HybrisUtils hybrisUtils;

    @Override
    public String getHybrisToken() {

        return hybrisUtils.getHybrisToken();
    }


}