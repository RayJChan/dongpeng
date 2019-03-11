package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.BlRank;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.system.service.BlRankService;
import com.dongpeng.system.service.DictionaryService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/blrank")
public class BlRankController extends BaseDataController<BlRankService,BlRank> {


    /**
     * 分页查找职级列表
     * @param blRank 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @Override
    @RequestMapping(value = "/list/{"+ Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(BlRank blRank, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(null == blRank.getEnable()){
            blRank.setEnable(Boolean.TRUE);
        }
        return ResponseResult.ok(service.findPage(new Page<BlRank>(pageNo, pageSize), blRank));
    }

    /**
     * 不分页查找职级列表
     * @param blRank 封装查询参数
     * @return
     */
    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(BlRank blRank){
        if(null == blRank.getEnable()){
            blRank.setEnable(Boolean.TRUE);
        }
        return ResponseResult.ok(service.findList(blRank));
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


    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody BlRank blRank, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, blRank)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        int rs=service.save(blRank);
        return 1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("保存职级信息失败");
    }


    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody BlRank blRank, HttpServletRequest request, Model model) throws Exception {

        if(!beanValidator(model, blRank)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        return service.update(blRank);
    }

    /**
     * 启用或者停用接口
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeEnable/{id}/{enable}", method = RequestMethod.POST)
    public ResponseResult enable(@PathVariable String id,@PathVariable int enable,HttpServletRequest request, Model model) throws Exception {
        if(!(enable==0 || enable==1)){
            return ResponseResult.failByBusiness("enable：参数异常");
        }
        BlRank blRankTemp= null;
        if(StringUtils.isNotBlank(id)){
            blRankTemp=service.get(Long.parseLong(id));
        }
        if(null==blRankTemp){
            return ResponseResult.failByParam("id 无效");
        }
        blRankTemp.setEnable(BooleanUtils.toBoolean(enable,1,0));
        int rs = service.updateEnable(blRankTemp);
        return rs > 0  ? ResponseResult.ok(null) : ResponseResult.failByBusiness("操作失败");
    }

    @Override
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new BlRank(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用菜单失败":"停用菜单失败");
        }
    }

}
