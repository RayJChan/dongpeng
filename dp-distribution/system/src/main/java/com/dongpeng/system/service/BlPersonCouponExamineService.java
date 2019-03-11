package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.entity.system.BlCouponUsePerson;
import com.dongpeng.entity.system.BlExamineCouponInfo;
import com.dongpeng.entity.system.BlExamineInfo;
import com.dongpeng.entity.system.BlPersonCouponExamine;
import com.dongpeng.entity.system.BlPersonCouponExamineInfo;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.MyApplyCoupon;
import com.dongpeng.entity.system.Organization;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.dao.BlPersonCouponExamineDao;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.dongpeng.common.config.Global.REVIEW_FAILURE;

@Service
@Transactional(readOnly = true)
public class BlPersonCouponExamineService extends BaseCrudService<BlPersonCouponExamineDao,BlPersonCouponExamine> {

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private BlExamineInfoService blExamineInfoService;

    @Autowired
    private BlCouponUsePersonService blCouponUsePersonService;

    @Override
    public String createDataScopeSql(BlPersonCouponExamine entity) {
        return null;
    }

    @Autowired
    public OrganizationService organizationService;


    @Override
    @Transactional
    public int save(BlPersonCouponExamine entity) throws OptimisticLockException {
        entity.setCurrentUser();
        User user = userService.get(entity.getCurrentId());
        Organization organization = organizationService.get(entity.getUseOrgId());
        entity.setPersonId(entity.getCurrentId());
        entity.setPersonName(user.getUserName());
        entity.setApplyOrgId(user.getOrgId());//申请人组织
        entity.setApplyOrgName(user.getOrgName());
        if(null!=organization){
            entity.setServiceArea(organization.getServiceArea());
        }
        if(null!=entity.getExamineUserId()){
            User examineUser = userService.get(entity.getExamineUserId());
            Preconditions.checkNotNull(examineUser,"审批人不存在");
            entity.setExamineUserName(examineUser.getUserName());
        }
        if(null!=entity.getUseTypeId()){
            Dictionary dictionary = DictUtils.getById(entity.getUseTypeId());
            entity.setUseTypeName(dictionary.getDetailName());
        }
        if(null==entity.getApplyNum()){
            entity.setApplyNum(1);
        }

        Dictionary in_review =  DictUtils.getByCode(Global.IN_REVIEW);
        entity.setApplyStatusId(in_review.getId());
        entity.setApplyStatusName(in_review.getDetailName());
        blCouponUsePersonService.deleteUsers(entity.getId());
        int rs = super.save(entity);
        List<BlCouponUsePerson> listPerson = entity.getBlCouponUsePersonList();
        if(CollectionUtils.isNotEmpty(listPerson)){
            for(BlCouponUsePerson person : listPerson){
                person.setExamineCouponId(entity.getId());
            }
            blCouponUsePersonService.insertBatch(listPerson);
        }



        //创建审核记录
        Dictionary unreview = DictUtils.getByCode(Global.UNREVIEW);
        Dictionary employeeApplyCouponExamine = DictUtils.getByCode(Global.EMPLOYEE_APPLY_COUPON_EXAMINE);
        BlExamineInfo blExamineInfo = new BlExamineInfo();
        blExamineInfo.setBusinessId(entity.getId());
        blExamineInfo.setBusinessType(employeeApplyCouponExamine.getDetailName());
        blExamineInfo.setBusinessTypeId(employeeApplyCouponExamine.getId());
        blExamineInfo.setUserName(entity.getPersonName());
        blExamineInfo.setUserId(entity.getPersonId());
        blExamineInfo.setNextUserId(entity.getExamineUserId());
        blExamineInfo.setNextUserName(entity.getExamineUserName());
        blExamineInfo.setProcessEnd(false);
        blExamineInfo.setExamineResultId(unreview.getId());
        blExamineInfo.setExamineResultName(unreview.getDetailName());// 变为 待审核

        blExamineInfoService.submit(blExamineInfo);

       // blExamineInfoService.save(blExamineInfo);

        return rs;
    }

    /**
     * 更改状态和更新优惠券ID
     * @param blPersonCouponExamine
     * @return
     */
    @Transactional
    public int updateStatus(BlPersonCouponExamine blPersonCouponExamine){
        blPersonCouponExamine.setCurrentUser();
        blPersonCouponExamine.preUpdate();
        return dao.updateStatus(blPersonCouponExamine);
    }

    public Page<Map<String,Object>> findMyApplyCouponPage(Page<Map<String,Object>> page, MyApplyCoupon myApplyCoupon){
        myApplyCoupon.setPage(page);
        List<Map<String,Object>> listMap = Lists.newArrayList();
        List<MyApplyCoupon> list = dao.findMyApplyCouponPage(myApplyCoupon);
        if(CollectionUtils.isNotEmpty(list)){
            for(MyApplyCoupon coupon : list){
                Map<String,Object> map = Maps.newHashMap();
                String useBeginTime = "";
                String useEndTime = "";
                String couponName = "".concat(String.valueOf(coupon.getDiscount())).concat("折");
                if(null==coupon.getCouponId()){
                    useBeginTime = DateFormatUtils.format(coupon.getUseBeginTime(),"yyyy-MM-dd");
                    useEndTime = DateFormatUtils.format(coupon.getUseEndTime(),"yyyy-MM-dd");
                    couponName = coupon.getCpnName();
                }else{
                    useBeginTime = DateFormatUtils.format(coupon.getCreateTime(),"yyyy-MM-dd");
                    useEndTime = DateFormatUtils.format(DateUtils.addDays(coupon.getCreateTime(),30),"yyyy-MM-dd");
                }

                map.put("id",coupon.getId());
                map.put("couponId",coupon.getCouponId());
                map.put("applyStatusName",coupon.getApplyStatusName());
                map.put("couponName",couponName);
                map.put("useBeginTime",useBeginTime);
                map.put("useEndTime",useEndTime);
                map.put("useOrgName",coupon.getUseOrgName());
                map.put("useOrgId",coupon.getUseOrgId());
                listMap.add(map);
            }
        }
        page.setList(listMap);
        return page;
    }

    /**
     * 员工申请优惠券审核列表
     * @param page
     * @param blPersonCouponExamineInfo
     * @return
     */
    public Page<BlPersonCouponExamineInfo> findPersonExamineInCouponPage(Page<BlPersonCouponExamineInfo> page,BlPersonCouponExamineInfo blPersonCouponExamineInfo){
        blPersonCouponExamineInfo.setPage(page);
        List<BlPersonCouponExamineInfo> list = dao.findPersonAndExamineInfoPage(blPersonCouponExamineInfo);
        page.setList(list);
        return page;
    }


    /**
     * 根据 couponId 获取 优惠券申请表信息
     * @param couponId
     * @return
     */
    public BlPersonCouponExamine getPersonCouponExamineByCouponId(Long couponId){

        return dao.getPersonCouponExamineByCouponId(couponId);
    }


    /**
     * 我的发起 未完成
     * @param page
     * @return
     */
    public Page<Map<String,Object>> findMyApplyCouponUnfinished(Page<Map<String,Object>> page){
        User user = userService.getCurrentUser();
        Map<String,Object> param = Maps.newHashMap();
        param.put("page",page);
        param.put("personId",user.getId());
        param.put("reviewFailureStatus",DictUtils.getByCode(REVIEW_FAILURE).getId());
        param.put("inReviewStatus",DictUtils.getByCode(Global.IN_REVIEW).getId());
        param.put("DEL_FLAG_NORMAL", Boolean.FALSE);
        List<Map<String,Object>> list = dao.findMyApplyCouponUnfinished(param);
        page.setList(list);
        return page;
    }

    /**
     * 我的发起 已完成
     * @param page
     * @return
     */
    public Page<Map<String,Object>> findMyApplyCouponFinished(Page<Map<String,Object>> page){
        User user = userService.getCurrentUser();
        Map<String,Object> param = Maps.newHashMap();
        param.put("page",page);
        param.put("personId",user.getId());
        param.put("applyStatusId",DictUtils.getByCode(Global.REVIEWED).getId());
        param.put("DEL_FLAG_NORMAL", Boolean.FALSE);
        List<Map<String,Object>> list = this.findMyApplyCoupon(page,param);
        page.setList(list);
        return page;
    }

    /**
     * 参数封装
     * @param page
     * @param param
     * @return
     */
    private  List<Map<String,Object>> findMyApplyCoupon(Page<Map<String,Object>> page,Map<String,Object> param){
        List<Map<String,Object>> list = dao.findMyApplyCouponUnfinished(param);
        list.stream().forEach(item -> {
            Float f = NumberUtils.toFloat(item.get("discount").toString(),0f);
            String   useBeginTime = DateFormatUtils.format((Date) item.get("createTime"),"yyyy-MM-dd");
            String   useEndTime = DateFormatUtils.format(DateUtils.addDays((Date) item.get("createTime"),30),"yyyy-MM-dd");
            item.put("useBeginTime",useBeginTime);
            item.put("useEndTime",useEndTime);
        });
        return list;
    }


    /**
     * 获取 我的发起 优惠券详情
     * @param id
     * @return
     */
    public Map<String,Object> getMyApplyCouponDetail(Long id){
        Long businessTypeId = null;
       // Dictionary reviewed = DictUtils.getByCode(Global.REVIEWED);//审核通过
       // Dictionary review_failure = DictUtils.getByCode(REVIEW_FAILURE);//审核不通过
       // Dictionary unreview = DictUtils.getByCode(Global.UNREVIEW);//未审核
        Dictionary coupon_examine = DictUtils.getByCode(Global.COUPON_EXAMINE);
        Dictionary employee_apply_coupon_examine = DictUtils.getByCode(Global.EMPLOYEE_APPLY_COUPON_EXAMINE);
        Dictionary submit = DictUtils.getByCode(Global.SUBMIT);
        List<Map<String,String>> blExamineInfoMap = Lists.newLinkedList();
        Map<String,Object> resultMap = Maps.newHashMap();
        //获取审批记录信息
        List<BlExamineInfo> blExamineInfoList =  blExamineInfoService.findBlExamineInfoByBusinessId(id);
        if(CollectionUtils.isNotEmpty(blExamineInfoList)){
            int size = blExamineInfoList.size();
            businessTypeId = blExamineInfoList.get(0).getBusinessTypeId();

            for (int i = 0; i < size; i++) {
                Map<String,String> map = Maps.newLinkedHashMap();
                String examineResultName = "";
                BlExamineInfo model = blExamineInfoList.get(i);
                if(submit.getId().longValue()==model.getExamineResultId().longValue()){
                    Map<String,String> beginMap = Maps.newHashMap();
                    beginMap.put("rankName",model.getUserName());
                    beginMap.put("userName",model.getUserName());
                    beginMap.put("examineResultName","发起审批");
                    beginMap.put("examineResultId",model.getExamineResultId().toString());
                    beginMap.put("examineRemark","");
                    beginMap.put("processEnd",model.getProcessEnd().toString());
                    beginMap.put("modifyTime",DateFormatUtils.format(model.getCreateTime(),"yy/MM/dd HH:mm"));
                    beginMap.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yy/MM/dd HH:mm"));
                    blExamineInfoMap.add(beginMap);
                    continue;
                }
                map.put("examineRemark",model.getExamineRemark());//备注
                map.put("userName",model.getNextUserName());//审核人
                map.put("examineResultName",model.getExamineResultName());//审核结果
                map.put("examineResultId",model.getExamineResultId().toString());
                map.put("rankName",model.getRankName());//职级
                map.put("processEnd",model.getProcessEnd().toString());//职级
                map.put("modifyTime",DateFormatUtils.format(model.getModifyTime(),"yy/MM/dd HH:mm"));
                map.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yy/MM/dd HH:mm"));
                blExamineInfoMap.add(map);
                if(model.getProcessEnd()){
                    Map<String,String> mapEnd = Maps.newHashMap();
                    mapEnd.put("rankName","完成审批");
                    mapEnd.put("examineRemark","");
                    mapEnd.put("examineResultName","已完成");
                    mapEnd.put("examineResultId","");
                    mapEnd.put("processEnd","");
                    mapEnd.put("userName","");
                    mapEnd.put("modifyTime",DateFormatUtils.format(model.getModifyTime(),"yy/MM/dd HH:mm"));
                    mapEnd.put("createTime",DateFormatUtils.format(model.getCreateTime(),"yy/MM/dd HH:mm"));
                    blExamineInfoMap.add(mapEnd);
                }
            }
        }
        resultMap.put("blExamineInfo",blExamineInfoMap);
        if(coupon_examine.getId().longValue()==businessTypeId.longValue()){
            Coupon coupon = couponService.get(id);
            resultMap.put("coupon",coupon);
        }else if(employee_apply_coupon_examine.getId().longValue()==businessTypeId.longValue()){
            Map<String,Object> map = dao.getPersonCouponExamineMap(id);
            if(null!=map){
                List<BlCouponUsePerson> list = blCouponUsePersonService.getUsersByCouponExamineId(id);
                map.put("blCouponUsePersonList",list);
            }
            Float f = NumberUtils.toFloat(map.get("discount").toString(),0f);
            String  useBeginTime = DateFormatUtils.format((Date) map.get("createTime"),"yyyy-MM-dd");
            String  useEndTime = DateFormatUtils.format(DateUtils.addDays((Date) map.get("createTime"),30),"yyyy-MM-dd");
            map.put("useBeginTime",useBeginTime);
            map.put("useEndTime",useEndTime);
            resultMap.put("coupon",map);
        }else{
            resultMap.put("coupon",Maps.newHashMap());
        }

        return resultMap;
    }


    /**
     * 获取当前用户 员工申请优惠券 待审批列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<BlPersonCouponExamineInfo> getPersonApplyCouponExamineUnFinish(int pageNo,int pageSize){
        Dictionary dictionary = DictUtils.getByCode(Global.UNREVIEW);
        Page<BlPersonCouponExamineInfo> page = new Page<>(pageNo,pageSize);
        BlPersonCouponExamineInfo blPersonCouponExamineInfo =new BlPersonCouponExamineInfo();
        blPersonCouponExamineInfo.setCurrentUser();
        blPersonCouponExamineInfo.setNextUserId(blPersonCouponExamineInfo.getCurrentId());
        blPersonCouponExamineInfo.setPage(page);
        blPersonCouponExamineInfo.setExamineResultId(dictionary.getId());
        List<BlPersonCouponExamineInfo> list = dao.findPersonAndExamineInfoPage(blPersonCouponExamineInfo);
        page.setList(list);
        return page;
    }

    /**
     * 获取当前用户  员工申请优惠券 已审批批列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<BlPersonCouponExamineInfo> getPersonApplyCouponExamineFinished(int pageNo,int pageSize){
        Dictionary review_failure = DictUtils.getByCode(Global.REVIEW_FAILURE);
        Dictionary reviewed = DictUtils.getByCode(Global.REVIEWED);
        Page<BlPersonCouponExamineInfo> page = new Page<>(pageNo,pageSize);
        BlPersonCouponExamineInfo blPersonCouponExamineInfo =new BlPersonCouponExamineInfo();
        blPersonCouponExamineInfo.setExamineResultIdList(Arrays.asList(reviewed.getId(),review_failure.getId()));
        blPersonCouponExamineInfo.setCurrentUser();
        blPersonCouponExamineInfo.setNextUserId(blPersonCouponExamineInfo.getCurrentId());
        blPersonCouponExamineInfo.setPage(page);
        List<BlPersonCouponExamineInfo> list = dao.findPersonAndExamineInfoPage(blPersonCouponExamineInfo);
        page.setList(list);
        return page;
    }


    @Override
    public BlPersonCouponExamine get(Long id) {
        BlPersonCouponExamine blPersonCouponExamine = super.get(id);
        List<BlCouponUsePerson> list = blCouponUsePersonService.getUsersByCouponExamineId(id);
        blPersonCouponExamine.setBlCouponUsePersonList(list);
        return blPersonCouponExamine;
    }

}
