package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.ReflectionsUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.system.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 字典项接口controller
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseDataController<DictionaryService,Dictionary> {

    /*@Autowired
    private DictionaryService service;*/
    @RequestMapping("/test")
    public ResponseResult test(@RequestParam(name = "code") String code){
        return ResponseResult.ok(DictUtils.getByCode(code));
    }

    /**
     * 分页查找字典列表
     * @param dictionary 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Dictionary dictionary, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(1==pageNo){
            return ResponseResult.ok(service.findPage(new Page<Dictionary>(pageNo, pageSize), dictionary));
        }else{
            return ResponseResult.ok(service.findPage(new Page<Dictionary>(pageNo, pageSize,-1), dictionary));
        }
    }

    /**
     * 不分页查找字典列表
     * @param dictionary 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult findAll(Dictionary dictionary){
        return ResponseResult.ok(service.findList(dictionary));
    }

    /**
     * 获取单个字典，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 新增一个字典
     * @param dictionary 封装字典数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody Dictionary dictionary, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, dictionary)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Dictionary dictionaryTemp=service.getByCode(dictionary.getDetailCode());
        if(null!=dictionaryTemp){
            return ResponseResult.failByParam("字典code已存在");
        }
        dictionaryTemp=service.getByNameAndDetail(dictionary.getDictionaryName(), dictionary.getDetailName());
        if(null!=dictionaryTemp){
            return ResponseResult.failByParam("字典已存在");
        }

        int rs=service.save(dictionary);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存字典失败");
        }
    }

    /**
     * 更新一个字典信息
     * @param dictionary 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = DictionaryService.class,handleName = "更新一个字典信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody Dictionary dictionary, HttpServletRequest request, Model model) throws Exception {
        if(null==dictionary.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Dictionary dictionaryTemp=service.get(dictionary.getId());

        if(null==dictionaryTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //code不同，判断是否重复
        if(StringPlusUtils.isNotBlank(dictionary.getDetailCode())
                && !dictionary.getDetailCode().equals(dictionaryTemp.getDetailCode())){
            if(null!=service.getByCode(dictionary.getDetailCode())){
                return ResponseResult.failByParam("字典code已存在");
            }
        }

        //名称和明细 同时不同，判断是否重复
        if( (StringPlusUtils.isNotBlank(dictionary.getDetailName()) && StringPlusUtils.isNotBlank(dictionary.getDictionaryName()) )
                && (!dictionary.getDetailName().equals(dictionaryTemp.getDetailName())
                && !dictionary.getDictionaryName().equals(dictionaryTemp.getDictionaryName())  ) ){
            if(null!=service.getByNameAndDetail(dictionary.getDictionaryName(), dictionary.getDetailName()) ){
                return ResponseResult.failByParam("字典已存在");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(dictionary, dictionaryTemp);//将dictionary非NULL值覆盖dictionaryTemp中的值

        int rs=service.save(dictionaryTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新字典失败");
            return checkVersion(dictionaryTemp);
        }*/

    }


    /**
     * 停用/启用 一个字典
     * <p>实际为逻辑删除</p>
     * @param id 字典id
     * @param deleteFlag true/1：停用；false/0：启用
     * @return
     */
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new Dictionary(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用字典失败":"停用字典失败");
        }
    }

    /**
     * 物理删除一个字典
     * @param id 字典id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new Dictionary(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除字典失败");
        }
    }*/

    /**
     * 批量物理删除字典
     * @param ids 字典id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new Dictionary(Long.valueOf(id)));
        }
        return ResponseResult.ok(null);
    }*/

    /**
     * 导入Excel数据
     * @param file 客户端上传的Excel文件
     * @return
     */
    @RequestMapping(value = "/import", method=RequestMethod.POST)
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName=new Dictionary().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<Dictionary> list = ei.getDataList(Dictionary.class);
            //####### 批量插入 ############
            for(int i=0;i<list.size();i++){
                try{
                    int rs=service.save(list.get(i));
                    if(1<=rs){
                        successNum+=1;
                    }else{
                        failureMsg.append(" 第"+(i+1)+"个导入出错 ");
                    }
                }catch (Exception e){
                    logger.error("excel导入出错,第"+(i+1)+"个: ", e.getCause());
                    failureMsg.append(" 第"+(i+1)+"个导入出错 ");
                }
            }
            failureNum=list.size()-successNum;

            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条"+dataTableName+"记录。");
            }
            return ResponseResult.ok("已成功导入 "+successNum+" 条"+dataTableName+"记录"+failureMsg);
        } catch (Exception e) {
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息："+e.getMessage());
        }
    }

}
