package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.entity.ShareToken;
import com.dongpeng.common.utils.ShareTokenUtils;
import com.dongpeng.common.utils.SnowflakeIdUtils;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.dao.CouponDao;
import com.dongpeng.system.dao.PersonCouponDao;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PersonCouponService extends BaseCrudService<PersonCouponDao, PersonCoupon> {

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private BlRankService blRankService;

    @Autowired
    private CouponShareLinkService couponShareLinkService;

    @Autowired
    private BlScoreDetailService blScoreDetailService;

    @Value("${constant.coupon.coupon_count_prefix}")
    private String COUPON_COUNT_PREFIX;

    @Value("${constant.coupon.super_score_percent}")
    private String superScorePercent;

    private final String COUPON_REVEIVE_PREFIX="COUPON_REVEIVE_PREFIX";

    private final String COUPON_WRITEOFF_PREFIX="COUPON_WRITEOFF_PREFIX";
    @Override
    public String createDataScopeSql(PersonCoupon entity) {
        return null;
    }

    /**

    * 用户领取一张优惠券

    * @param couponId 优惠券Id

    * @return

    * @exception

    * @date        2018/10/29 15:40

    */
    @Transactional
    public ResponseResult receiveCoupon(Long couponId,User user ,User fromUser) throws OptimisticLockException, NullPointerException, IllegalStateException, IllegalArgumentException, InterruptedException {
        Preconditions.checkNotNull(user);
        RLock lock = redisson.getLock(COUPON_REVEIVE_PREFIX+couponId+user.getId());
        if (lock.tryLock()){
            try {
                Coupon coupon = (Coupon) couponService.get(couponId);
                Preconditions.checkNotNull(coupon);
                Preconditions.checkNotNull(user);
                Dictionary couponStatus = dictionaryService.get(coupon.getStatusId());
                Preconditions.checkState(couponStatus.getDetailCode().equals(Global.COUPON_STATUS_ACTIVE),"优惠券状态未激活，无法领取");
                Preconditions.checkState(dao.findListByCouponIdAndUserId(couponId,user.getId()).isEmpty(),"用户已经领取过该优惠券");
                Preconditions.checkState(checkRank(coupon,user),"用户职级不够，无法领取");
                Preconditions.checkState(checkReceiveType(coupon,user),"非特定用户，无法领取");
                PersonCoupon personCoupon = new PersonCoupon();
                personCoupon.setPcNo(String.valueOf(SnowflakeIdUtils.getInstance().nextId()));
                personCoupon.setCpnName(coupon.getCpnName());
                personCoupon.setCpnId(couponId);
                personCoupon.setCpnNo(coupon.getCpnNo());
                personCoupon.setPersonId(user.getId());
                personCoupon.setPersonName(user.getUserName());
                Dictionary personCouponStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_NOT_WRITE_OFF);
                personCoupon.setStatusId(personCouponStatus.getId());
                personCoupon.setStatusName(personCouponStatus.getDetailName());
                if (fromUser!=null){
                    personCoupon.setFromId(fromUser.getId());
                    personCoupon.setFromName(fromUser.getUserName());
                }
                Date now =  new Date();
                Preconditions.checkState(now.after(coupon.getReceiveBeginTime())&&now.before(coupon.getReceiveEndTime()),"不在领取时间内，无法领取");
                Preconditions.checkState(save(personCoupon)==1,"领取失败");
                RAtomicLong couponCount = redisson.getAtomicLong(COUPON_COUNT_PREFIX+coupon.getCpnNo());
                long remainder = couponCount.decrementAndGet();
                Preconditions.checkArgument(remainder>=0,"优惠券可领取数量不足");
                couponService.cpnCalculateSend(coupon);
                return ResponseResult.ok(coupon);
            }
            finally {
                lock.unlock();
            }
        }
        return ResponseResult.failByBusiness("领取失败");
    }

    public ResponseResult getPersonCouponsByPage(PersonCoupon personCoupon){
        Preconditions.checkArgument(personCoupon.getPage().getPageSize()!=-1,"page size can not be null");
        List<PersonCoupon> data=dao.findList(personCoupon);
        personCoupon.getPage().setList(data);
        ResponseResult response =  ResponseResult.ok(personCoupon.getPage());
        return response;
    }

    public List<PersonCoupon> getExportData(PersonCoupon personCoupon){
        personCoupon.getPage().setPageSize(-1);
        return dao.findList(personCoupon);
    }

    private boolean checkRank(Coupon coupon,User user){
        long coupobRankId=coupon.getRankId();
        long userRankId=user.getRankId();
        if (coupobRankId==userRankId){
            return true;
        }
        else {
            BlRank blRank = blRankService.get(coupobRankId);
            String rankParents = blRank.getParentIds();
            return rankParents!=null&&rankParents.contains(String.valueOf(userRankId));
        }
    }

    private boolean checkOrg(Organization organization,User user){
        if (organization.getId().longValue()==user.getOrgId().longValue()){
            return true;
        }
        else {
            String parentIds =organizationService.get(user.getOrgId()).getParentIds();
            return parentIds!=null&&parentIds.contains(String.valueOf(organization.getId()));
        }
    }

    private boolean checkReceiveType(Coupon coupon,User user){
        Dictionary receiveType = dictionaryService.get(coupon.getReceiveTypeId());
        if (receiveType.getDetailCode().equals(Global.COUPON_RECEIVE_TYPE_PUBLIC)){
            return true;
        }
        else if (receiveType.getDetailCode().equals(Global.COUPON_RECEIVE_TYPE_STAFF)){
            Dictionary userType = dictionaryService.get(user.getTypeId());
            if (userType.getDetailCode().equals(Global.DICT_USER_TYPE_EMPLOYEE)){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public List<PersonCoupon> findListByCouponId(Long couponId){
       return dao.findListByCouponId(couponId);
    }

    public void personCouponInvalid(Long personCouponId){
        PersonCoupon personCoupon = get(personCouponId);
        Preconditions.checkNotNull(personCoupon);
        Dictionary personCouponInvalidStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_INVALID);
        personCoupon.setStatusId(personCouponInvalidStatus.getId());
        personCoupon.setStatusName(personCouponInvalidStatus.getDetailName());
        Preconditions.checkState(dao.statusChange(personCoupon)==1,"操作失败");
    }

    @Transactional
    public ResponseResult receiveCouponFromShare(String token) throws OptimisticLockException, InterruptedException {
        ShareToken shareToken = ShareTokenUtils.cheakToken(token);
        User user = userService.getCurrentUser();
        User fromUser = userService.get(shareToken.getFromId());
        Preconditions.checkNotNull(user,"请先登录");
        Preconditions.checkNotNull(fromUser,"错误的分享链路");
        if (shareToken.getPhones()!=null&&!shareToken.getPhones().isEmpty()){
            Preconditions.checkState(shareToken.getPhones().contains(user.getPhone()),"非特定用户无法领取");
        }
        Coupon coupon = couponService.get(shareToken.getCouponId());
        Preconditions.checkNotNull(coupon,"错误的领取链接");
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
        return receiveCoupon(coupon.getId(),user,fromUser);
    }

    public ResponseResult findMyCoupon(CouponRequest couponRequest){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        couponRequest.setUserId(user.getId());
        return ResponseResult.ok(dao.findMyCoupons(couponRequest));
    }

    public ResponseResult generateWriteoffCode(Long personCouponId){
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        PersonCoupon personCoupon = get(personCouponId);
        Preconditions.checkNotNull(personCoupon,"错误的用户优惠券id");
        Preconditions.checkState(personCoupon.getPersonId().longValue()==user.getId().longValue(),"非本人优惠券无法核销");
        Long code = SnowflakeIdUtils.getInstance().nextId();
        RBucket<Long> bucket = redisson.getBucket(code.toString());
        bucket.set(personCouponId,15,TimeUnit.MINUTES);
        return ResponseResult.ok(code);
    }

    @Transactional
    public ResponseResult writeoff(WriteoffRequest writeoffRequest) throws InterruptedException, OptimisticLockException {
        Preconditions.checkNotNull(writeoffRequest.getDealAmount(),"成交金额不能为空");
        Preconditions.checkNotNull(writeoffRequest.getWriteoffCode());
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Dictionary userType = dictionaryService.get(user.getTypeId());
        Preconditions.checkState(userType.getDetailCode().equals(Global.DICT_USER_TYPE_EMPLOYEE),"非员工无法进行优惠券核销");
        RBucket<Long> bucket = redisson.getBucket(writeoffRequest.getWriteoffCode());
        Preconditions.checkNotNull(bucket.get(),"核销码已过期，请重新核销");
        RLock lock = redisson.getLock(COUPON_WRITEOFF_PREFIX+bucket.get());
        if (lock.tryLock()){
            try {
                PersonCoupon personCoupon = get(bucket.get());
                Preconditions.checkNotNull(personCoupon);
                Coupon coupon = couponService.get(personCoupon.getCpnId());
                Date now = new Date();
                Preconditions.checkState(now.after(coupon.getUseBeginTime())&&now.before(coupon.getUseEndTime()),"不在优惠券可使用时间范围内，无法核销");
                Dictionary personCouponStatus = dictionaryService.get(personCoupon.getStatusId());
                Preconditions.checkState(checkOrg(organizationService.get(coupon.getOrgId()),user),"所属组织不在优惠券可用组织内");
                Preconditions.checkState(personCouponStatus.getDetailCode().equals(Global.PERSON_COUPON_STATUS_NOT_WRITE_OFF),"优惠券状态:"+personCouponStatus.getDetailName()+",无法核销");
                BigDecimal condition = coupon.getUseCondition();
                BigDecimal dealAmount = new BigDecimal(writeoffRequest.getDealAmount());
                Preconditions.checkState(dealAmount.compareTo(condition)>=0,"成交金额小于使用门槛 "+condition.toString());
                personCoupon.setDealAmount(dealAmount);
                BigDecimal payAmount = BigDecimal.ZERO; //实付金额
                Dictionary couponType = dictionaryService.get(coupon.getTypeId());
                //不要把faceValue提上来 除非你想要报错
                if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MANZHE)){
                    BigDecimal faceValue = new BigDecimal(coupon.getFaceValue()); //折扣
                    payAmount = dealAmount.multiply(faceValue);
                }
                else if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MANJIAN)){
                    BigDecimal faceValue = new BigDecimal(coupon.getFaceValue()); //减免金额
                    payAmount = dealAmount.subtract(faceValue);
                }
                else if (couponType.getDetailCode().equals(Global.COUPON_TYPE_MEIMANJIAN)){
                    BigDecimal faceValue = new BigDecimal(coupon.getFaceValue()); //减免金额
                    payAmount = dealAmount.subtract(faceValue.multiply(dealAmount.divide(condition,0,BigDecimal.ROUND_DOWN)));
                }
                else {
                    payAmount = dealAmount;
                }
                personCoupon.setPayAmount(payAmount);
                Dictionary couponSource = dictionaryService.get(coupon.getSourceId());
                //私人申请的优惠券不产生积分
                if (!couponSource.getDetailCode().equals(Global.COUPON_SOURCE_PRIVATE)){
                    BigDecimal scorePercent = new BigDecimal(coupon.getScorePercent()); //积分比例
                    personCoupon.setScore(dealAmount.multiply(scorePercent).intValue());
                }
                Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
                personCoupon.setStatusId(writeoffStatus.getId());
                personCoupon.setStatusName(writeoffStatus.getDetailName());
                personCoupon.setOrgId(user.getOrgId());
                personCoupon.setOrgName(user.getOrgName());
                personCoupon.setWriteoffId(user.getId());
                personCoupon.setWriteoffName(user.getUserName());
                personCoupon.setWriteoffTime(now);
                personCoupon.setWriteoffRemark(writeoffRequest.getRemark());
                personCoupon.setDealBillUrl(writeoffRequest.getDealBillUrl());
                personCoupon.setPersonName(writeoffRequest.getPersonName());
                personCoupon.setPersonPhone(writeoffRequest.getPersonPhone());
                int result = dao.writeoff(personCoupon);
                Preconditions.checkState(result==1,"核销失败");
                scoreCaculate(personCoupon);//核销后积分计算
                bucket.delete();
                couponService.cpnCalculateSend(coupon);
                return ResponseResult.ok(coupon);
            }
            finally {
                lock.unlock();
            }
        }
        else {
            return ResponseResult.failByBusiness("该优惠券已经被锁");
        }

    }

    public ResponseResult getWriteoffResult(Long personCouponId){
        Map<String,Object> resulet = Maps.newHashMap();
        PersonCoupon personCoupon = get(personCouponId);
        Preconditions.checkNotNull(personCoupon);
        Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
        resulet.put("iswriteOff",writeoffStatus.getId().longValue()==personCoupon.getStatusId().longValue());
        resulet.put("score",personCoupon.getScore());
        return ResponseResult.ok(resulet);
    }

    public ResponseResult verifyWriteoffCode(String code){
        User currentUser = userService.getCurrentUser();
        Preconditions.checkNotNull(currentUser);
        Dictionary employeeUserType = dictionaryService.getByCode(Global.DICT_USER_TYPE_EMPLOYEE);
        Preconditions.checkState(employeeUserType.getId().longValue()==currentUser.getTypeId().longValue(),"非东鹏员工无法进行核销");
        RBucket<Long> bucket = redisson.getBucket(code);
        Preconditions.checkNotNull(bucket.get(),"核销码已过期，请重新核销");
        bucket.expire(15,TimeUnit.MINUTES);
        PersonCoupon personCoupon = get(bucket.get());
        Preconditions.checkNotNull(personCoupon);
        Coupon coupon=couponService.get(personCoupon.getCpnId());
        Preconditions.checkNotNull(coupon);
        Map<String,Object> map = Maps.newHashMap();
        User user = userService.get(personCoupon.getPersonId());
        map.put("coupon",coupon);
        map.put("pcNo",personCoupon.getPcNo());
        map.put("witeoff_code",code);
        map.put("name",user.getPersonName());
        map.put("phone",user.getPhone());
        return ResponseResult.ok(map);
    }

    private void scoreCaculate(PersonCoupon personCoupon) throws OptimisticLockException {
        Coupon coupon = couponService.get(personCoupon.getCpnId());
        Dictionary couponSource = dictionaryService.get(coupon.getSourceId());
        //私人申请的优惠券不增加积分
        if (couponSource.getDetailCode().equals(Global.COUPON_SOURCE_PRIVATE)){
            return;
        }
        Date now = new Date();
        Dictionary storeScoreSource = dictionaryService.getByCode(Global.STORE_EXPENDITURE);
        BlScoreDetail score = new BlScoreDetail();
        score.setUserId(personCoupon.getPersonId());
        score.setPcId(personCoupon.getId());
        score.setSourceId(storeScoreSource.getId());
        score.setSourceName(storeScoreSource.getDetailName());
        score.setOrgName(personCoupon.getOrgName());
        score.setProduceTime(now);
        score.setScore(personCoupon.getScore());
        blScoreDetailService.addScore(score);
        if (personCoupon.getFromId()!=null){
            Dictionary shareScoreSource = dictionaryService.getByCode(Global.SHARING_COUPON_EXPENDITURE);
            BlScoreDetail superScore = new BlScoreDetail();
            superScore.setUserId(personCoupon.getFromId());
            superScore.setPcId(personCoupon.getId());
            superScore.setSourceId(shareScoreSource.getId());
            superScore.setSourceName(shareScoreSource.getDetailName());
            superScore.setOrgName(personCoupon.getOrgName());
            superScore.setProduceTime(now);
            superScore.setScore(new BigDecimal(personCoupon.getScore()).multiply(new BigDecimal(superScorePercent)).intValue());
            blScoreDetailService.addScore(superScore);
        }
    }

    public PersonCoupon findListByCouponIdAndUserId(Long couponId, Long userId){
        List<PersonCoupon> list = dao.findListByCouponIdAndUserId(couponId,userId);
        return list.isEmpty()?null:list.get(0);
    }

    public ResponseResult getWriteoffInfo(WriteoffRequest request){
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(request.getStartDate(),"开始时间不能为空");
        Preconditions.checkNotNull(request.getEndDate(),"结束时间不能为空");
        Preconditions.checkArgument(request.getStartDate().before(request.getEndDate()),"开始时间必须小于结束时间");
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
        request.setPersonCouponStatusId(writeoffStatus.getId());
        request.setUserId(user.getId());
        return ResponseResult.ok(dao.getWriteoffInfo(request));
    }

    public ResponseResult listWriteOff(WriteoffRequest request){
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(request.getStartDate(),"开始时间不能为空");
        Preconditions.checkNotNull(request.getEndDate(),"结束时间不能为空");
        Preconditions.checkArgument(request.getStartDate().before(request.getEndDate()),"开始时间必须小于结束时间");
        Preconditions.checkArgument(request.getLimitIndex()!=null&&request.getLimitIndex()>=0,"错误的分页参数limitIndex");
        Preconditions.checkArgument(request.getLimitCount()!=null&&request.getLimitCount()>0&&request.getLimitCount()<200,"错误的分页参数limitCount");
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user);
        Dictionary writeoffStatus = dictionaryService.getByCode(Global.PERSON_COUPON_STATUS_WRITE_OFF);
        request.setPersonCouponStatusId(writeoffStatus.getId());
        request.setUserId(user.getId());
        return ResponseResult.ok(dao.listWriteOff(request));
    }

    public ResponseResult getWriteoffInfoDetail(Long personCouponId){
        Preconditions.checkNotNull(personCouponId,"错误的核销记录");
        User user = userService.getCurrentUser();
        Preconditions.checkNotNull(user,"请先登录");
        WriteoffRequest request = new WriteoffRequest();
        request.setUserId(user.getId());
        request.setPersonCouponId(personCouponId);
        return ResponseResult.ok(dao.getWriteoffInfoDetail(request));

    }
}
