package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DecimalCalculateUtil;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.entity.system.BlScoreDetail;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.system.dao.BlScoreDetailDao;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.cache.NullCacheKey;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class BlScoreDetailService extends BaseCrudService<BlScoreDetailDao, BlScoreDetail> {


    private static final long  EXPIRATION_TIME = 300;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public String createDataScopeSql(BlScoreDetail entity) {
        return null;
    }


    @Transactional
    @Override
    public int save(BlScoreDetail entity) throws OptimisticLockException {
        int rs = super.save(entity);
        return rs;
    }

    /**
     * 增加积分
     * @param entity
     * @return
     * @throws OptimisticLockException
     */
    @Transactional
    public void addScore(BlScoreDetail entity) throws OptimisticLockException {
        Dictionary dictionary = DictUtils.getById(entity.getSourceId());
        Preconditions.checkNotNull(dictionary,"积分来源不存在");
        Preconditions.checkNotNull(entity.getSourceId(),"积分来源ID不能为空");
        entity.setSourceName(dictionary.getDetailName());
        entity.setCurrentUser();
        updateUserScore(entity.getUserId(),entity.getScore());
        updateCouponScore(entity.getPcId(),entity.getScore());
        amqpTemplate.convertAndSend("scoreMq",entity);
    }

    /**
     * 扣减积分
     * @param entity
     * @return
     * @throws OptimisticLockException
     */
    @Transactional
    public void consumeScore(BlScoreDetail entity) throws OptimisticLockException {
        Dictionary dictionary = DictUtils.getById(entity.getSourceId());
        BigDecimal sum = getScoreByUserId(entity.getUserId());
        Preconditions.checkArgument(sum.intValue()+entity.getScore() >= 0  ,"积分不够");
        entity.setSourceName(dictionary.getDetailName());
        entity.setCurrentUser();
        updateUserScore(entity.getUserId(),entity.getScore());
        //updateCouponScore(entity.getPcId(),entity.getScore());
        amqpTemplate.convertAndSend("scoreMq",entity);
    }

    /**
     * 获取当前用户积分详细
     * @param page
     * @param blScoreDetail
     * @return
     */
    public ResponseResult getScoreDetailList(Page<BlScoreDetail> page, BlScoreDetail blScoreDetail){
        blScoreDetail.setPage(page);
        List<BlScoreDetail> list =  dao.findList(blScoreDetail);
        BigDecimal sum = dao.getScoreByUserId(blScoreDetail.getUserId());
        sum = sum==null ? BigDecimal.ZERO : sum ;
        page.setList(list);
        ResponseResult responseResult = ResponseResult.ok();
        responseResult.put("data",page);
        responseResult.put("sumScore",sum);
        return responseResult;
    }


    /**
     * 获取所有积分列表
     * @param page
     * @param paramMap
     * @return
     */
    public Page<BlScoreDetail> findListPage(Page<BlScoreDetail> page, Map<String,Object> paramMap){
        paramMap.put("page",page);
        paramMap.put("DEL_FLAG_NORMAL",Boolean.FALSE);
        List<BlScoreDetail> list = dao.findListPage(paramMap);
        page.setList(list);
        return page;
    }


    /**
     * 获取当前用户总积分
     * @param blScoreDetail
     * @return
     */
    public BigDecimal getScoreByCurrentUser (BlScoreDetail blScoreDetail){
        blScoreDetail.setCurrentUser();
        BigDecimal sum = BigDecimal.ZERO;
        RAtomicLong rAtomicLong = redisson.getAtomicLong(Global.USER_SCORE_TORAL.concat(blScoreDetail.getCurrentId().toString()));
        if(!rAtomicLong.isExists()){
            blScoreDetail.setUserId(blScoreDetail.getCurrentId());
            sum = dao.getScoreByUserId(blScoreDetail.getCurrentId());
            sum = sum==null ? BigDecimal.ZERO : sum ;
            rAtomicLong.expire(EXPIRATION_TIME,TimeUnit.SECONDS);
            rAtomicLong.compareAndSet(0,sum.longValue());
        }else {
            sum = new BigDecimal(rAtomicLong.get());
        }
        return sum;
    }

    public BigDecimal getScoreByUserId(Long userId){
        RAtomicLong rAtomicLong = redisson.getAtomicLong(Global.USER_SCORE_TORAL.concat(userId.toString()));
        if (rAtomicLong.isExists()){
            return new BigDecimal(rAtomicLong.get());
        }else {
            BigDecimal sum = dao.getScoreByUserId(userId);
            sum = sum==null ? BigDecimal.ZERO : sum ;
            rAtomicLong.expire(EXPIRATION_TIME,TimeUnit.SECONDS);
            rAtomicLong.compareAndSet(0,sum.longValue());
            return sum;
        }
    }

    /**
     * 获取当前优惠券产生的总积分
     * @param personCouponId
     * @return
     */
    public BigDecimal getScoreByPersonCouponId (Long personCouponId){
        String key = Global.COUPON_SCORE_TORAL_.concat(personCouponId.toString());
        BigDecimal sum = BigDecimal.ZERO;
        RBucket<BigDecimal> bucket = redisson.getBucket(key);
        if(!bucket.isExists()){
            sum = dao.getScoreByPersonCouponId(personCouponId);
            sum = sum==null ? BigDecimal.ZERO : sum ;
            bucket.set(sum,1,TimeUnit.MINUTES);
        }else {
            sum = bucket.get();
        }
        return sum;
    }

    /**
     * 更新用户积分缓存
     * @param userId
     * @param score
     */
    public void updateUserScore(Long userId,Integer score){
        RAtomicLong rAtomicLong = redisson.getAtomicLong(Global.USER_SCORE_TORAL.concat(userId.toString()));
        if(!rAtomicLong.isExists()){
            BigDecimal total = this.getScoreByUserId(userId);
            Double d = DecimalCalculateUtil.add(total.doubleValue(),score.doubleValue());
            rAtomicLong.expire(300, TimeUnit.SECONDS);
            rAtomicLong.set(d.longValue());
        }else{
            rAtomicLong.addAndGet(score);
        }
    }

    /**
     * 更新优惠券总积分缓存
     * @param couponId
     * @param score
     */
    public BigDecimal updateCouponScore (Long couponId,Integer score){
        String key = Global.COUPON_SCORE_TORAL_.concat(couponId.toString());
        BigDecimal sum = BigDecimal.ZERO;
        RBucket<BigDecimal> bucket = redisson.getBucket(key);
        if(!bucket.isExists()){
            sum = dao.getScoreByPersonCouponId(couponId);
            sum = sum==null ? BigDecimal.ZERO : sum ;
            Double total = DecimalCalculateUtil.add(sum.doubleValue(),score.doubleValue());
            bucket.set(new BigDecimal(total),1,TimeUnit.MINUTES);
        }else {
            sum = bucket.get();
            Double total = DecimalCalculateUtil.add(sum.doubleValue(),score.doubleValue());
            bucket.set(new BigDecimal(total),1,TimeUnit.MINUTES);
        }
        return sum;
    }

}
