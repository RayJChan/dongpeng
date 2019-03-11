package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.OrgArea;

@MyBatisDao
public interface OrgAreaDao extends BaseCrudDao<OrgArea> {

    /**
     * 根据组织id物理删除组织服务范围
     * @param orgId 封装 orgId
     * @return
     */
    public int deleteByOrgId(Long orgId);
}
