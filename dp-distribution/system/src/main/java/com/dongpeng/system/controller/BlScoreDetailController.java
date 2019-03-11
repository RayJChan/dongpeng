package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.BlScoreDetail;
import com.dongpeng.system.service.BlScoreDetailService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class BlScoreDetailController extends BaseDataController<BlScoreDetailService,BlScoreDetail> {

    /**
     * 分页查找
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/findListPage/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findListPage(@RequestParam(required = false) Map<String, Object> param, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){

        return ResponseResult.ok(service.findListPage(new Page<BlScoreDetail>(pageNo, pageSize), param));
    }


    /**
     * 获取单个字典
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }



    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody BlScoreDetail blScoreDetail, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, blScoreDetail)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        int rs=service.save(blScoreDetail);
        return 1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("保存积分信息失败");
    }

    /**
     * 获取当前用户积分明细
     * @param blScoreDetail
     * @return
     */
    @RequestMapping(value = "/getScoreDetailList/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult getScoreDetailList(BlScoreDetail blScoreDetail, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize) {
        blScoreDetail.setCurrentUser();
        blScoreDetail.setUserId(blScoreDetail.getCurrentId());
        return service.getScoreDetailList(new Page<BlScoreDetail>(pageNo, pageSize), blScoreDetail);
    }

    /**
     * 获取当前用户总积分
     * @param blScoreDetail
     * @return
     */
    @RequestMapping(value = "/getScoreByCurrentUser", method = RequestMethod.GET)
    public ResponseResult getScoreByCurrentUser(BlScoreDetail blScoreDetail) {
        ResponseResult responseResult = ResponseResult.ok();
        blScoreDetail.setCurrentUser();
        responseResult.put("sumScore",service.getScoreByUserId(blScoreDetail.getCurrentId()));
        return responseResult;
    }

    /**
     * 获取指定用户总积分
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getScoreByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult getScoreByCurrentUser(@PathVariable Long userId) {
        ResponseResult responseResult = ResponseResult.ok();
        responseResult.put("sumScore",service.getScoreByUserId(userId));
        return responseResult;
    }


    @RequestMapping(value = "/addScore", method = RequestMethod.POST)
    public ResponseResult addScore(@RequestBody BlScoreDetail blScoreDetail) throws OptimisticLockException {
        service.addScore(blScoreDetail);
        return  ResponseResult.ok(null) ;
    }

    @RequestMapping(value = "/consumeScore", method = RequestMethod.POST)
    public ResponseResult consumeScore(@RequestBody BlScoreDetail blScoreDetail) throws OptimisticLockException {
        service.consumeScore(blScoreDetail);
        return ResponseResult.ok(null) ;
    }

    /**
     * 获取指定用户总积分
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getScoreByPersonCouponId/{couponId}", method = RequestMethod.GET)
    public ResponseResult getScoreByPersonCouponId(@PathVariable Long couponId) {
        ResponseResult responseResult = ResponseResult.ok();
        responseResult.put("sumScore",service.getScoreByPersonCouponId(couponId));
        return responseResult;
    }

}
