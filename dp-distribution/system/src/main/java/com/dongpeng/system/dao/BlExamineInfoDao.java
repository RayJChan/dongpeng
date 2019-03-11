package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.BlExamineCouponInfo;
import com.dongpeng.entity.system.BlExamineInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface BlExamineInfoDao extends BaseCrudDao<BlExamineInfo> {

    public List<BlExamineCouponInfo> findPageCouponExamine( BlExamineCouponInfo entity);

    public  List<BlExamineInfo>  findBlExamineInfoByBusinessId(Long businessId);

    public  int  deleteToggleByBisIdAndExResultId(Map<String,Object> map);

}
