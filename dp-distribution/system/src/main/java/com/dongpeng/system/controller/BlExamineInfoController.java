package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.entity.system.BlExamineCouponInfo;
import com.dongpeng.entity.system.BlExamineInfo;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.system.service.BlExamineInfoService;
import com.google.common.base.Preconditions;
import net.bytebuddy.implementation.bytecode.collection.ArrayAccess;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 优惠券审核接口
 */
@RestController
@RequestMapping("/examineInfo")
public class BlExamineInfoController extends BaseDataController<BlExamineInfoService,BlExamineInfo> {

    /**
     * 分页查找列表
     * 所有审核信息查询
     * @param BlExamineInfo 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @Override
    @RequestMapping(value = "/list/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(BlExamineInfo BlExamineInfo, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<BlExamineInfo>(pageNo, pageSize), BlExamineInfo));
    }

    /**
     * 获取优惠券待审核信息
     * @param blExamineCouponInfo 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/listCouponExamine/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method ={RequestMethod.GET,RequestMethod.POST})
    public ResponseResult findPageCouponExamine(BlExamineCouponInfo blExamineCouponInfo, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        blExamineCouponInfo.setCurrentUser();
        blExamineCouponInfo.setNextUserId(blExamineCouponInfo.getCurrentId());//只查询当前用户
        blExamineCouponInfo.setBusinessTypeId(DictUtils.getByCode(Global.COUPON_EXAMINE).getId());
        blExamineCouponInfo.setNotExamineResultId(Arrays.asList(DictUtils.getByCode(Global.REVIEW_FAILURE).getId(),DictUtils.getByCode(Global.SUBMIT).getId()));//不查出 审核失败数据
        return ResponseResult.ok(service.findPageCouponExamine(new Page<BlExamineCouponInfo>(pageNo, pageSize), blExamineCouponInfo));
    }

    /**
     * 不分页查找列表
     * @param BlExamineInfo 封装查询参数
     * @return
     */
    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(BlExamineInfo BlExamineInfo){
        return ResponseResult.ok(service.findList(BlExamineInfo));
    }


    /**
     * 获取单个字典，根据id
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }


    /**
     * 提交
     * @param blExamineInfo
     * @param request
     * @param model
     * @return
     * @throws OptimisticLockException
     */
    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody BlExamineInfo blExamineInfo, HttpServletRequest request, Model model) throws OptimisticLockException {
        Preconditions.checkNotNull(blExamineInfo.getNextUserId(),"提交到审核人不不能为空");
        int rs = service.submit(blExamineInfo);
        return 1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("提交成功");
    }


    /**
     * type:1 审核成功
     * type：0 审核失败
     * @param BlExamineInfo
     * @param type
     * @param request
     * @param model
     * @return
     * @throws OptimisticLockException
     */
    @RequestMapping(value = "/check/{type}", method = RequestMethod.POST)
    public ResponseResult check(@RequestBody BlExamineInfo BlExamineInfo,@PathVariable int type, HttpServletRequest request, Model model) throws Exception {
        if(null== BlExamineInfo.getId()){
            return ResponseResult.failByBusiness("ID 不能为空 ");
        }
        return service.couponCheck(BlExamineInfo,type);
    }

}
