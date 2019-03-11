package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.CouponShareLink;
import com.dongpeng.system.dao.CouponDao;
import com.dongpeng.system.dao.CouponShareLinkDao;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponShareLinkService extends BaseCrudService<CouponShareLinkDao, CouponShareLink> {
    @Override
    public String createDataScopeSql(CouponShareLink entity) {
        return null;
    }

    public CouponShareLink getParent(CouponShareLink link){
        Preconditions.checkNotNull(link,"错误的分享链路");
        List<CouponShareLink> list = dao.getParent(link);
        return list.isEmpty()?null:list.get(0);
    }

    public boolean add(CouponShareLink couponShareLink) throws OptimisticLockException {
        Preconditions.checkNotNull(couponShareLink);
        Preconditions.checkNotNull(couponShareLink.getFromId());
        Preconditions.checkNotNull(couponShareLink.getToId());
        Preconditions.checkNotNull(couponShareLink.getCpnId());
        if (couponShareLink.getFromId().equals(couponShareLink.getToId())){
            return false;
        }
        if (dao.isExisted(couponShareLink).isEmpty()){
            return save(couponShareLink)==1;
        }
        return false;
    }
}
