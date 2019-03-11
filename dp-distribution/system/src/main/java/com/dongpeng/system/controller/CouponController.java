package com.dongpeng.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.ExportExcelUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.CouponRequest;
import com.dongpeng.system.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

* @Description:    优惠券controller

* @Author:         xc

* @CreateDate:     2018/10/25 15:26

* @UpdateUser:     xc

* @UpdateDate:     2018/10/25 15:26

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    @Autowired
    private CouponService couponService;

    /**

    * 新增一个优惠券

    * @param coupon 接收一个优惠券

    * @return

    * @exception

    * @date        2018/10/25 15:27

    */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseResult addCoupon(@RequestBody Coupon coupon) throws OptimisticLockException {
        return couponService.addCoupon(coupon);
    }

    @RequestMapping("/get/{couponId}")
    public ResponseResult get(@PathVariable Long couponId){
        return ResponseResult.ok(couponService.get(couponId));
    }
    
    /**
    
    * 分页查询优惠券信息
    
    * @url /list/{pagesize}/{pageNo}
     * pageNo为1时才会返回总条数
     * 
    
    * @return      
    
    * @exception   
    
    * @date        2018/10/25 15:29
    
    */
    @RequestMapping(value = "/list/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Coupon coupon, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(pageNo==1){
            coupon.setPage(new Page<Coupon>(pageNo, pageSize));
            //页码为1时查找总页数等数据
            return couponService.getCouponsByPage(coupon);
        }else{
            //页码非1时不查找总页数等数据
            coupon.setPage(new Page<Coupon>(pageNo, pageSize,-1));
            return couponService.getCouponsByPage(coupon);
        }
    }
    
    /**
    
    * 更新一条优惠券信息
    
    * @param coupon
    
    * @return      
    
    * @exception   
    
    * @date        2018/10/25 15:31
    
    */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseResult updateCoupon(@RequestBody Coupon coupon) throws OptimisticLockException {
        return  couponService.updateCoupon(coupon);
    }
    
    /**
    
    * 激活优惠券 只有优惠券为inactive状态时才能激活
    
    * @param coupon
    
    * @return      
    
    * @exception   
    
    * @date        2018/10/25 15:32
    
    */
    @RequestMapping(value = "/active",method = RequestMethod.POST)
    public ResponseResult couponActive(@RequestBody Coupon coupon){
        return couponService.couponActive(coupon);
    }
    
    /**
    
    * 结束一条优惠券 只有优惠券状态为inactive或者active时才能结束
    
    * @param coupon
    
    * @return      
    
    * @exception   
    
    * @date        2018/10/25 15:33
    
    */
    @RequestMapping(value = "/end",method = RequestMethod.POST)
    public ResponseResult couponEnd(@RequestBody Coupon coupon){
        return couponService.couponEnd(coupon);
    }
    
    /**
    
    * 作废一条优惠券
    
    * @param coupon
    
    * @return      
    
    * @exception   
    
    * @date        2018/10/25 15:34
    
    */
    @RequestMapping(value = "/invalid",method = RequestMethod.POST)
    public ResponseResult couponInvalid(@RequestBody Coupon coupon){
        return couponService.couponInvalid(coupon);
    }

    /**

    * 导出数据

    * @param coupon

    * @return

    * @exception

    * @date        2018/10/29 10:40

    */
    @RequestMapping("/export/{group}")
    public ResponseResult dataExport(@PathVariable int group,Coupon coupon ,HttpServletResponse response){
        String dataTableName = coupon.getDataTableName();
        try {
            new ExportExcelUtils(dataTableName,coupon.getClass(),1,group).setDataList(couponService.findList(coupon)).write(response,dataTableName+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
            return null;
        } catch (IOException e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息："+e.getMessage());
        }
    }

    @RequestMapping(value = "/publiclist/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.POST)
    public ResponseResult findPublicCoupons(@RequestBody CouponRequest couponRequest, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        couponRequest.setPage(new Page<>(pageNo,pageSize,-1));
        return couponService.findPublicCoupons(couponRequest);
    }

    @RequestMapping(value = "/taglist/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.POST)
    public ResponseResult findTagCoupons(@RequestBody CouponRequest couponRequest, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        couponRequest.setPage(new Page<>(pageNo,pageSize,-1));
        return couponService.findTagCoupons(couponRequest);
    }

    @RequestMapping("/getsharetoken/{couponId}")
    public ResponseResult getShareToken(@PathVariable Long couponId){
        return couponService.getShareToken(couponId);
    }

    @RequestMapping("/getsharetokenbytoken/{token}")
    public ResponseResult getShareTokenByToken(@PathVariable String token) throws OptimisticLockException {
        return couponService.getShareTokenByToken(token);
    }

    @RequestMapping("/getsharetokenbyexamine/{examineId}")
    public ResponseResult getShareTokenByExamine(@PathVariable Long examineId){
        return couponService.getShareTokenByExamine(examineId);
    }

    @RequestMapping("/findSharedCoupons")
    public ResponseResult findSharedCoupons(){
        return couponService.findSharedCoupons();
    }

    @RequestMapping("/findShareInvalidCoupons")
    public ResponseResult findShareInvalidCoupons(){
        return couponService.findShareInvalidCoupons();
    }

    @RequestMapping("/findAbleShareCoupons/{pagesize}/{page}")
    public ResponseResult findAbleShareCoupons(@PathVariable Long pagesize,@PathVariable Long page){
        CouponRequest couponRequest = new CouponRequest();
        couponRequest.setLimitCount(pagesize);
        couponRequest.setLimitIndex((page-1)*pagesize);
        return couponService.findAbleShareCoupons(couponRequest);
    }

    @GetMapping("/expireCoupon")
    public ResponseResult expireCoupon(){
        return couponService.expireCoupon();
    }

    @GetMapping("/findReceivedUserFromShare/{couponId}")
    public ResponseResult findReceivedUserFromShare(@PathVariable Long couponId){
        return couponService.findReceivedUserFromShare(couponId);
    }

    @GetMapping("/getbysharetoken/{shareToken}")
    public ResponseResult getByShareToken(@PathVariable String shareToken){
        return couponService.getByShareToken(shareToken);
    }

}
