package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.BlRank;

import java.util.Map;

@MyBatisDao
public interface BlRankDao  extends BaseCrudDao<BlRank> {

    public int updateParentNameByParentId(Map<String,Object> param);

    public int updateEnable(BlRank blRank);

}
