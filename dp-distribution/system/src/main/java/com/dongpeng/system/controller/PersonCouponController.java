package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.ExportExcelUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.service.CouponService;
import com.dongpeng.system.service.DictionaryService;
import com.dongpeng.system.service.PersonCouponService;
import com.dongpeng.system.service.UserService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

* @Description:    优惠券详情控制

* @Author:         xc

* @CreateDate:     2018/11/1 15:48

* @UpdateUser:     xc

* @UpdateDate:     2018/11/1 15:48

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@RestController
@RequestMapping("/personcoupon")
public class PersonCouponController extends BaseController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private PersonCouponService personCouponService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private UserService userService;

    /**

    * 分页查询优惠券详情

    * @param

    * @return

    * @exception

    * @date        2018/11/1 15:49

    */
    @RequestMapping(value = "/list/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(PersonCoupon personCoupon, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(pageNo==1){
            personCoupon.setPage(new Page<PersonCoupon>(pageNo, pageSize));

        }else{
            //页码非1时不查找总页数等数据
            personCoupon.setPage(new Page<PersonCoupon>(pageNo, pageSize,-1));
        }
        return personCouponService.getPersonCouponsByPage(personCoupon);
    }

    @RequestMapping(value = "/mycoupons/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.POST)
    public ResponseResult myCoupons(@RequestBody CouponRequest couponRequest, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        couponRequest.setPage(new Page<>(pageNo,pageSize,-1));
        return personCouponService.findMyCoupon(couponRequest);
    }

    /**

    * 导出优惠券

    * @param personCoupon

    * @return

    * @exception

    * @date        2018/11/1 15:50

    */
    @RequestMapping("/export")
    public ResponseResult dataExport(PersonCoupon personCoupon , HttpServletResponse response){
        String dataTableName = personCoupon.getDataTableName();
        try {
            new ExportExcelUtils(dataTableName,personCoupon.getClass()).setDataList(personCouponService.findList(personCoupon)).write(response,dataTableName+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
            return null;
        } catch (IOException e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息："+e.getMessage());
        }
    }

    /**

    * 领取公共优惠券

    * @param couponId

    * @return

    * @exception

    * @date        2018/11/1 15:52

    */
    @RequestMapping("/receivecoupon/{couponId}")
    public ResponseResult receiveCoupon(@PathVariable Long couponId) throws OptimisticLockException, InterruptedException {
        Coupon coupon = couponService.get(couponId);
        Preconditions.checkNotNull(coupon);
        Dictionary couponSource = dictionaryService.get(coupon.getSourceId());
        Preconditions.checkState(couponSource.getDetailCode().equals(Global.COUPON_SOURCE_PUBLIC),"无法领取非公共优惠券");
        return personCouponService.receiveCoupon(couponId,userService.getCurrentUser(),null);
    }

    /**

    * 通过分享链接领取优惠券

    * @param shareToken

    * @return

    * @exception

    * @date        2018/11/1 15:53

    */
    @RequestMapping(value = "/receicecouponfromsharetoken/{shareToken}",method = RequestMethod.GET)
    public ResponseResult receiceCouponFromShareToken(@PathVariable String shareToken) throws OptimisticLockException, InterruptedException {
        return personCouponService.receiveCouponFromShare(shareToken);
    }

    @RequestMapping(value = "/generatewriteoffcode/{personcouponid}",method = RequestMethod.GET)
    public ResponseResult generateWriteoffCode(@PathVariable Long personcouponid){
        return personCouponService.generateWriteoffCode(personcouponid);
    }

    @RequestMapping(value = "/verifywriteoffcode/{writeoffCode}",method = RequestMethod.GET)
    public ResponseResult verifyWriteoffCode(@PathVariable String writeoffCode){
        return personCouponService.verifyWriteoffCode(writeoffCode);
    }

    @RequestMapping(value = "/writeoff",method = RequestMethod.POST)
    public ResponseResult writeoff(@RequestBody WriteoffRequest request) throws InterruptedException, OptimisticLockException {
        return personCouponService.writeoff(request);
    }

    @RequestMapping("/getwriteoffresult/{personCouponId}")
    public ResponseResult getWriteoffResult(@PathVariable Long personCouponId){
        return personCouponService.getWriteoffResult(personCouponId);
    }

    @PostMapping("/getwriteoffinfo")
    public ResponseResult getWriteoffInfo(@RequestBody WriteoffRequest request){
        return personCouponService.getWriteoffInfo(request);
    }

    @PostMapping("/listwriteoff/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}")
    public ResponseResult listWriteOff(@RequestBody WriteoffRequest request,  @PathVariable(Global.PAGE_NO)Long pageNo, @PathVariable(Global.PAGE_SIZE)Long pageSize){
        Preconditions.checkArgument(pageNo>=1,"错误的页码");
        Preconditions.checkArgument(pageSize>0,"错误的页码大小");
        request.setLimitIndex((pageNo-1)*pageSize);
        request.setLimitCount(pageSize);
        return personCouponService.listWriteOff(request);
    }

    @GetMapping("/getwriteoffinfodetail/{personCouponId}")
    public ResponseResult getwriteoffinfodetail(@PathVariable Long personCouponId){
        return personCouponService.getWriteoffInfoDetail(personCouponId);
    }

}
