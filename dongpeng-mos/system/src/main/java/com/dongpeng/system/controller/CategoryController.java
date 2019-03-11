package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.Category;
import com.dongpeng.system.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseDataController<CategoryService,Category> {

    /**
     * 分页查找品类列表
     * @param category 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Category category, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<Category>(pageNo, pageSize,-1), category));
    }

    /**
     * 不分页查找品类列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(Category category){
        return ResponseResult.ok(service.findList(category));
    }

    /**
     * 获取单个品类，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 获取单个品类，根据code查询
     * @param categoryCode
     * @return
     */
    @RequestMapping(value = "/get/{categoryCode}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("categoryCode") String categoryCode) {
        return ResponseResult.ok(service.findUniqueByProperty("category_code",categoryCode));
    }


    /**
     * 新增一个品类
     * @param category 封装品类数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Category category, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, category)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(null!=service.findUniqueByProperty("category_code",category.getCategoryCode())){
            return ResponseResult.failByParam("该品类已存在，无法添加");
        }
        int rs=service.save(category);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存品类失败");
        }
    }

    /**
     * 更新一个品类信息
     * @param category 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = CategoryService.class,handleName = "更新一个品类信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Category category, HttpServletRequest request, Model model) throws Exception {
        if(null==category.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Category breedTemp=service.get(category.getId());

        if(null==breedTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新编号和旧编号不相等情况下，要判断新编号是否会重复
        if(StringUtils.isNotBlank(category.getCategoryCode()) && !breedTemp.getCategoryCode().equals(category.getCategoryCode())){
            Category categoryCode = service.findUniqueByProperty("category_code",category.getCategoryCode());
            if(null!=categoryCode){
                return ResponseResult.failByParam("品类编码已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(category, breedTemp);//将breed非NULL值覆盖breedTemp中的值

        int rs=service.save(breedTemp);
        return ResponseResult.ok(null);
    }



}
