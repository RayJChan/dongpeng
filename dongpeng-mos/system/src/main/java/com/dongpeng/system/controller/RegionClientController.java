package com.dongpeng.system.controller;


import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.pojo.ClientRegionPoJo;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.ExportExcelUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.ReflectionsUtils;
import com.dongpeng.entity.system.RegionClient;
import com.dongpeng.system.service.RegionClientService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/regionClient")
public class RegionClientController extends BaseDataController<RegionClientService, RegionClient> {

    /**
     * 批量更新或插入区域与客户关联表信息
     * @param list
     * @return
     */
    @RequestMapping(value = "updateList", method = RequestMethod.POST)
    public ResponseResult updateList(@RequestBody List<RegionClient> list, HttpServletRequest request, Model model) throws Exception {

        if (list == null) {
            return  ResponseResult.failByParam("参数为空");
        }
        return service.updateList(list);
    }
    /**
     * 根据区ID和客户ID查询客户信息
     * @param regionClient 封装传参
     * @return
     */
    @RequestMapping(value = "/findByRegion", method = RequestMethod.GET)
    public ResponseResult findByRegion(RegionClient regionClient) {
        if (regionClient.getDistrictId() == null) {
            ResponseResult.failByParam("districtId 不能为空");
        }
        return ResponseResult.ok(service.findByRegion(regionClient));
    }
    /**
     * 导入Excel数据
     * @param file 客户端上传的Excel文件
     * @return
     */
    @RequestMapping(value = "/import", method=RequestMethod.POST)
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName = new RegionClient().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);
            List<RegionClient> list = ei.getDataList(RegionClient.class);
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
        }catch (Exception e){
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息："+e.getMessage());
        }
    }
    /**
     * 导出客户数据
     * @param regionClient 封装的数据
     * @param request request
     * @param response response
     * @return return
     */
    @Override
    public ResponseResult exportFile(RegionClient regionClient, HttpServletRequest request, HttpServletResponse response) {
        try {
            String dataTableName = new RegionClient().getDataTableName();
            String fileName = dataTableName+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<RegionClient> list=service.findExportFile(regionClient);
            new ExportExcelUtils(dataTableName, ReflectionsUtils.getClassGenricType(this.getClass(), 1))
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息："+e.getMessage());
        }
    }

}
