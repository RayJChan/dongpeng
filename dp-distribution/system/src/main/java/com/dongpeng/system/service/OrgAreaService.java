package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.OrgArea;
import com.dongpeng.system.dao.OrgAreaDao;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrgAreaService extends BaseCrudService<OrgAreaDao,OrgArea> {
    @Override
    public String createDataScopeSql(OrgArea entity) {
        return null;
    }

    /**
     * 增加组织服务范围（批量增加）
     * @param orgAreas 组织范围列表
     * @return
     */
    @Transactional(readOnly = false)
    public int addOrgAreaList(List<OrgArea> orgAreas){
        //如果新区域数据id有值，则删除该组织所有旧区域
        if(orgAreas!=null && !orgAreas.isEmpty()){
            deleteByOrgId(orgAreas.get(0).getOrgId());
        }else{
            return 0;
        }
        return insertBatch(orgAreas);
    }

    /**
     * 根据组织id物理删除组织服务范围
     * @param orgId 封装 orgId
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteByOrgId(Long orgId){
        return dao.deleteByOrgId(orgId);
    }
}
