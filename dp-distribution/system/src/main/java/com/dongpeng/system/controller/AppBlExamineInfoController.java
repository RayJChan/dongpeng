package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.BlExamineInfo;
import com.dongpeng.entity.system.MyApplyCoupon;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.service.BlExamineInfoService;
import com.dongpeng.system.service.BlPersonCouponExamineService;
import com.dongpeng.system.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app/examine")
public class AppBlExamineInfoController extends BaseController {


    @Autowired
    private BlPersonCouponExamineService blPersonCouponExamineService;

    @Autowired
    private BlExamineInfoService blExamineInfoService;

    /**
     * 分页查找 我的发起 未完成数据
     * 所有审核信息查询
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/myApplyUnFinished/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findMyApplyCouponUnfinished(@PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){

        return ResponseResult.ok(blPersonCouponExamineService.findMyApplyCouponUnfinished(new Page<Map<String,Object>>(pageNo,pageSize)));
    }

    /**
     * 分页查找 我的发起 已完成数据
     * 所有审核信息查询
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/myApplyFinished/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findMyApplyCouponFinished(@PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){

        return ResponseResult.ok(blPersonCouponExamineService.findMyApplyCouponFinished(new Page<Map<String,Object>>(pageNo,pageSize)));
    }

    /**
     * 根据类型 获取 优惠券详情 和 审批信息
     * type=coupon 获取优惠券详情 type=personCoupon 获取员工申请优惠券详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCoupon/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(blPersonCouponExamineService.getMyApplyCouponDetail(id));
    }

    /**
     * 我的 已审核
     * @return
     */
    @RequestMapping(value = "/getExamineFinished/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult getMyExamineFinished(@PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize) {
        return ResponseResult.ok(blExamineInfoService.getMyExamineFinished(new Page<Map<String, String>>(pageNo,pageSize)));
    }

    /**
     * 我的 待审核
     * @return
     */
    @RequestMapping(value = "/getExamineUnFinish/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult getMyExamineUnfinish(@PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize) {
        return ResponseResult.ok(blExamineInfoService.getMyExamineUnfinish(new Page<Map<String, String>>(pageNo,pageSize)));
    }

}
