package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.FreightItem;
import com.dongpeng.system.dao.FreightItemDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FreightItemService extends BaseCrudService<FreightItemDao,FreightItem> {
    @Override
    public String createDataScopeSql(FreightItem entity) {
        return null;
    }

    /**
     * 根据名称查找唯一
     * @param name 费用项名称
     * @return
     */
    public FreightItem getByName(String name){
        return findUniqueByProperty("item_name", name);
    }
}
