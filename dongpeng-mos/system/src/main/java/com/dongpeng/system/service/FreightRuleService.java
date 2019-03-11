package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.FreightItem;
import com.dongpeng.entity.system.FreightRule;
import com.dongpeng.system.dao.FreightRuleDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FreightRuleService extends BaseCrudService<FreightRuleDao,FreightRule> {
    @Autowired
    private FreightItemService freightItemService;

    @Override
    public String createDataScopeSql(FreightRule entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(FreightRule freightRule) throws OptimisticLockException {
        //如果运费项ID不为空，运费项名称为空，自动查找运费项名称
        if(null!=freightRule.getItemId() && StringUtils.isBlank(freightRule.getItemName())){
            FreightItem freightItem=freightItemService.get(freightRule.getItemId());
            freightRule.setItemName(null==freightItem?"":freightItem.getItemName());
        }
        return super.save(freightRule);
    }
}
