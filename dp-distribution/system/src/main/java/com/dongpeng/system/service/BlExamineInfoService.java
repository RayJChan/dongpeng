package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.entity.system.BlExamineCouponInfo;
import com.dongpeng.entity.system.BlExamineInfo;
import com.dongpeng.entity.system.BlPersonCouponExamine;
import com.dongpeng.entity.system.BlPersonCouponExamineInfo;
import com.dongpeng.entity.system.BlPersonExamine;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.dao.BlExamineInfoDao;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jdk.nashorn.internal.ir.EmptyNode;
import net.bytebuddy.asm.Advice;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BlExamineInfoService extends BaseCrudService<BlExamineInfoDao,BlExamineInfo> {

    private static final Logger logger = LoggerFactory.getLogger(BlExamineInfoService.class);

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private PersonCouponService personCouponService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlPersonCouponExamineService blPersonCouponExamineService;

    @Autowired
    private BlPersonExamineService blPersonExamineService;

    @Override
    public String createDataScopeSql(BlExamineInfo entity) {
        return null;
    }


    /**
     * 创建审核记录
     *
     * @param entity
     * @return
     * @throws OptimisticLockException
     */
    @Transactional
    @Override
    public int save(BlExamineInfo entity) throws OptimisticLockException {
        if(null==entity.getUserId()){
            entity.setCurrentUser();
            entity.setUserId(entity.getCurrentId());
            entity.setUserName(entity.getModifierName());
        }
        if(null==entity.getExamineResultId()){
            Dictionary dictionary = DictUtils.getByCode(Global.UNREVIEW);
            entity.setExamineResultId(dictionary.getId());
            entity.setExamineResultName(dictionary.getDetailName());
        }
        if(null==entity.getBusinessType()){
            Dictionary dictionary = DictUtils.getById(entity.getBusinessTypeId());
            if(null!=dictionary){
                entity.setBusinessType(dictionary.getDetailName());
            }
        }
        return super.save(entity);
    }

    @Transactional
    public int saveAndUpdateType(BlExamineInfo entity) throws OptimisticLockException {
        User user = userService.get(entity.getNextUserId());
        Preconditions.checkNotNull(user,"不存在该审核人用户");
        entity.setNextUserName(user.getUserName());
        int rs = this.save(entity);
        if (rs == 1) {

            Dictionary dictionary = DictUtils.getByCode(Global.IN_REVIEW);
            Dictionary coupon_examine = DictUtils.getByCode(Global.COUPON_EXAMINE);
            Dictionary user_apply_examine = DictUtils.getByCode(Global.USER_APPLY_EXAMINE);
            Dictionary employee_apply_coupon_examine = DictUtils.getByCode(Global.EMPLOYEE_APPLY_COUPON_EXAMINE);

            if (coupon_examine.getId().equals(entity.getBusinessTypeId())) {
                this.updateCouponType(entity.getBusinessId(),dictionary);
            } else if(user_apply_examine.getId().equals(entity.getBusinessTypeId())){
                //提交时候 用户审核表和用户表 状态都为审核中所以无需更新状态
            }else if(employee_apply_coupon_examine.getId().equals(entity.getBusinessTypeId())){
                 //不需要更新状态，前端提交申请后 默认为审核中状态
            }

        }
        return rs;
    }


    /**
     * 提交
     * @param blExamineInfo
     * @return
     * @throws OptimisticLockException
     */
    @Transactional
    public int submit(BlExamineInfo blExamineInfo) throws OptimisticLockException {
        //创建提交记录
        Dictionary dictionary = DictUtils.getByCode(Global.SUBMIT);
        BlExamineInfo subBlExamineInfo = new BlExamineInfo();
        subBlExamineInfo.setCurrentUser();
        subBlExamineInfo.setNextUserName("");
        subBlExamineInfo.setUserId(subBlExamineInfo.getUserId());
        subBlExamineInfo.setUserName(subBlExamineInfo.getModifierName());
        subBlExamineInfo.setExamineResultId(dictionary.getId());
        subBlExamineInfo.setExamineResultName(dictionary.getDetailName());
        subBlExamineInfo.setProcessEnd(Boolean.FALSE);
        subBlExamineInfo.setBusinessTypeId(blExamineInfo.getBusinessTypeId());
        subBlExamineInfo.setBusinessType(blExamineInfo.getBusinessType());
        subBlExamineInfo.setBusinessId(blExamineInfo.getBusinessId());
        subBlExamineInfo.setExamineRemark("提交");
        this.save(subBlExamineInfo);
        int rs= this.saveAndUpdateType(blExamineInfo);
        return rs;
    }


    /**
     * 审批通过后 自动创建优惠券 并领取
     * @return
     */
    public void createCoupon(Long businessbId) throws OptimisticLockException, InterruptedException {
        Dictionary reviewed_dic =  DictUtils.getByCode(Global.REVIEWED);//已审核
        Dictionary unreview_dic =  DictUtils.getByCode(Global.UNREVIEW);//未审核
        Dictionary coupon_status_inactive_dic =  DictUtils.getByCode(Global.COUPON_STATUS_INACTIVE);//已激活
        Dictionary coupon_type_manzhe_dic =  DictUtils.getByCode(Global.COUPON_TYPE_MANZHE);//满折券
        Dictionary coupon_receive_type_public =  DictUtils.getByCode(Global.COUPON_RECEIVE_TYPE_PUBLIC);//公共券
        Dictionary coupon_source_private_dic = DictUtils.getByCode(Global.COUPON_SOURCE_PRIVATE);//员工申请
        Dictionary coupon_use_type_myself_dic = DictUtils.getByCode(Global.COUPON_USE_TYPE_MYSELF);//为自己申请

        BlPersonCouponExamine blPersonCouponExamine = blPersonCouponExamineService.get(businessbId);
        User user = userService.get(blPersonCouponExamine.getPersonId());
        Coupon coupon = new Coupon();
        coupon.setCpnName(blPersonCouponExamine.getApplyProductName());
        coupon.setCpnIntro(blPersonCouponExamine.getApplyProductName());
        coupon.setReceiveBeginTime(blPersonCouponExamine.getCreateTime());
        coupon.setReceiveEndTime(DateUtils.addMonths(blPersonCouponExamine.getCreateTime(),1));
        coupon.setUseBeginTime(new Date());
        coupon.setUseEndTime(DateUtils.addMonths(new Date(),1));
        coupon.setExamineStatusName(unreview_dic.getDetailName());
        coupon.setExamineStatusId(unreview_dic.getId());
        coupon.setStatusId(coupon_status_inactive_dic.getId());
        coupon.setStatusName(coupon_status_inactive_dic.getDetailName());
        coupon.setReceiveTypeId(coupon_receive_type_public.getId());
        coupon.setReceiveTypeName(coupon_receive_type_public.getDetailName());
        coupon.setUseNote("1. 不可以与其他优惠公用；\n".concat("2. 分享给朋友，朋友到店消费您也可以获取奖励；"));
        coupon.setUseCondition(BigDecimal.ZERO);//使用门槛
        coupon.setIsShare(Boolean.TRUE);
        coupon.setRankName(Global.LOWRANK);
        coupon.setRankId(Long.valueOf(Global.LOWRANK_ID));
        coupon.setTypeName(coupon_type_manzhe_dic.getDetailName());
        coupon.setTypeId(coupon_type_manzhe_dic.getId());
        coupon.setCpnNum(null==blPersonCouponExamine.getApplyNum() ? 1 : blPersonCouponExamine.getApplyNum());
        coupon.setScorePercent("0");
        coupon.setScore(0);
        coupon.setWriteoffNum(0);
        coupon.setWriteoffPercent(BigDecimal.ZERO);
        coupon.setPayAmount(BigDecimal.ZERO);
        coupon.setReceivePercent(BigDecimal.ZERO);
        coupon.setFaceValue(Double.toString(blPersonCouponExamine.getDiscount()));
        coupon.setOrgId(blPersonCouponExamine.getUseOrgId());
        coupon.setOrgName(blPersonCouponExamine.getUseOrgName());
        coupon.setSourceName(coupon_source_private_dic.getDetailName());
        coupon.setSourceId(coupon_source_private_dic.getId());//优惠券来源
        couponService.addCoupon(coupon);
        //更改优惠券待审核状态
        coupon.setExamineStatusId(reviewed_dic.getId());
        coupon.setExamineStatusName(reviewed_dic.getDetailName());
        couponService.examineStatusChange(coupon);

        //更改申请状态
        blPersonCouponExamine.setCouponId(coupon.getId());
        blPersonCouponExamine.setCouponName(coupon.getCpnName());
        blPersonCouponExamine.setApplyStatusId(reviewed_dic.getId());
        blPersonCouponExamine.setApplyStatusName(reviewed_dic.getDetailName());
        blPersonCouponExamineService.updateStatus(blPersonCouponExamine);

        //激活优惠券
        couponService.couponActive(couponService.get(coupon.getId()));

        //如果是为自己领取
        if(blPersonCouponExamine.getUseTypeId().equals(coupon_use_type_myself_dic.getId())){
            //为用户指定领取优惠券
            personCouponService.receiveCoupon(coupon.getId(),user,user);
        }

    }

    /**
     * 更改优惠券状态
     * @param id
     * @param dictionary
     */
    @Transactional
    public void updateCouponType(Long id, Dictionary dictionary){
        //todo 更改优惠券状态
        Coupon coupon = couponService.get(id);
        coupon.setExamineStatusId(dictionary.getId());
        coupon.setExamineStatusName(dictionary.getDetailName());
        couponService.examineStatusChange(coupon);
    }

    /**
     * 更新用户审核状态
     * @param blPersonExamine
     * @param dictionary
     * @throws Exception
     */
    @Transactional
    public void updateUserExamineStatus(BlPersonExamine blPersonExamine, Dictionary dictionary) throws OptimisticLockException {
        User user1 = new User();
        user1.setPhone(blPersonExamine.getPhone());
        User u = userService.getByPhone(user1);
        Preconditions.checkNotNull(u, "电话号码:" + blPersonExamine.getPhone() + "的用户不存在");
        try {
            userService.updateExamineStatus(u.getId(), dictionary,blPersonExamine);
        } catch (Exception e) {
            throw new OptimisticLockException(this,u);
        }
    }



    /**
     * 分页查询优惠券审批记录
     * @param page
     * @param blExamineCouponInfo
     * @return
     */
    public Page<BlExamineCouponInfo> findPageCouponExamine(Page<BlExamineCouponInfo> page, BlExamineCouponInfo blExamineCouponInfo){
        blExamineCouponInfo.setPage(page);
        page.setList(dao.findPageCouponExamine(blExamineCouponInfo));
        return page;
    }


    /**
     * 审核信息
     * @param entity
     * @return
     * @throws Exception
     */
    @Transactional
    public ResponseResult couponCheck(BlExamineInfo entity,int type) throws Exception {
        Preconditions.checkNotNull(entity.getExamineRemark(),"审批意见不能为空");
        Dictionary dict_unreview = DictUtils.getByCode(Global.UNREVIEW);//未审核
        BlExamineInfo blExamineInfoTemp =  dao.get(entity.getId());//根据审核ID 获取到待审批信息
        Preconditions.checkNotNull(blExamineInfoTemp,"id 无效");
        Preconditions.checkArgument(dict_unreview.getId().equals(blExamineInfoTemp.getExamineResultId()),"不为【未审核】状态,无法正常审核");

        //更新上一条数据状态
        if(type==1){
            Dictionary reviewedDic = DictUtils.getByCode(Global.REVIEWED);//已审核
            //审核通过
            if(null!=entity.getNextUserId() && entity.getProcessEnd()==false ){//如果有下级 需要新建 审批明细
                BlExamineInfo newBlExamineInfo = new BlExamineInfo();
                newBlExamineInfo.setUserId(blExamineInfoTemp.getNextUserId());
                if(null!=blExamineInfoTemp.getNextUserId()){
                    User u = userService.get(blExamineInfoTemp.getNextUserId());
                    if(null!=u){
                        newBlExamineInfo.setUserName(u.getUserName());
                    }
                }
                newBlExamineInfo.setNextUserId(entity.getNextUserId());
                if(null!=entity.getNextUserId()){
                    User u = userService.get(entity.getNextUserId());
                    if(null!=u){
                        newBlExamineInfo.setNextUserName(u.getUserName());
                    }
                }
                newBlExamineInfo.setExamineResultId(dict_unreview.getId());//插入字典未审核状态
                newBlExamineInfo.setExamineResultName(dict_unreview.getDetailName());
                newBlExamineInfo.setBusinessId(blExamineInfoTemp.getBusinessId());
                newBlExamineInfo.setBusinessType(blExamineInfoTemp.getBusinessType());
                newBlExamineInfo.setBusinessTypeId(blExamineInfoTemp.getBusinessTypeId());
                newBlExamineInfo.setBusinessDesc("");
                newBlExamineInfo.setProcessEnd(Boolean.FALSE);
                this.save(newBlExamineInfo);
            }else{
                blExamineInfoTemp.setProcessEnd(Boolean.TRUE);//没有选下级审批人 默认自动结束流程
                if(blExamineInfoTemp.getBusinessTypeId().equals(DictUtils.getByCode(Global.COUPON_EXAMINE).getId())){
                    //改变  审批通过状态 。优惠券必须手动激活
                    Coupon coupon = couponService.get(blExamineInfoTemp.getBusinessId());
                    coupon.setExamineStatusId(reviewedDic.getId());
                    coupon.setExamineStatusName(reviewedDic.getDetailName());
                    couponService.examineStatusChange(coupon);

                }else if(blExamineInfoTemp.getBusinessTypeId().equals(DictUtils.getByCode(Global.EMPLOYEE_APPLY_COUPON_EXAMINE).getId())){

                    this.createCoupon(blExamineInfoTemp.getBusinessId());

                }else if(blExamineInfoTemp.getBusinessTypeId().equals(DictUtils.getByCode(Global.USER_APPLY_EXAMINE).getId())){
                    BlPersonExamine blPersonExamine = blPersonExamineService.get(blExamineInfoTemp.getBusinessId());
                    blPersonExamine.setApplyName(reviewedDic.getDetailName());
                    blPersonExamine.setApplyId(reviewedDic.getId());
                    blPersonExamineService.updateStatus(blPersonExamine);
                    this.updateUserExamineStatus(blPersonExamine,reviewedDic);
                }
            }

            blExamineInfoTemp.setExamineResultName(reviewedDic.getDetailName());//审核结果名称 通过
            blExamineInfoTemp.setExamineResultId(reviewedDic.getId());//审核结果ID

        }else if(type==0){

            Dictionary review_failure = DictUtils.getByCode(Global.REVIEW_FAILURE);//审核不通过状态
            blExamineInfoTemp.setExamineResultName(review_failure.getDetailName());//审核结果名称 通过 不通过
            blExamineInfoTemp.setExamineResultId(review_failure.getId());//审核结果ID

            if(blExamineInfoTemp.getBusinessTypeId().equals(DictUtils.getByCode(Global.COUPON_EXAMINE).getId())){
                //更新优惠券状态 审批不通过状态
                Coupon coupon = couponService.get(blExamineInfoTemp.getBusinessId());
                coupon.setExamineStatusId(review_failure.getId());
                coupon.setExamineStatusName(review_failure.getDetailName());
                couponService.examineStatusChange(coupon);
            }else if(blExamineInfoTemp.getBusinessTypeId().equals(DictUtils.getByCode(Global.EMPLOYEE_APPLY_COUPON_EXAMINE).getId())){
                //更改员工申请优惠券  审批不通过状态
                BlPersonCouponExamine blPersonCouponExamine = new BlPersonCouponExamine();
                blPersonCouponExamine.setId(blExamineInfoTemp.getBusinessId());
                blPersonCouponExamine.setApplyStatusName(review_failure.getDetailName());
                blPersonCouponExamine.setApplyStatusId(review_failure.getId());
                blPersonCouponExamineService.updateStatus(blPersonCouponExamine);
            }else if(blExamineInfoTemp.getBusinessTypeId().equals(DictUtils.getByCode(Global.USER_APPLY_EXAMINE).getId())){
                BlPersonExamine blPersonExamine = blPersonExamineService.get(blExamineInfoTemp.getBusinessId());
                blPersonExamine.setApplyName(review_failure.getDetailName());
                blPersonExamine.setApplyId(review_failure.getId());
                blPersonExamineService.update(blPersonExamine);
                this.updateUserExamineStatus(blPersonExamine,review_failure);
            }

        }

        blExamineInfoTemp.setCurrentUser();
        blExamineInfoTemp.setExamineRemark(entity.getExamineRemark());//审核内容
        blExamineInfoTemp.preUpdate();
        int rs = dao.update(blExamineInfoTemp);

        return  1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("系统个错误，审核失败");
    }


    /**
     * 根据 businessId 获取审批记录信息
     * @param businessId
     * @return
     */
    public  List<BlExamineInfo>  findBlExamineInfoByBusinessId(Long businessId){

        return dao.findBlExamineInfoByBusinessId(businessId);
    }


    /**
     * 获取优惠券审批完成的
     */
    public Page<BlExamineCouponInfo> getCoponExamineFinished(int pageNo,int pageSize){
            Dictionary  review_failure = DictUtils.getByCode(Global.REVIEW_FAILURE);
            Dictionary  reviewed = DictUtils.getByCode(Global.REVIEWED);
            Page<BlExamineCouponInfo> page = new Page<BlExamineCouponInfo>(pageNo, pageSize);
            BlExamineCouponInfo blExamineCouponInfo = new BlExamineCouponInfo();
            blExamineCouponInfo.setPage(page);
            blExamineCouponInfo.setCurrentUser();
            blExamineCouponInfo.setNextUserId(blExamineCouponInfo.getCurrentId());//只查询当前用户
            blExamineCouponInfo.setBusinessTypeId(DictUtils.getByCode(Global.COUPON_EXAMINE).getId());
            blExamineCouponInfo.setExamineResultIdList(Arrays.asList(review_failure.getId(),reviewed.getId()));
            page.setList(dao.findPageCouponExamine(blExamineCouponInfo));
        return page;
    }


    /**
     * 获取优惠券 待审批
     */
    public Page<BlExamineCouponInfo> getCouponExamineUnFinish(int pageNo,int pageSize){
        Dictionary  unreview = DictUtils.getByCode(Global.UNREVIEW);
        BlExamineCouponInfo blExamineCouponInfo = new BlExamineCouponInfo();
        Page<BlExamineCouponInfo> page = new Page<BlExamineCouponInfo>(pageNo, pageSize);
        blExamineCouponInfo.setPage(page);
        blExamineCouponInfo.setCurrentUser();
        blExamineCouponInfo.setNextUserId(blExamineCouponInfo.getCurrentId());//只查询当前用户
        blExamineCouponInfo.setBusinessTypeId(DictUtils.getByCode(Global.COUPON_EXAMINE).getId());
        blExamineCouponInfo.setExamineResultId(unreview.getId());
        page.setList(dao.findPageCouponExamine(blExamineCouponInfo));
        return page;
    }


    /**
     * 聚合数据
     * 获取 个人优惠券审批信息和个人优惠券申请审批信息 待审核信息
     * @return
     */
    public Page<Map<String,String>> getMyExamineUnfinish(Page<Map<String,String>> page){
        long count = 0;
        Page<BlExamineCouponInfo> page1 = this.getCouponExamineUnFinish(page.getPageNo(),page.getPageSize());
        List<Map<String,String>> list = Lists.newLinkedList();
        List<BlExamineCouponInfo> blExamineCouponInfoList = page1.getList();
        if(CollectionUtils.isNotEmpty(blExamineCouponInfoList)){
            for(BlExamineCouponInfo model : blExamineCouponInfoList){
                Map<String,String> map = Maps.newHashMap();
                map.put("couponName",model.getCpnName());
                map.put("userName",model.getCreaterName());
                map.put("serviceArea",model.getServiceArea());
                map.put("examineResultName",model.getExamineResultName());
                map.put("examineResultId",model.getExamineResultId().toString());
                map.put("couponId",model.getBusinessId().toString());
                map.put("examineId",model.getId().toString());
                map.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yyyy-MM-dd"));
                map.put("businessTypeId",model.getBusinessTypeId().toString());
                map.put("businessType",model.getBusinessType());
                list.add(map);
            }
            count += page1.getCount();
        }
        Page<BlPersonCouponExamineInfo> page2 = blPersonCouponExamineService.getPersonApplyCouponExamineUnFinish(page.getPageNo(),page.getPageSize());
        List<BlPersonCouponExamineInfo> blPersonCouponExamineInfoList = page2.getList();
        if(CollectionUtils.isNotEmpty(blPersonCouponExamineInfoList)){
            for(BlPersonCouponExamineInfo model : blPersonCouponExamineInfoList){
                Map<String,String> map = Maps.newHashMap();
                map.put("couponName","打".concat(model.getDiscount().toString()).concat("折"));
                map.put("examineId",model.getExamineInfoId().toString());
                map.put("couponId","");
                map.put("userName",model.getCreaterName());
                map.put("serviceArea",model.getServiceArea());
                map.put("examineResultId",model.getExamineResultId().toString());
                map.put("examineResultName",model.getExamineResultName());
                map.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yyyy-MM-dd"));
                map.put("businessTypeId",model.getBusinessTypeId().toString());
                map.put("businessType",model.getBusinessType());
                map.put("businessId",model.getBusinessId().toString());
                list.add(map);
            }
            count+= page2.getCount();
        }
        page.setCount(count);
        page.setList(list);
        return page;
    }

    /**
     * 聚合数据
     * 获取 个人优惠券审批信息和个人优惠券申请审批信息 已审核信息
     * @return
     */
    public Page<Map<String,String>> getMyExamineFinished(Page<Map<String,String>> page){
        long count = 0;
        Page<BlExamineCouponInfo> page1 = this.getCoponExamineFinished(page.getPageNo(),page.getPageSize());
        List<Map<String,String>> list = Lists.newLinkedList();
        List<BlExamineCouponInfo> blExamineCouponInfoList = page1.getList();
        if(CollectionUtils.isNotEmpty(blExamineCouponInfoList)){
            for(BlExamineCouponInfo model : blExamineCouponInfoList){
                Map<String,String> map = Maps.newHashMap();
                map.put("couponName",model.getCpnName());
                map.put("businessId",model.getBusinessId().toString());
                map.put("userName",model.getCreaterName());
                map.put("serviceArea",model.getServiceArea());
                map.put("examineResultId",model.getExamineResultId().toString());
                map.put("examineResultName",model.getExamineResultName());
                map.put("couponId",model.getBusinessId().toString());
                map.put("examineId",model.getId().toString());
                map.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yyyy-MM-dd"));
                map.put("businessTypeId",model.getBusinessTypeId().toString());
                map.put("businessType",model.getBusinessType());
                list.add(map);
            }
            count += page1.getCount();
        }
        Page<BlPersonCouponExamineInfo> page2 = blPersonCouponExamineService.getPersonApplyCouponExamineFinished(page.getPageNo(),page.getPageSize());
        List<BlPersonCouponExamineInfo> blPersonCouponExamineInfoList = page2.getList();
        if(CollectionUtils.isNotEmpty(blPersonCouponExamineInfoList)){
            for(BlPersonCouponExamineInfo model : blPersonCouponExamineInfoList){
                Map<String,String> map = Maps.newHashMap();
                map.put("couponName","打".concat(model.getDiscount().toString()).concat("折"));
                map.put("examineId",model.getExamineInfoId().toString());
                map.put("couponId",model.getCouponId()==null? "" : model.getCouponId().toString());
                map.put("businessId",model.getBusinessId().toString());
                map.put("userName",model.getCreaterName());
                map.put("serviceArea",model.getServiceArea());
                map.put("examineResultId",model.getExamineResultId().toString());
                map.put("examineResultName",model.getExamineResultName());
                map.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yyyy-MM-dd"));
                map.put("businessTypeId",model.getBusinessTypeId().toString());
                map.put("businessType",model.getBusinessType());
                list.add(map);
            }
            count+= page2.getCount();
        }
        page.setCount(count);
        page.setList(list);
        return page;
    }

    /**
     * 优惠券作废触发，删除 未审核数据
     * @param businessId
     * @return
     */
    @Transactional
    public int deleteToggleByBisIdAndExResultId(Long businessId){
        Dictionary  unreview = DictUtils.getByCode(Global.UNREVIEW);
        Map<String,Object> map = Maps.newHashMap();
        map.put("businessId",businessId);
        map.put("deleteFlag",Boolean.TRUE);
        map.put("examineResultIdList",Arrays.asList(unreview.getId()));
        return dao.deleteToggleByBisIdAndExResultId(map);
    }

}
