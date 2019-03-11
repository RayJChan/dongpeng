package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.*;
import com.dongpeng.common.vo.AddressVo;
import com.dongpeng.entity.system.Region;
import com.dongpeng.entity.system.RegionClient;
import com.dongpeng.system.service.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 省市区 管理接口 controller
 * @author wuhongda
 */
@RestController
@RequestMapping("/region")
public class RegionController extends BaseDataController<RegionService, Region> {

    @Override
    public ResponseResult add(@RequestBody Region region, HttpServletRequest request, Model model) throws OptimisticLockException {
        if (!beanValidator(model, region)) {
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Region regionTemp = service.getByName(region);
        if (null != regionTemp) {
            return ResponseResult.failByParam("该区域已存在");
        }

        int rs = service.save(region);
        if (1 == rs) {
            return ResponseResult.ok(null);
        } else {
            return ResponseResult.failByBusiness("保存区域失败");
        }
    }

    @Override
    @EnableDetailLog(serviceclass = RegionService.class, handleName = "更新省市区")
    public ResponseResult update(@RequestBody Region region, HttpServletRequest request, Model model) throws Exception {
        if (!beanValidator(model, region)) {
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        if (null == region.getId()) {
            return ResponseResult.failByParam("id不能为空");
        }
        Region regionTemp = service.get(region.getId());

        if (null == regionTemp) {
            return ResponseResult.failByParam("id 无效");
        }

        //新省市区和旧省市区不相等情况下，要判断新省市区是否会重复
        if (StringPlusUtils.isNotBlank(region.getName()) && !region.getName().equals(regionTemp.getName())) {
            Region regionByName = service.getByName(region);
            if (null != regionByName) {
                return ResponseResult.failByParam("区域已存在");
            }
        }
        BeanUtils.copyBeanNotNull2Bean(region, regionTemp);//将非NULL值覆盖Temp中的值

        int rs = service.save(regionTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return checkVersion(regionTemp);
        }*/
    }

    /**
     * 查找城市列表
     *
     * @param region 封装查询参数
     * @return
     */
    @RequestMapping(value = "/cityList", method = RequestMethod.GET)
    public ResponseResult findCityList(Region region) {
        return ResponseResult.ok(service.findCityList(region));
    }

    /**
     * 模糊查询所有省市区
     *
     * @param addressVo 封装查询参数
     * @return
     */
    @RequestMapping(value = "/districtLst/{" + Global.PAGE_SIZE + "}/{" + Global.PAGE_NO + "}", method = RequestMethod.GET)
    public ResponseResult findDistrictList(AddressVo addressVo, @PathVariable(Global.PAGE_NO) Integer pageNo, @PathVariable(Global.PAGE_SIZE) Integer pageSize) {
        return ResponseResult.ok(service.findDistrictList(new Page<AddressVo>(pageNo, pageSize - 1), addressVo));
    }

    /**
     * 查找服务范围
     *
     * @return
     */
    @RequestMapping(value = "/findServiceArea", method = RequestMethod.GET)
    public ResponseResult findServiceArea() {
        return ResponseResult.ok(service.findServiceArea());
    }

    /**
     * 不分页查找列表(供小程序调用)
     *
     * @param region 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list/xcx", method = RequestMethod.GET)
    public ResponseResult findAllByXcx(Region region) {
        return ResponseResult.ok(service.findList(region));
    }


    /**
     * 导入Excel数据
     *
     * @param file 客户端上传的Excel文件
     * @return
     */
    @Override
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseResult importFile(MultipartFile file) {
        try {
            String dataTableName = new Region().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<Region> list = ei.getDataList(Region.class);
            //####### 批量插入 ############
            for (int i = 0; i < list.size(); i++) {
                try {
                    int rs = service.saveForExcel(list.get(i));
                    if (1 <= rs) {
                        successNum += 1;
                    } else {
                        failureMsg.append(" 第" + (i + 1) + "个导入出错 ");
                    }
                } catch (Exception e) {
                    logger.error("excel导入出错,第" + (i + 1) + "个: ", e.getCause());
                    failureMsg.append(" 第" + (i + 1) + "个导入出错 ");
                }
            }
            failureNum = list.size() - successNum;

            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条" + dataTableName + "记录。");
            }
            return ResponseResult.ok("已成功导入 " + successNum + " 条" + dataTableName + "记录" + failureMsg);
        } catch (Exception e) {
            logger.error("导入失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导入失败！失败信息：" + e.getMessage());
        }
    }

    /**
     * 更新省市区
     *
     * @param addressVo 省市区列
     * @return
     */
    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    public ResponseResult updateList(@RequestBody AddressVo addressVo, HttpServletRequest request, Model model) throws Exception {
        return service.updateList(addressVo);
    }

    /**
     * 导出客户数据
     *
     * @param addressVo 封装的数据
     * @param request   request
     * @param response  response
     * @return return
     */
    @RequestMapping(value = "/exportClient", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult exportFile(AddressVo addressVo, HttpServletRequest request, HttpServletResponse response) {
        try {
            String dataTableName = new AddressVo().getDataTableName();
            String fileName = dataTableName + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<AddressVo> list = service.findExportFile(addressVo);
            new ExportExcelUtils(dataTableName, ReflectionsUtils.getClassGenricType(this.getClass(), 1))
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.error("导出记录失败！失败信息：", e);
            return ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "导出记录失败！失败信息：" + e.getMessage());
        }
    }
}
