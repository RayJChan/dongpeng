package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Company;
import com.dongpeng.entity.system.UserCompany;
import com.dongpeng.system.service.CompanyService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 公司管理接口controller
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseDataController<CompanyService,Company> {
    //@Autowired
    //private CompanyService service;

    /**
     * 分页查找公司列表
     * @param company 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Company company, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<Company>(pageNo, pageSize), company));
    }

    /**
     * 获取单个公司，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 获取单个公司，根据公司编码
     * @param companyCode
     * @return
     */
    @RequestMapping(value = "/getByCode/{companyCode}", method = RequestMethod.GET)
    public ResponseResult getByCompanyCode(@PathVariable("companyCode")String companyCode){
        return  ResponseResult.ok(service.getByCompanyCode(companyCode)) ;
    }

    /**
     * 据公司名称 获取公司（含模糊查询）
     * @return
     */
    @RequestMapping(value = "/getByCompanyName/{companyName}", method = RequestMethod.GET)
    public ResponseResult getByCompanyName(@PathVariable("companyName")String companyName){
        return  ResponseResult.ok(service.getByCompanyName(companyName)) ;
    }


    /**
     * 新增一个公司
     * @param company 封装公司数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Company company, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, company)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Company companyTemp=service.getByCompanyCode(company.getCompanyCode());
        if(null!=companyTemp){
            return ResponseResult.failByParam("公司编码已存在");
        }

        int rs=service.save(company);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存公司失败");
        }
    }

    /**
     * 更新一个公司信息
     * @param company 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = CompanyService.class,handleName = "更新一个公司信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Company company, HttpServletRequest request, Model model) throws Exception {
        if(null==company.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Company companyTemp=service.get(company.getId());

        if(null==companyTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新编号和旧编号不相等情况下，要判断新编号是否会重复
        if(StringUtils.isNotBlank(company.getCompanyCode()) && !companyTemp.getCompanyCode().equals(company.getCompanyCode())){
            Company companyByCode=service.getByCompanyCode(company.getCompanyCode());
            if(null!=companyByCode){
                return ResponseResult.failByParam("公司编码已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(company, companyTemp);//将company非NULL值覆盖companyTemp中的值

        int rs=service.save(companyTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }
        else{
            //return ResponseResult.failByBusiness("更新公司失败");
            return checkVersion(companyTemp);
        }*/
    }


    /**
     * 停用/启用 一个公司
     * <p>实际为逻辑删除</p>
     * @param id 公司id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new Company(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用公司失败":"停用公司失败");
        }
    }

    /**
     * 物理删除一个公司
     * @param id 公司id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Company(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除公司失败");
        }
    }*/

    /**
     * 批量物理删除公司
     * @param ids 公司id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Company(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

    /**
     * 删除用户公司
     * @param userId 用户id
     * @param companyIds 公司id集合，多个用逗号分隔
     * @return
     */
    @RequestMapping(value = "/deleteUserCompany/{userId}", method = RequestMethod.POST)
    public ResponseResult deleteUserCompany(@PathVariable("userId") Long userId,@RequestParam(required = true) String companyIds){
        String idArray[] =companyIds.split(",");
        for(String companyId : idArray){
            service.deleteUserCompany(new UserCompany(userId,Long.valueOf(companyId)));
        }
        return ResponseResult.ok(null);
    }

    /**
     * 插入用户公司
     * @param userId 用户id
     * @param companyIds 公司id集合，多个用逗号分隔
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertUserCompany/{userId}", method = RequestMethod.POST)
    public ResponseResult insertUserCompany(@PathVariable("userId") Long userId
            ,@RequestParam(required = true) String companyIds
            , HttpServletRequest request){
        int rs=service.insertUserCompany(userId,companyIds);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入用户公司失败");
        }
    }

    /**
     * 根据用户id查找公司信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListByUserId(@PathVariable("userId") Long userId){
        return ResponseResult.ok(service.findListByUserId(new UserCompany(userId)));
    }

    /**
     * 根据用户id查找该用户 未有的 公司信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listNotInUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListNotInUserId(@PathVariable("userId") Long userId){
        return ResponseResult.ok(service.findListNotInUserId(new UserCompany(userId)));
    }

}
