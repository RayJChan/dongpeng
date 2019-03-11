package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.BlPersonCouponExamine;
import com.dongpeng.entity.system.BlPersonExamine;
import com.dongpeng.entity.system.BlPersonExamineInfo;

import java.util.List;

@MyBatisDao
public interface BlPersonExamineDao extends BaseCrudDao<BlPersonExamine> {

    public int updateStatus(BlPersonExamine blPersonExamine);

    public  List<BlPersonExamineInfo> findListPersonExaminePage(BlPersonExamineInfo blPersonExamineInfo);

}
