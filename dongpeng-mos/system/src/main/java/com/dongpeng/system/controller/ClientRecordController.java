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
import com.dongpeng.entity.system.*;
import com.dongpeng.system.dao.ClientRecordDao;
import com.dongpeng.system.service.ClientRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/clientRecord")
public class ClientRecordController extends BaseDataController<ClientRecordService, ClientRecord> {

    @Autowired
    ClientRecordDao dao;

    /**
     * 根据区ID和客户ID查询客户信息
     *
     * @param regionClient 封装传参
     * @return
     */
    @RequestMapping(value = "/findByRegion", method = RequestMethod.GET)
    public ResponseResult findByRegion(RegionClient regionClient) {
        if (regionClient.getClientId() == null) {
            ResponseResult.failByParam("clientId 不能为空");
        }
        if (regionClient.getDistrictId() == null) {
            ResponseResult.failByParam("districtId 不能为空");
        }
        return ResponseResult.ok(service.findByRegion(regionClient));
    }

    /**
     * 分页查找客户列表
     * @param clientRecord 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return ResponseResult
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(ClientRecord clientRecord, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<ClientRecord>(pageNo, pageSize,-1), clientRecord));
    }

    /**
     * 新增一个客户
     * @param clientRecord 封装客户数据
     * @param model model
     * @return ResponseResult
     */
    @Override
    public ResponseResult add(@RequestBody ClientRecord clientRecord, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, clientRecord)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        return service.insert(clientRecord);
    }

    /**
     * 修改客户信息
     * @param clientRecord 封装客户数据
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult update(@RequestBody ClientRecord clientRecord, HttpServletRequest request, Model model) throws Exception {
        if(!beanValidator(model, clientRecord)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(clientRecord.getId() == null){
            return ResponseResult.failByParam("客户id不能为空");
        }
        if(clientRecord.getClientCode() == null){
            return ResponseResult.failByParam("客户编码不能为空");
        }
        return service.update(clientRecord);
    }

    /**
     * 财务数据维护
     * @return
     */
    @RequestMapping(value = "/maintain", method = RequestMethod.POST)
    public ResponseResult maintain(@RequestBody ClientRecord clientRecord, HttpServletRequest request, Model model)throws Exception {
        if(!beanValidator(model, clientRecord)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if(clientRecord.getId() == null){
            return ResponseResult.failByParam("客户id不能为空");
        }
        if(clientRecord.getClientCode() == null){
            return ResponseResult.failByParam("客户编码不能为空");
        }
    return service.maintain(clientRecord);
    }

    /**
     * 导出客户数据
     * @param clientRecord 封装的数据
     * @param request request
     * @param response response
     * @return return
     */
    @Override
    public ResponseResult exportFile(ClientRecord clientRecord, HttpServletRequest request, HttpServletResponse response) {
        try {
            String dataTableName = new ClientRecord().getDataTableName();
            String fileName = dataTableName+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<ClientRecord> list=service.findList(clientRecord);
            new ExportExcelUtils(dataTableName, ReflectionsUtils.getClassGenricType(this.getClass(), 1))
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息："+e.getMessage());
        }
    }

    /**
     * 导入数据
     * @param file 客户端上传的Excel文件
     * @return
     */
    @Override
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName = new ClientRecord().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failMsg = new StringBuilder("导入失败信息:");
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<ClientRecord> list = ei.getDataList(ClientRecord.class);
            //####### 批量插入 ############

            for(int i=0;i<list.size();i++){
                if(list.get(i).getClientCode() == null || "".equals(list.get(i).getClientCode())){
                    failMsg.append("第"+(i+1)+"条客编为空、");
                    failureNum ++;
                    continue;
                }
                Department Department = service.getByDepartmentName(list.get(i).getDepartmentName());
                if(Department == null){
                    failMsg.append("第"+(i+1)+"条部门["+list.get(i).getDepartmentName()+"]无效");
                    failureNum ++;
                    continue;
                }
                Company Company = service.getCompanyName(list.get(i).getCompanyName());
                if(Company == null){
                    failMsg.append("第"+(i+1)+"条公司["+list.get(i).getCompanyName()+"]无效");
                    failureNum ++;
                    continue;
                }
                Dictionary clientType = service.getByDictionaryName(list.get(i).getClientType());
                if(clientType == null){
                    failMsg.append("第"+(i+1)+"条客户类型["+list.get(i).getClientType()+"]无效");
                    failureNum ++;
                    continue;
                }
                Dictionary ClientChannel = service.getByDictionaryName(list.get(i).getChannelName());
                if(ClientChannel == null){
                    failMsg.append("第"+(i+1)+"条渠道["+list.get(i).getChannelName()+"]无效");
                    failureNum ++;
                    continue;
                }
                if(list.get(i).getProvince() != null && !"".equals(list.get(i).getProvince())){
                    Region province = service.getByProvinceName(list.get(i).getProvince());
                    if(province == null){
                        failMsg.append("第"+(i+1)+"条省["+list.get(i).getProvince()+"]无效");
                        failureNum ++;
                        continue;
                    }
                    list.get(i).setProvinceId(province.getId());
                }
                if(list.get(i).getCity() != null && !"".equals(list.get(i).getCity())){
                    Region city = service.getByProvinceName(list.get(i).getCity());
                    if(city == null){
                        failMsg.append("第"+(i+1)+"条市["+list.get(i).getCity()+"]无效");
                        failureNum ++;
                        continue;
                    }
                    list.get(i).setCityId(city.getId());
                }
                if(list.get(i).getDistrict() != null && !"".equals(list.get(i).getDistrict())){
                    Region district = service.getByProvinceName(list.get(i).getDistrict());
                    if(district == null){
                        failMsg.append("第"+(i+1)+"条区["+list.get(i).getDistrict()+"]无效");
                        failureNum ++;
                        continue;
                    }
                    list.get(i).setDistrictId(district.getId());
                }

                list.get(i).setCompanyId(Company.getId());
                list.get(i).setDepartmentId(Department.getId());
                list.get(i).setClientTypeId(clientType.getId());
                list.get(i).setChannelId(ClientChannel.getId());

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
