package com.dongpeng.common.db.contorller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.ExportExcelUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.ReflectionsUtils;
import com.dongpeng.common.web.BaseController;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 基础数据抽象controller
 * <ul>
 *     <li>需要访问数据库的controller可以继承此抽象类</li>
 *     <li>继承该类后将拥有基本的对外增删改查接口</li>
 * </ul>
 * @param <S>  controller 需要注入的 主要 service class
 * @param <T> entity
 */
public abstract class BaseDataController<S extends BaseCrudService, T extends DataEntity<T>> extends BaseController {

    /**
     * 服务层对象
     */
    @Autowired
    protected S service;

    /**
     * 判断是否版本号导致更新失败，并返回ResponseResult的错误提示
     * @param entity 需要与数据库对比的entity
     * @return ResponseResult
     */
    @Deprecated
    protected ResponseResult checkVersion( T entity) {
        if(service.compareVersion(entity)){
            return ResponseResult.failByBusiness("更新失败");
        }else{
            return ResponseResult.failByBusiness(entity.getModifierName()+" 用户正在操作该数据，请稍后再试");
        }

    }

    /**
     * 分页查找列表
     * @param t 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(T t, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(pageNo==1){
            //页码为1时查找总页数等数据
            return ResponseResult.ok(service.findPage(new Page<T>(pageNo, pageSize), t));
        }else{
            //页码非1时不查找总页数等数据
            return ResponseResult.ok(service.findPage(new Page<T>(pageNo, pageSize,-1), t));
        }
    }

    /**
     * 不分页查找列表
     * @param t 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(T t){
        return ResponseResult.ok(service.findList(t));
    }

    /**
     * 获取单个，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个entity
     * @param t 封装entity数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody T t, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, t)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        int rs=service.save(t);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    /**
     * 更新一个entity信息
     * <p>说明：如需要记录修改的字段值，请Override重写该方法，并加上EnableDetailLog注解</p>
     * @param t 封装需要更新的信息
     * @return
     * @throws OptimisticLockException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody T t, HttpServletRequest request, Model model) throws Exception {
        if(null==t.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        T temp= (T) service.get(t.getId());

        if(null==temp){
            return ResponseResult.failByParam("id 无效");
        }

        BeanUtils.copyBeanNotNull2Bean(t, temp);//将非NULL值覆盖temp中的值

        service.save(temp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return checkVersion(temp);
        }*/
    }

    /**
     * 批量新增entity
     * @param list 封装entity数据列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public ResponseResult insertBatch(@RequestBody List<T> list, HttpServletRequest request, Model model){
        int rs=service.insertBatch(list);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    /**
     * 停用/启用 一个entity
     * <p>实际为逻辑删除</p>
     * @param id entityid
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag) throws IllegalAccessException, InstantiationException {
        T t = (T) ReflectionsUtils.getClassGenricType(this.getClass(), 1).newInstance();
        t.setDeleteFlag(deleteFlag);
        t.setId(id);
        int rs=service.deleteToggle(t);
        if(0<rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"停用失败":"启用失败");
        }
    }

    /**
     * 物理删除一个entity
     * @param id entity id
     * @return
     */
   /* @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id) throws IllegalAccessException, InstantiationException {
        T t = (T) ReflectionsUtils.getClassGenricType(this.getClass(), 1).newInstance();
        t.setId(id);
        int rs=service.delete(t);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除失败");
        }
    }*/

    /**
     * 批量物理删除entity
     * @param ids entity id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids) throws IllegalAccessException, InstantiationException {
        String idArray[] =ids.split(",");
        for(String id : idArray){
            T t = (T) ReflectionsUtils.getClassGenricType(this.getClass(), 1).newInstance();
            t.setId(Long.valueOf(id));
            service.delete(t);
        }
        return ResponseResult.ok(null);
    }*/

    /**
     * 下载导入数据模板
     * @param response
     * @return
     */
    @RequestMapping(value = "/import/template")
    public ResponseResult importFileTemplate(HttpServletResponse response) {
        try {
            String dataTableName=((T) ReflectionsUtils.getClassGenricType(this.getClass(), 1).newInstance()).getDataTableName();
            String fileName = dataTableName+"数据导入模板.xlsx";
            List<T> list = Lists.newArrayList();
            new ExportExcelUtils(dataTableName+"数据", ReflectionsUtils.getClassGenricType(this.getClass(), 1), 2)
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导入模板下载失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入模板下载失败！失败信息："+e.getMessage());
        }
    }

    /**
     * 导入Excel数据
     * @param file 客户端上传的Excel文件
     * @return
     */
    @RequestMapping(value = "/import", method=RequestMethod.POST)
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName=((T) ReflectionsUtils.getClassGenricType(this.getClass(), 1).newInstance()).getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<T> list = ei.getDataList(ReflectionsUtils.getClassGenricType(this.getClass(),1));
            //####### 批量插入 ############
            for(int i=0;i<=list.size();i=i+50){
                try{
                    successNum+=service.insertBatch(list.subList(i, i+50>=list.size()?list.size():i+50));
                }catch (Exception e){
                    logger.error("excel导入出错,第"+(i+1)+"个: ", e.getCause());
                    //failureMsg.append(e.getCause());
                }

            }
            failureNum=list.size()-successNum;
            //####### 批量插入 ############

            //####### 逐条插入 ############
            /*for (T t : list){
                try{
                    service.save(t);
                    successNum++;
                }catch(ConstraintViolationException ex){
                    failureNum++;
                }catch (Exception ex) {
                    failureNum++;
                }
            }*/
            //####### 逐条插入 ############

            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条"+dataTableName+"记录。");
            }
            return ResponseResult.ok("已成功导入 "+successNum+" 条"+dataTableName+"记录"+failureMsg);
        } catch (Exception e) {
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息："+e.getMessage());
        }
    }

    /**
     * 导出excel文件
     * @param t 封装查询参数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/export", method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseResult exportFile(T t, HttpServletRequest request, HttpServletResponse response) {
        try {
            String dataTableName=((T) ReflectionsUtils.getClassGenricType(this.getClass(), 1).newInstance()).getDataTableName();
            String fileName = dataTableName+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<T> list=service.findList(t);
            new ExportExcelUtils(dataTableName, ReflectionsUtils.getClassGenricType(this.getClass(), 1))
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息："+e.getMessage());
        }
    }
}
