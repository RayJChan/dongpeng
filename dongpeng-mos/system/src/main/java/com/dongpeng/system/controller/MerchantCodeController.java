package com.dongpeng.system.controller;


import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.ExportExcelUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.ReflectionsUtils;
import com.dongpeng.entity.system.ClientRecord;
import com.dongpeng.entity.system.MerchantCode;
import com.dongpeng.entity.system.Product;
import com.dongpeng.system.service.MerchantCodeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/merchantCode")
public class MerchantCodeController extends BaseDataController<MerchantCodeService, MerchantCode> {

    /**
     * 分页查找商家编码列表
     * @param merchantCode 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return ResponseResult
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(MerchantCode merchantCode, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<MerchantCode>(pageNo, pageSize,-1), merchantCode));
    }

    /**
     *添加数据
     * @param merchantCode 封装的数据
     * @param request
     * @param model
     * @return
     * @throws OptimisticLockException
     */

    @Override
    public ResponseResult add(@RequestBody MerchantCode merchantCode, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, merchantCode)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(merchantCode.getMerchantCode() == null || "".equals(merchantCode.getMerchantCode())){
            return ResponseResult.failByParam("商家编码不能为空");
        }
        if(merchantCode.getConversionLogicname() == null || "".equals(merchantCode.getConversionLogicname())){
            return ResponseResult.failByParam("换算逻辑不能为空");
        }
        if(merchantCode.getProductCode() == null || "".equals(merchantCode.getProductCode())){
            return ResponseResult.failByParam("商品编码不能为空");
        }
        return service.insert(merchantCode);
    }

    /**
     *修改数据
     * @param merchantCode
     * @param request
     * @param model
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult update(@RequestBody MerchantCode merchantCode, HttpServletRequest request, Model model) throws Exception {
        if(!beanValidator(model, merchantCode)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(merchantCode.getId() == null){
            return ResponseResult.failByParam("数据id不能为空");
        }
        if(merchantCode.getMerchantCode() == null || "".equals(merchantCode.getMerchantCode())){
            return ResponseResult.failByParam("商家编码不能为空");
        }
        if(merchantCode.getConversionLogicname() == null || "".equals(merchantCode.getConversionLogicname())){
            return ResponseResult.failByParam("换算逻辑不能为空");
        }
        if(merchantCode.getProductCode() == null || "".equals(merchantCode.getProductCode())){
            return ResponseResult.failByParam("商品编码不能为空");
        }
        return service.update(merchantCode);
    }

    /**
     *
     * @param file 客户端上传的Excel文件
     * @return
     */
    @Override
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName = new MerchantCode().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failMsg = new StringBuilder("导入失败信息:");
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<MerchantCode> list = ei.getDataList(MerchantCode.class);
            System.out.println(list.size()+"!!!!!!!!!!!!!!!!!!!!!!");
            //####### 批量插入 ############

            for(int i=0;i<list.size();i++){
                if(list.get(i).getProductCode() == null){
                    failMsg.append("第"+(i+1)+"条商品编码["+list.get(i).getProductCode()+"]为空");
                    failureNum ++;
                    continue;
                }
                if(list.get(i).getMerchantCode() == null){
                    failMsg.append("第"+(i+1)+"条商家编码["+list.get(i).getMerchantCode()+"]为空");
                    failureNum ++;
                    continue;
                }
                Product product = service.getProduct(list.get(i).getProductCode());
                if(product == null){
                    failMsg.append("第"+(i+1)+"条商品编码["+list.get(i).getProductCode()+"]无效");
                    failureNum ++;
                    continue;
                }
                list.get(i).setProductCode(product.getCommodityCode());
                try{
                    successNum+=service.saveForExcel(list.get(i));
                }catch (Exception e){
                    logger.error("excel导入出错,第"+(i+1)+"个: ", e.getCause());
                }

            }

            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条"+dataTableName+"记录。");
            }
            return ResponseResult.ok("已成功导入 "+successNum+" 条"+dataTableName+"记录;"+failureMsg+failMsg);
        } catch (Exception e) {
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息："+e.getMessage());
        }
    }

}
