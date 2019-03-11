package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.entity.system.BlExamineCouponInfo;
import com.dongpeng.entity.system.BlExamineInfo;
import com.dongpeng.entity.system.BlPersonExamine;
import com.dongpeng.entity.system.BlPersonExamineInfo;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.dao.BlPersonExamineDao;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BlPersonExamineService extends BaseCrudService<BlPersonExamineDao,BlPersonExamine> {

    @Override
    public String createDataScopeSql(BlPersonExamine entity) {
        return null;
    }

    @Autowired
    private BlExamineInfoService blExamineInfoService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public int save(BlPersonExamine entity) throws OptimisticLockException {
        Dictionary in_review =  DictUtils.getByCode(Global.IN_REVIEW);
        entity.setApplyId(in_review.getId());
        entity.setApplyName(in_review.getDetailName());
        int rs = super.save(entity);

        //创建审核记录
        Dictionary unreview = DictUtils.getByCode(Global.UNREVIEW);
        Dictionary employeeApplyCouponExamine = DictUtils.getByCode(Global.USER_APPLY_EXAMINE);
        BlExamineInfo blExamineInfo = new BlExamineInfo();
        blExamineInfo.setBusinessId(entity.getId());
        blExamineInfo.setBusinessType(employeeApplyCouponExamine.getDetailName());
        blExamineInfo.setBusinessTypeId(employeeApplyCouponExamine.getId());
        blExamineInfo.setUserName(entity.getCreaterName());
        blExamineInfo.setUserId(entity.getCreaterId());
        blExamineInfo.setProcessEnd(false);
        blExamineInfo.setExamineResultId(unreview.getId());
        blExamineInfo.setExamineResultName(unreview.getDetailName());// 变为 待审核
        blExamineInfoService.save(blExamineInfo);

        User user1 = new User();
        user1.setPhone(entity.getPhone());
        User u = userService.getByPhone(user1);
        Preconditions.checkNotNull(u, "电话号码:" + entity.getPhone() + "的用户不存在");
        try {
            userService.updateExamineStatus(u.getId(), in_review,entity);
        } catch (Exception e) {
            throw new OptimisticLockException(this,u);
        }
        return rs;
    }

    @Transactional
    public int update(BlPersonExamine blPersonExamine) throws Exception {
        Preconditions.checkNotNull(blPersonExamine.getId(),"id 不能为空");
        BlPersonExamine old_model = get(blPersonExamine.getId());
        Preconditions.checkNotNull(old_model,"找不到对应数据");
        BeanUtils.copyBeanNotNull2Bean(blPersonExamine, old_model);
        int rs = super.save(old_model);
        return  rs;
    }

    /**
     * 更新状态
     * @param blPersonExamine
     * @return
     */
    @Transactional
    public int updateStatus(BlPersonExamine blPersonExamine){
        blPersonExamine.preUpdate();
        return dao.updateStatus(blPersonExamine);
    }


    /**
     * 查询人员审核记录
     * @param page
     * @param blPersonExamineInfo
     * @return
     */
    public Page<BlPersonExamineInfo> findListPersonExaminePage(Page<BlPersonExamineInfo> page, BlPersonExamineInfo blPersonExamineInfo){
        blPersonExamineInfo.setPage(page);
        page.setList(dao.findListPersonExaminePage(blPersonExamineInfo));
        return page;
    }


}
