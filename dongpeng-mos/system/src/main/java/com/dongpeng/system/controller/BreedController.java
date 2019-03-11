package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.Breed;
import com.dongpeng.system.service.BreedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/breed")
public class BreedController extends BaseDataController<BreedService,Breed> {

    /**
     * 分页查找品类列表
     * @param breed 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Breed breed, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<Breed>(pageNo, pageSize,-1), breed));
    }

    /**
     * 不分页查找品类列表
     * @param breed 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(Breed breed){
        return ResponseResult.ok(service.findList(breed));
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
     * 新增一个品类
     * @param breed 封装品类数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Breed breed, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, breed)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Breed breedTemp=service.getByBreedCode(breed.getBreedCode());
        if(null!=breedTemp){
            return ResponseResult.failByParam("品类编码已存在");
        }

        int rs=service.save(breed);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存品类失败");
        }
    }

    /**
     * 更新一个品类信息
     * @param breed 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = BreedService.class,handleName = "更新一个品类信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Breed breed, HttpServletRequest request, Model model) throws Exception {
        if(null==breed.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Breed breedTemp=service.get(breed.getId());

        if(null==breedTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新编号和旧编号不相等情况下，要判断新编号是否会重复
        if(StringUtils.isNotBlank(breed.getBreedCode()) && !breedTemp.getBreedCode().equals(breed.getBreedCode())){
            Breed breedByCode=service.getByBreedCode(breed.getBreedCode());
            if(null!=breedByCode){
                return ResponseResult.failByParam("品类编码已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(breed, breedTemp);//将breed非NULL值覆盖breedTemp中的值

        int rs=service.save(breedTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新品类失败");
            return checkVersion(breedTemp);
        }*/
    }


    /**
     * 停用/启用 一个品类
     * <p>实际为逻辑删除</p>
     * @param id 品类id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new Breed(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用品类失败":"停用品类失败");
        }
    }

    /**
     * 物理删除一个品类
     * @param id 品类id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Breed(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除品类失败");
        }
    }*/

    /**
     * 批量物理删除品类
     * @param ids 品类id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Breed(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/
}
