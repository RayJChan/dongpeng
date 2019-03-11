package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.entity.system.BlPersonCouponExamine;
import com.dongpeng.entity.system.BlPersonCouponExamineInfo;
import com.dongpeng.entity.system.BlRank;
import com.dongpeng.entity.system.MyApplyCoupon;
import com.dongpeng.system.service.BlPersonCouponExamineService;
import com.google.common.base.Preconditions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 用户申请优惠券
 */
@RestController
@RequestMapping("/personCouponExamine")
public class BlPersonCouponExamineController extends BaseDataController<BlPersonCouponExamineService,BlPersonCouponExamine> {



    /**
     * 分页查找
     * @param blPersonCouponExamine 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @Override
    @RequestMapping(value = "/list/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(BlPersonCouponExamine blPersonCouponExamine, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<BlPersonCouponExamine>(pageNo, pageSize), blPersonCouponExamine));
    }


    /**
     * 我申请的优惠券
     * @param myApplyCoupon
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/myApplyCoupon/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult myApplyCouponPage(MyApplyCoupon myApplyCoupon, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        myApplyCoupon.setCurrentUser();
        myApplyCoupon.setPersonId(myApplyCoupon.getCurrentId());
        return ResponseResult.ok(service.findMyApplyCouponPage(new Page<Map<String,Object>>(pageNo, pageSize),myApplyCoupon));
    }



    /**
     * 员工申请优惠券 审核列表
     * @param blPersonCouponExamineInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/perExamineInfo/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPersonAndExamineInfoPage(BlPersonCouponExamineInfo blPersonCouponExamineInfo, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        blPersonCouponExamineInfo.setCurrentUser();
        blPersonCouponExamineInfo.setNextUserId(blPersonCouponExamineInfo.getCurrentId());
        blPersonCouponExamineInfo.setNotExamineResultId(Arrays.asList(DictUtils.getByCode(Global.REVIEW_FAILURE).getId(),DictUtils.getByCode(Global.SUBMIT).getId()));//不查出 审核失败数据
        return ResponseResult.ok(service.findPersonExamineInCouponPage(new Page<BlPersonCouponExamineInfo>(pageNo, pageSize),blPersonCouponExamineInfo));
    }

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody BlPersonCouponExamine blPersonCouponExamine, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, blPersonCouponExamine)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        int rs=service.save(blPersonCouponExamine);
        return 1==rs ? new ResponseResult(ResponseCode.SUCCESS.getCode(),"提交申请成功") : ResponseResult.failByBusiness("提交申请失败");
    }


    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody BlPersonCouponExamine blPersonCouponExamine, HttpServletRequest request, Model model) throws Exception {
        Preconditions.checkNotNull(blPersonCouponExamine.getId(),"id 不能为空");
        BlPersonCouponExamine blPersonCouponExamineTemp = service.get(blPersonCouponExamine.getId());
        Preconditions.checkNotNull(blPersonCouponExamineTemp,"id 无效");
        if(!beanValidator(model, blPersonCouponExamine)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        BeanUtils.copyBeanNotNull2Bean(blPersonCouponExamine, blPersonCouponExamineTemp);
        int rs=service.save(blPersonCouponExamineTemp);
        return 1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("修改失败");
    }

    /**
     * 获取单个，根据id
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }


}
