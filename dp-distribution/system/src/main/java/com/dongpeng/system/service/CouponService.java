package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.entity.ShareToken;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.utils.ShareTokenUtils;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.dao.CouponDao;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.util.Strings;
import org.bouncycastle.util.Integers;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**

* @Description:    提供优惠券业务接口

* @Author:         xc

* @CreateDate:     2018/10/23 9:50

* @UpdateUser:     xc

* @UpdateDate:     2018/10/23 9:50

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Service
public class CouponService extends BaseCrudService<CouponDao, Coupon> {

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private BlRankService blRankService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonCouponService personCouponService;

    @Autowired
    private CouponShareLinkService couponShareLinkService;

    @Autowired
    private BlPersonCouponExamineService personCouponExamineService;

    @Autowired
    private BlCouponUsePersonService couponUsePersonService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private BlScoreDetailService blScoreDetailService;

    @Autowired
    private BlExamineInfoService blExamineInfoService;

    @Value("${constant.coupon.coupon_count_prefix}")
    private String COUPON_COUNT_PREFIX;

    @Transactional(readOnly = false)
    @Override
    public int save(Coupon entity) throws OptimisticLockException {
        int rs;
        entity.setCurrentUser();
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            rs= dao.insert(entity);
        } else {
            entity.preUpdate();
            User user = userService.getCurrentUser();
            Dictionary backUser = dictionaryService.getByCode(Global.DICT_USER_TYPE_BACKSTAGE);
            //非后台用户不更新修改时间，修改人和修改人id
            if (user!=null&&user.getTypeId().longValue()==backUser.getId().longValue()){
                rs= dao.update(entity);
            }
            else {
                entity.setModifierName(null);
                entity.setModifierId(null);
                entity.setModifyTime(null);
                rs= dao.update(entity);
            }
            if(0>=rs){
                throw new OptimisticLockException(this,entity);
            }
        }
        return rs;
    }

    /**

    * 分页查询优惠券信息 必须进行分页 即pagesize不能为-1

    * @param coupon

    * @return

    * @exception

    * @date        2018/10/25 15:36

    */
    public ResponseResult getCouponsByPage(Coupon coupon){
        Preconditions.checkArgument(coupon.getPage().getPageSize()!=-1,"page size can not be null");
        List<Coupon> data=dao.findList(coupon);
        coupon.getPage().setList(data);
        ResponseResult response =  ResponseResult.ok(coupon.getPage());
        return response;
    }

    /**

    * 获取导出数据

    * @param coupon

    * @return

    * @exception

    * @date        2018/10/29 10:35

    */
    public List<Coupon> getExportData(Coupon coupon){
        coupon.getPage().setPageSize(-1);
        return dao.findList(coupon);
    }

    /**

    * 新增一条优惠券 对属性进行参数校验

    * @param coupon

    * @return

    * @exception

    * @date        2018/10/25 15:37

    */
    @Transactional
    public ResponseResult addCoupon(Coupon coupon) throws OptimisticLockException {
        couponValidate(coupon);
        Dictionary inactiveStatus = dictionaryService.getByCode(Global.COUPON_STATUS_INACTIVE);
        coupon.setStatusId(inactiveStatus.getId());
        coupon.setStatusName(inactiveStatus.getDetailName());
        Dictionary unreviewStatus = dictionaryService.getByCode(Global.UNREVIEW);
        coupon.setExamineStatusId(unreviewStatus.getId());
        coupon.setExamineStatusName(unreviewStatus.getDetailName());
        String couponCode=generateCouponCode();
        coupon.setCpnNo(couponCode);
        return ResponseResult.ok(save(coupon));
    }

    private void couponValidate(Coupon coupon){
        Preconditions.checkNotNull(coupon);
        Preconditions.checkArgument(Strings.isNotBlank(coupon.getCpnName()),"优惠券名称不能为空");
        Preconditions.checkArgument(Strings.isNotBlank(coupon.getCpnIntro()),"优惠券描述不能为空");
        Preconditions.checkArgument(coupon.getCpnNum()!=null&&coupon.getCpnNum()>0,"错误的优惠券数量");
        Preconditions.checkNotNull(coupon.getOrgId(),"组织不能为空");
        Organization organization = organizationService.get(coupon.getOrgId());
        Preconditions.checkNotNull(organization,"错误的组织");
        coupon.setOrgName(organization.getOrgName());
        coupon.setServiceArea(organization.getServiceArea());
        Preconditions.checkNotNull(coupon.getTypeId(),"优惠券类型不能为空");
        Dictionary couponType=dictionaryService.get(coupon.getTypeId());
        Preconditions.checkNotNull(couponType,"错误的优惠券类型");
        coupon.setTypeName(couponType.getDetailName());
        if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MANZHE)){
            Preconditions.checkArgument(new BigDecimal(coupon.getFaceValue()).compareTo(BigDecimal.ONE)<0,"优惠券折扣不能超过1");
            Preconditions.checkArgument(new BigDecimal(coupon.getFaceValue()).compareTo(BigDecimal.ZERO)>0,"优惠券折扣不能小于0");
        }
        else if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MANJIAN)){
            Preconditions.checkArgument(coupon.getUseCondition().compareTo(new BigDecimal(coupon.getFaceValue()))>=0,"使用门槛不能小于优惠金额");
        }
        else if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MEIMANSONG)){
            Preconditions.checkArgument(coupon.getUseCondition().compareTo(BigDecimal.ZERO)>0,"每满送的使用门槛必须大于0");
        }
        else if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MEIMANJIAN)){
            Preconditions.checkArgument(coupon.getUseCondition().compareTo(new BigDecimal(coupon.getFaceValue()))>=0,"使用门槛不能小于优惠金额");
            Preconditions.checkArgument(coupon.getUseCondition().compareTo(BigDecimal.ZERO)>0,"每满减的使用门槛必须大于0");
        }
        Preconditions.checkNotNull(coupon.getUseCondition(),"优惠券使用门槛不能为空");
        Preconditions.checkArgument(Strings.isNotBlank(coupon.getFaceValue()),"优惠券面值不能为空");
        Preconditions.checkArgument(Strings.isNotBlank(coupon.getScorePercent()),"积分比例不能为空");
        Preconditions.checkNotNull(coupon.getReceiveBeginTime(),"领用开始时间不能为空");
        Preconditions.checkNotNull(coupon.getReceiveEndTime(),"领用结束时间不能为空");
        Preconditions.checkState(coupon.getReceiveBeginTime().compareTo(coupon.getReceiveEndTime())<=0,"领用开始时间必须在领用结束时间之前");
        Preconditions.checkNotNull(coupon.getUseBeginTime(),"使用开始时间不能为空");
        Preconditions.checkNotNull(coupon.getUseEndTime(),"使用结束时间不能为空");
        Preconditions.checkState(coupon.getUseBeginTime().compareTo(coupon.getUseEndTime())<=0,"使用开始时间必须在使用结束时间之前");
        Preconditions.checkState(coupon.getReceiveBeginTime().compareTo(coupon.getUseBeginTime())<=0,"领用开始时间必须在使用开始时间之前");
        Preconditions.checkState(coupon.getReceiveEndTime().compareTo(coupon.getUseEndTime())<=0,"领用结束时间必须在使用结束时间之前");
        Preconditions.checkNotNull(coupon.getRankId(),"可领取职级不能为空");
        Preconditions.checkNotNull(coupon.getSourceId(),"优惠券来源不能为空");
        Dictionary couponSource = dictionaryService.get(coupon.getSourceId());
        Preconditions.checkNotNull(couponSource,"错误的优惠券来源");
        coupon.setSourceName(couponSource.getDetailName());
        BlRank blRank = blRankService.get(coupon.getRankId());
        Preconditions.checkNotNull(blRank,"可领取职级不能为空");
        coupon.setRankName(blRank.getRankName());
        Preconditions.checkNotNull(coupon.getIsShare());
        Preconditions.checkNotNull(coupon.getReceiveTypeId(),"领用类型不能为空");
        Dictionary couponReceiveType = dictionaryService.get(coupon.getReceiveTypeId());
        Preconditions.checkNotNull(couponReceiveType,"错误的领用类型");
        coupon.setReceiveTypeName(couponReceiveType.getDetailName());
    }

    /**

     * 更新一条优惠券信息

     * @param coupon

     * @return

     * @exception

     * @date        2018/10/25 15:31

     */
    @Transactional
    public ResponseResult updateCoupon(Coupon coupon) throws OptimisticLockException {
        Preconditions.checkNotNull(coupon.getId());
        Coupon couponData = get(coupon.getId());
        Preconditions.checkNotNull(couponData,"错误的couponID");
        Dictionary status = dictionaryService.get(couponData.getStatusId());
        Dictionary reviewStatus = dictionaryService.get(couponData.getExamineStatusId());
        Preconditions.checkState(status.getDetailCode().equals(Global.COUPON_STATUS_INACTIVE),"优惠券状态为："+status.getDetailName()+",不允许修改");
        Preconditions.checkState(reviewStatus.getDetailCode().equals(Global.UNREVIEW)||reviewStatus.getDetailCode().equals(Global.REVIEW_FAILURE),"优惠券审核状态为："+reviewStatus.getDetailName()+",不允许修改");
        couponValidate(coupon);
        return save(coupon)==1?ResponseResult.ok():ResponseResult.failByBusiness("更新优惠券失败");
    }

    /**

    * sql层的权限控制 在sql里以$(sql)为占位符 例如 where 1=1 and $(sql)

    * @param entity

    * @return

    * @exception

    * @date        2018/10/25 15:41

    */
    @Override
    public String createDataScopeSql(Coupon entity) {
        return null;
    }

    /**

     * 激活优惠券 只有优惠券为inactive状态时才能激活

     * @param coupon

     * @return

     * @exception

     * @date        2018/10/25 15:32

     */
    @Transactional
    public ResponseResult couponActive(Coupon coupon){
        Preconditions.checkNotNull(coupon);
        Preconditions.checkNotNull(coupon.getVersion());
        Preconditions.checkNotNull(coupon.getId());
        Coupon updateCoupon = get(coupon.getId());
        Preconditions.checkNotNull(updateCoupon);
        Preconditions.checkState(coupon.getVersion()==updateCoupon.getVersion(),"此优惠券已被其他人修改，请刷新数据");
        Dictionary couponStaus = dictionaryService.get(updateCoupon.getStatusId());
        Dictionary reviewStatus = dictionaryService.get(updateCoupon.getExamineStatusId());
        Preconditions.checkState(reviewStatus.getDetailCode().equals(Global.REVIEWED),"优惠券审核状态："+reviewStatus.getDetailName()+"，无法激活");
        if (couponStaus.getDetailCode().equals(Global.COUPON_STATUS_INACTIVE)){
            Dictionary activeStatus = dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE);
            updateCoupon.setStatusId(activeStatus.getId());
            updateCoupon.setStatusName(activeStatus.getDetailName());
            int activerResult = dao.statusChange(updateCoupon);
            if (activerResult==1){
                RAtomicLong couponCount = redisson.getAtomicLong(COUPON_COUNT_PREFIX+updateCoupon.getCpnNo());
                couponCount.set(updateCoupon.getCpnNum());
                return ResponseResult.ok();
            }
            return ResponseResult.failByBusiness("操作失败");
        }
        else {
            return ResponseResult.failByBusiness("优惠券状态错误："+updateCoupon.getStatusName());
        }
    }

    /**

     * 结束一条优惠券 只有优惠券状态为inactive或者active时才能结束

     * @param coupon

     * @return

     * @exception

     * @date        2018/10/25 15:33

     */
    @Transactional
    public ResponseResult couponEnd(Coupon coupon){
        Preconditions.checkNotNull(coupon);
        Preconditions.checkNotNull(coupon.getVersion());
        Preconditions.checkNotNull(coupon.getId());
        Coupon updateCoupon = get(coupon.getId());
        Preconditions.checkNotNull(updateCoupon);
        Preconditions.checkState(coupon.getVersion()==updateCoupon.getVersion(),"此优惠券已被其他人修改，请刷新数据");
        Dictionary couponStaus = dictionaryService.get(updateCoupon.getStatusId());
        if (couponStaus.getDetailCode().equals(Global.COUPON_STATUS_INACTIVE)||couponStaus.getDetailCode().equals(Global.COUPON_STATUS_ACTIVE)){
            Dictionary endStatus = dictionaryService.getByCode(Global.COUPON_STATUS_END);
            updateCoupon.setStatusId(endStatus.getId());
            updateCoupon.setStatusName(endStatus.getDetailName());
            Preconditions.checkState(dao.statusChange(updateCoupon)==1,"操作失败");
            blExamineInfoService.deleteToggleByBisIdAndExResultId(coupon.getId());
            return ResponseResult.ok();
        }
        else {
            return ResponseResult.failByBusiness("优惠券状态错误："+updateCoupon.getStatusName());
        }

    }

    /**

     * 作废一条优惠券

     * @param coupon

     * @return

     * @exception

     * @date        2018/10/25 15:34

     */
    @Transactional
    public ResponseResult couponInvalid(Coupon coupon){
        Preconditions.checkNotNull(coupon);
        Preconditions.checkNotNull(coupon.getVersion());
        Preconditions.checkNotNull(coupon.getId());
        Coupon updateCoupon = get(coupon.getId());
        Preconditions.checkNotNull(updateCoupon);
        Preconditions.checkState(coupon.getVersion()==updateCoupon.getVersion(),"此优惠券已被其他人修改，请刷新数据");
        Dictionary couponStaus = dictionaryService.get(updateCoupon.getStatusId());
        if (couponStaus.getDetailCode().equals(Global.COUPON_STATUS_INVALID)){
            return ResponseResult.failByBusiness("此优惠券已经被作废");
        }
        else {
            Dictionary invalidStatus = dictionaryService.getByCode(Global.COUPON_STATUS_INVALID);
            updateCoupon.setStatusId(invalidStatus.getId());
            updateCoupon.setStatusName(invalidStatus.getDetailName());
            Preconditions.checkArgument(dao.statusChange(updateCoupon)==1,"操作失败");
            personCouponService.findListByCouponId(coupon.getId()).forEach(e->personCouponService.personCouponInvalid(e.getId()));
            blExamineInfoService.deleteToggleByBisIdAndExResultId(coupon.getId());
            return ResponseResult.ok();
        }
    }

    private String generateCouponCode(){
        String couponIndex = DateUtils.getDate("yyMMdd");
        Long couponNum = Long.parseLong(couponIndex+"0000");
        RAtomicLong code = redisson.getAtomicLong("couponIndex"+couponIndex);
        return String.valueOf(couponNum+code.incrementAndGet());
    }

    /**

    * 优惠券审核状态修改，审核状态修改需要使用此接口

    * @param coupon 需要更新的coupon

    * @return

    * @exception

    * @date        2018/10/26 13:18

    */
    @Transactional
    public ResponseResult examineStatusChange(Coupon coupon){
        Preconditions.checkNotNull(coupon);
        Dictionary status = dictionaryService.get(coupon.getStatusId());
        Preconditions.checkState(status.getDetailCode().equals(Global.COUPON_STATUS_INACTIVE),"优惠券状态："+status.getDetailName()+"，无法修改审核状态");
        return dao.examineStatusChange(coupon)==1?ResponseResult.ok():ResponseResult.failByBusiness("修改审核状态失败");
    }

    public ResponseResult getShareToken(Long couponId){
        Preconditions.checkNotNull(couponId);
        Coupon coupon = get(couponId);
        RAtomicLong couponCount = redisson.getAtomicLong(COUPON_COUNT_PREFIX+coupon.getCpnNo());
        Preconditions.checkArgument(couponCount.get()>0,"优惠券可领取数量不足，无法分享");
        Dictionary receiveType = dictionaryService.get(coupon.getReceiveTypeId());
        if (receiveType.getDetailCode().equals(Global.COUPON_RECEIVE_TYPE_PUBLIC)){
            return getShareTokenByCoupon(couponId);
        }
        else if (receiveType.getDetailCode().equals(Global.COUPON_RECEIVE_TYPE_STAFF)){
            User user = userService.getCurrentUser();
            Preconditions.checkNotNull(user);
            PersonCoupon personCoupon = personCouponService.findListByCouponIdAndUserId(couponId,user.getId());
            Preconditions.checkNotNull(personCoupon,"你没有拥有此优惠券，无法分享");
            return getShareTokenByPersonCoupon(personCoupon.getId());
        }
        else {
            return ResponseResult.failByBusiness("错误的优惠券id");
        }
    }

    public ResponseResult getShareTokenByPersonCoupon(Long personCouponId){
        Preconditions.checkNotNull(personCouponId);
        PersonCoupon personCoupon = personCouponService.get(personCouponId);
        Preconditions.checkNotNull(personCoupon,"错误的优惠券id");
        User user = userService.getCurrentUser();
        Preconditions.checkState(personCoupon.getPersonId().longValue()==user.getId().longValue(),"非本人优惠券");
        Coupon coupon = get(personCoupon.getCpnId());
        Date now = new Date();
        Preconditions.checkState(now.before(coupon.getReceiveEndTime()),"已过优惠券领取时间，无法领取");
        Preconditions.checkState(coupon.getIsShare(),"优惠券无法分享");
        ShareToken token = new ShareToken();
        token.setFromId(user.getId());
        token.setCouponId(personCoupon.getCpnId());
        Map<String,Object> data = Maps.newHashMap();
        data.put("token",ShareTokenUtils.getToken(token));
        data.put("couponId",personCoupon.getCpnId());
        return ResponseResult.ok(data);
    }


    public ResponseResult getShareTokenByCoupon(Long couponId){
        Preconditions.checkNotNull(couponId);
        Coupon coupon = get(couponId);
        Preconditions.checkNotNull(coupon);
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Date now = new Date();
        Dictionary receiveType = dictionaryService.get(coupon.getReceiveTypeId());
        Preconditions.checkState(receiveType.getDetailCode().equals(Global.COUPON_RECEIVE_TYPE_PUBLIC),"非公共优惠券无法分享");
        Preconditions.checkState(now.before(coupon.getReceiveEndTime()),"已过优惠券领取时间，无法领取");
        Preconditions.checkState(coupon.getIsShare(),"优惠券无法分享");
        ShareToken token = new ShareToken();
        token.setFromId(user.getId());
        token.setCouponId(couponId);
        Map<String,Object> data = Maps.newHashMap();
        data.put("token",ShareTokenUtils.getToken(token));
        data.put("couponId",couponId);
        return ResponseResult.ok(data);
    }

    @Transactional
    public ResponseResult getShareTokenByToken(String token) throws OptimisticLockException {
        ShareToken shareToken = ShareTokenUtils.cheakToken(token);
        Coupon coupon = get(shareToken.getCouponId());
        Preconditions.checkNotNull(coupon,"错误的分享token");
        RAtomicLong couponCount = redisson.getAtomicLong(COUPON_COUNT_PREFIX+coupon.getCpnNo());
        Preconditions.checkArgument(couponCount.get()>0,"优惠券可领取数量不足，无法分享");
        User fromUser = userService.get(shareToken.getFromId());
        Preconditions.checkNotNull(fromUser);
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        CouponShareLink link =  new CouponShareLink();
        link.setCpnId(shareToken.getCouponId());
        link.setCpnName(coupon.getCpnName());
        link.setFromId(shareToken.getFromId());
        link.setFromName(fromUser.getUserName());
        link.setToId(user.getId());
        link.setToName(user.getUserName());
        CouponShareLink parent = couponShareLinkService.getParent(link);
        if (parent==null){
            link.setFromIds(fromUser.getId().toString());
        }
        else {
            link.setFromIds(parent.getFromIds()+","+fromUser.getId());
        }
        couponShareLinkService.add(link);
        shareToken.setFromId(user.getId());
        Map<String,Object> data = Maps.newHashMap();
        data.put("token",ShareTokenUtils.getToken(shareToken));
        data.put("couponId",coupon.getId());
        return ResponseResult.ok(data);
    }

    public ResponseResult getShareTokenByExamine(Long id){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        BlPersonCouponExamine personCouponExamine = personCouponExamineService.get(id);
        Preconditions.checkNotNull(personCouponExamine,"错误的申请表id");
        Dictionary examineStatus = dictionaryService.get(personCouponExamine.getApplyStatusId());
        Preconditions.checkState(examineStatus.getDetailCode().equals(Global.REVIEWED),"该审批还未通过");
        Preconditions.checkArgument(user.getId().longValue()==personCouponExamine.getPersonId().longValue(),"此优惠券不属于你");
        Coupon coupon = get(personCouponExamine.getCouponId());
        RAtomicLong couponCount = redisson.getAtomicLong(COUPON_COUNT_PREFIX+coupon.getCpnNo());
        Preconditions.checkArgument(couponCount.get()>0,"优惠券可领取数量不足，无法分享");
        Preconditions.checkState(coupon.getIsShare(),"优惠券无法分享");
        ShareToken token =  new ShareToken();
        token.setFromId(user.getId());
        token.setCouponId(personCouponExamine.getCouponId());
        token.setPhones(couponUsePersonService.getUsersByCouponExamineId(id).stream().map(e->e.getPhone()).collect(Collectors.toList()));
        Map<String,Object> data = Maps.newHashMap();
        data.put("token",ShareTokenUtils.getToken(token));
        data.put("couponId",personCouponExamine.getCouponId());
        return ResponseResult.ok(data);
    }

    public ResponseResult findPublicCoupons(CouponRequest couponRequest){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(couponRequest.getRegionId(),"请选择地址");
        couponRequest.setStatusId(dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE).getId());
        couponRequest.setDate(new Date());
        Dictionary userType = dictionaryService.getByCode(Global.DICT_USER_TYPE_FRIEND);
        if (user.getTypeId().longValue()==userType.getId().longValue()){
            couponRequest.setReceiveTypeId(dictionaryService.getByCode(Global.COUPON_RECEIVE_TYPE_PUBLIC).getId());
        }
        couponRequest.setSourceId(dictionaryService.getByCode(Global.COUPON_SOURCE_PUBLIC).getId());
        return ResponseResult.ok(dao.findPublicCoupons(couponRequest));
    }

    public ResponseResult findTagCoupons(CouponRequest couponRequest){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        //Preconditions.checkNotNull(couponRequest.getRegionId(),"请选择地址");
        couponRequest.setStatusId(dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE).getId());
        couponRequest.setDate(new Date());
        Dictionary userType = dictionaryService.getByCode(Global.DICT_USER_TYPE_FRIEND);
        if (user.getTypeId().longValue()==userType.getId().longValue()){
            couponRequest.setReceiveTypeId(dictionaryService.getByCode(Global.COUPON_RECEIVE_TYPE_PUBLIC).getId());
        }
        couponRequest.setSourceId(dictionaryService.getByCode(Global.COUPON_SOURCE_PUBLIC).getId());
        return ResponseResult.ok(dao.findTagCoupons(couponRequest));
    }

    public void cpnCalculateSend(Coupon coupon){
        coupon.setCurrentUser();
        amqpTemplate.convertAndSend("CouponCalculateExchange","CouponCalculateQueue",coupon);
    }

    @Transactional
    public void cpnCalculate(Coupon coupon) throws OptimisticLockException {
        Preconditions.checkNotNull(coupon);
        List<PersonCoupon> list=personCouponService.findListByCouponId(coupon.getId());
        if (list.isEmpty())
            return;
        BigDecimal receiveNum = new BigDecimal(list.size()); //已领取数量
        BigDecimal cpnNum = new BigDecimal(coupon.getCpnNum()); //总数量
        coupon.setCpnReceivedNum(receiveNum.intValue());
        coupon.setReceivePercent(receiveNum.divide(cpnNum,2,BigDecimal.ROUND_UP));
        Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
        List<PersonCoupon> writeoffList = list.stream().filter(p->p.getStatusId().longValue()==writeoffStatus.getId().longValue()).collect(Collectors.toList());
        if (!writeoffList.isEmpty()){
            BigDecimal writeoffNum = new BigDecimal(writeoffList.size()); //已核销数量
            coupon.setWriteoffNum(writeoffNum.intValue());
            coupon.setWriteoffPercent(writeoffNum.divide(cpnNum,2,BigDecimal.ROUND_UP));
            final BigDecimal[] writeoffAmount = {BigDecimal.ZERO}; //核销金额
            final BigDecimal[] score = {BigDecimal.ZERO}; //产生积分
            final BigDecimal[] payAmount = {BigDecimal.ZERO}; //实付金额
            writeoffList.forEach(p->{
                writeoffAmount[0] = writeoffAmount[0].add(p.getDealAmount());
                score[0] = score[0].add(blScoreDetailService.getScoreByPersonCouponId(p.getId()));
                payAmount[0] = payAmount[0].add(p.getPayAmount());
            });
            coupon.setWriteoffAmount(writeoffAmount[0]);
            coupon.setScore(score[0].intValue());
            coupon.setPayAmount(payAmount[0]);
        }
        save(coupon);
    }

    public ResponseResult findCouponsByOrgId(Long orgId){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(orgId);
        Organization organization = organizationService.get(orgId);
        Preconditions.checkNotNull(organization);
        CouponRequest couponRequest =  new CouponRequest();
        couponRequest.setOrgParentIds(organization.getParentIds());
        couponRequest.setOrgId(orgId);
        couponRequest.setStatusId(dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE).getId());
        couponRequest.setDate(new Date());
        Dictionary userType = dictionaryService.getByCode(Global.DICT_USER_TYPE_FRIEND);
        if (user.getTypeId().longValue()==userType.getId().longValue()){
            couponRequest.setReceiveTypeId(dictionaryService.getByCode(Global.COUPON_RECEIVE_TYPE_PUBLIC).getId());
        }
        couponRequest.setSourceId(dictionaryService.getByCode(Global.COUPON_SOURCE_PUBLIC).getId());
        List<Coupon> list = dao.findCouponsByOrgId(couponRequest);
        int receiveNum = list.stream().map(Coupon::getCpnReceivedNum).reduce(0,(a,b)->a+b);
        Map<String,Object> data = Maps.newHashMap();
        data.put("data",list);
        data.put("receiveNum",receiveNum);
        return ResponseResult.ok(data);
    }

    public ResponseResult findSharedCoupons(){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Dictionary activeStatus = dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE);
        Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
        List<Coupon> list = dao.findShareCoupons(user.getId(),activeStatus.getId());
        list.forEach(c->{
            int num = c.getPersonCoupons().size();
            int usedNum = c.getPersonCoupons().stream().filter(p->p.getStatusId().longValue()==writeoffStatus.getId().longValue()).collect(Collectors.toList()).size();
            c.setCpnNum(num);
            c.setCpnReceivedNum(usedNum);
        });
        return ResponseResult.ok(list);
    }

    public ResponseResult findShareInvalidCoupons(){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Dictionary activeStatus = dictionaryService.getByCode(Global.COUPON_STATUS_INVALID);
        Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
        List<Coupon> list = dao.findShareCoupons(user.getId(),activeStatus.getId());
        list.forEach(c->{
            int num = c.getPersonCoupons().size();
            int usedNum = c.getPersonCoupons().stream().filter(p->p.getStatusId().longValue()==writeoffStatus.getId().longValue()).collect(Collectors.toList()).size();
            c.setCpnNum(num);
            c.setCpnReceivedNum(usedNum);
        });
        return ResponseResult.ok(list);
    }

    public ResponseResult findAbleShareCoupons(CouponRequest couponRequest){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(couponRequest.getLimitIndex());
        Preconditions.checkNotNull(couponRequest.getLimitCount());
        couponRequest.setStatusId(dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE).getId());
        couponRequest.setDate(new Date());
        couponRequest.setReceiveTypeId(dictionaryService.getByCode(Global.COUPON_RECEIVE_TYPE_PUBLIC).getId());
        couponRequest.setUserId(user.getId());
        return ResponseResult.ok(dao.findAbleShareCoupons(couponRequest));
    }

    @Transactional
    public ResponseResult expireCoupon(){
        Dictionary activeStatus = dictionaryService.getByCode(Global.COUPON_STATUS_ACTIVE);
        List<Coupon> expiredCoupons = dao.findExpiredCoupon(activeStatus.getId());
        expiredCoupons.forEach(this::couponInvalid);
        return ResponseResult.ok();
    }

    public ResponseResult findReceivedUserFromShare(Long couponId){
        Preconditions.checkNotNull(couponId,"优惠券id不能为空");
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user,"请先登录");
        return ResponseResult.ok(dao.findReceivedUserFromShare(couponId,user.getId()));
    }

    public ResponseResult getByShareToken(String shareToken){
        Preconditions.checkNotNull(shareToken,"分享token不能为空");
        ShareToken share = ShareTokenUtils.cheakToken(shareToken);
        return  ResponseResult.ok(dao.get(share.getCouponId()));
    }


}
