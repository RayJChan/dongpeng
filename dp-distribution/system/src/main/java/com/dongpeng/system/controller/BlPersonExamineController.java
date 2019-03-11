package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.BlPersonExamine;
import com.dongpeng.entity.system.BlPersonExamineInfo;
import com.dongpeng.system.service.BlPersonExamineService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/personExamine")
public class BlPersonExamineController  extends BaseDataController<BlPersonExamineService,BlPersonExamine> {


    /**
     * 分页查找
     * @param blPersonExamine 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @Override
    @RequestMapping(value = "/list/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(BlPersonExamine blPersonExamine, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<BlPersonExamine>(pageNo, pageSize), blPersonExamine));
    }


    /**
     * 分页查找
     * @param blPersonExamineInfo 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/findPageCouponExamine/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(BlPersonExamineInfo blPersonExamineInfo, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findListPersonExaminePage(new Page<BlPersonExamineInfo>(pageNo, pageSize), blPersonExamineInfo));
    }

    /**
     * 获取单个
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
    public ResponseResult add(@RequestBody BlPersonExamine blPersonExamine, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, blPersonExamine)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        int rs=service.save(blPersonExamine);
        return 1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("提交申请失败");
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody BlPersonExamine blPersonExamine , HttpServletRequest request, Model model) throws Exception {
        if(!beanValidator(model, blPersonExamine)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        int rs = service.update(blPersonExamine);
        return rs==1 ? ResponseResult.ok(null) : ResponseResult.failByBusiness("更新信息失败");
    }

    @Override
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new BlPersonExamine(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除失败");
        }
    }

}
