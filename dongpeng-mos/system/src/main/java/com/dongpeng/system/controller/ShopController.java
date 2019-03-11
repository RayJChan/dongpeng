package com.dongpeng.system.controller;

import com.alibaba.fastjson.JSON;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.*;
import com.dongpeng.entity.system.Shop;
import com.dongpeng.system.service.ShopService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import com.dongpeng.common.entity.ResponseCode;
import java.util.List;


/**
 * 门店管理接口controller
 */
@RestController
@RequestMapping("/shop")
public class ShopController extends BaseDataController<ShopService,Shop> {

    /**
     * 分页查找门店列表
     * @param shop 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return ResponseResult
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(Shop shop, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<Shop>(pageNo, pageSize,-1), shop));
    }

    /**
     * 新增一个门店
     * @param shop 封装门店数据
     * @param model model
     * @return ResponseResult
     */
    @Override
    public ResponseResult add(@RequestBody Shop shop, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, shop)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(shop.getShopCode()==null){
            return ResponseResult.failByParam("门店编码为空");
        }
        Shop shopTemp=service.getByShopCode(shop.getShopCode());
        if(null!=shopTemp){
            return ResponseResult.failByParam("门店编码已存在");
        }
        if(shop.getClientCode() != null){
            Long clientId = service.getClientId(shop.getClientCode());
            if(clientId == null){
                return ResponseResult.failByParam("客编:"+shop.getClientCode()+"为无效数据");
            }
                shop.setClientId(clientId);
        }
        int rs=service.save(shop);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存门店失败");
        }
    }

    /**
     * 更新一个门店信息
     * @param shop 封装需要更新的信息
     * @return ResponseResult
     */
    @Override
    public ResponseResult update(@RequestBody Shop shop, HttpServletRequest request, Model model) throws Exception {
        if(!beanValidator(model, shop)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(null==shop.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Shop temp= service.getById(shop.getId());

        if(null==temp){
            return ResponseResult.failByParam("id 无效");
        }
        if(temp.getDeleteFlag()){
            return ResponseResult.failByParam("id为:"+temp.getId()+"的数据为无效数据,不能修改");
        }
        Shop shopCode = service.getByShopCode(shop.getShopCode());
        if(shopCode != null){
            return ResponseResult.failByParam("门店编码:"+shopCode.getShopCode()+"为已有数据,请修改");
        }
        BeanUtils.copyBeanNotNull2Bean(shop, temp);//将非NULL值覆盖temp中的值

        try {
            service.upShop(temp);
        }catch (Exception e){
            return ResponseResult.fail(ResponseCode.BUSINESS_FAILE.getCode(),"更新失败");
        }

        return ResponseResult.ok(null);

    }

    /**
     * 导入数据
     * @param file 客户端上传的Excel文件
     * @return
     */
    @Override
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName = new Shop().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failMsg = new StringBuilder("导入失败信息:");
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<Shop> list = ei.getDataList(Shop.class);
            //####### 批量插入 ############

            for(int i=0;i<list.size();i++){
                if(list.get(i).getShopCode() == null || "".equals(list.get(i).getShopCode())){
                    failMsg.append("第"+(i+1)+"条门店编码为空、");
                    failureNum ++;
                    continue;
                }
                if(list.get(i).getClientCode() == null || "".equals(list.get(i).getClientCode())){
                    failMsg.append("第"+(i+1)+"条客编为空、");
                    failureNum ++;
                    continue;
                }
                    Long clientId = service.getClientId(list.get(i).getClientCode());
                if(clientId == null){
                    failMsg.append("第"+(i+1)+"条客编无效、");
                    failureNum ++;
                    continue;
                }else{
                    list.get(i).setClientId(clientId);
                }
                try{
                    successNum+=service.saveForExcel(list.get(i));
                }catch (Exception e){
                    logger.error("excel导入出错,第"+(i+1)+"个: ", e.getCause());
                }

            }

            if (failureNum>0){
                failureMsg.insert(0, "，失败 "+failureNum+" 条"+dataTableName+"记录。");
            }
            return ResponseResult.ok("已成功导入 "+successNum+" 条"+dataTableName+"记录"+failureMsg+failMsg);
        } catch (Exception e) {
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息："+e.getMessage());
        }
    }

    /**
     * 导出门店数据
     * @param shop 封装的数据
     * @param request request
     * @param response response
     * @return return
     */
    @Override
    public ResponseResult exportFile(Shop shop, HttpServletRequest request, HttpServletResponse response) {
        try {
            String dataTableName = new Shop().getDataTableName();
            String fileName = dataTableName+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<Shop> list=service.findList(shop);
            new ExportExcelUtils(dataTableName, ReflectionsUtils.getClassGenricType(this.getClass(), 1))
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息："+e.getMessage());
        }
    }
}
