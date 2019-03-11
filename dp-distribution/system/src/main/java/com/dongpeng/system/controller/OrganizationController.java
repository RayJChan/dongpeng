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
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.OrgArea;
import com.dongpeng.entity.system.Organization;
import com.dongpeng.entity.system.User;
import com.dongpeng.entity.system.UserOrg;
import com.dongpeng.system.service.OrgAreaService;
import com.dongpeng.system.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 组织管理接口controller
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseDataController<OrganizationService,Organization> {
    @Autowired
    private OrgAreaService orgAreaService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /*@RequestMapping("/test")
    public ResponseResult test(@RequestParam("parentOrgCode") String parentOrgCode, HttpServletRequest request, Model model) throws OptimisticLockException {
        return ResponseResult.ok(service.generateOrgCode(parentOrgCode));
    }*/

    /**
     * 按地理位置查询数据,并查询出对应优惠券
     * @param organization 至少要包含用户的经纬度
     * @param minRadius 最小查找范围 单位：千米
     * @param maxRadius 最大查找范围 单位：千米
     * @return
     */
    @RequestMapping(value = "/listByGeo/{minRadius}/{maxRadius}", method = RequestMethod.GET)
    public ResponseResult listByGeo(Organization organization, @PathVariable("minRadius")Integer minRadius, @PathVariable("maxRadius")Integer maxRadius){
       return ResponseResult.ok(service.findListByGeoAndCoupons(organization, minRadius,maxRadius));
    }

    /**
     * 获取范围内最近的组织
     * @param organization 至少要包含用户的经纬度,组织id
     * @param radius 查找范围 单位：千米
     * @return
     */
    @RequestMapping(value = "/get/nearestDistance/{radius}", method = RequestMethod.GET)
    public ResponseResult getNearestDistance(Organization organization, @PathVariable("radius")Integer radius){
        return ResponseResult.ok(service.getNearestDistance(organization, radius));
    }

    /**
     * 新增一个组织
     * @param organization 封装organization数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Override
    public ResponseResult add(@RequestBody Organization organization, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, organization)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        Organization orgTemp=service.getByName(organization.getOrgName());
        if(null!=orgTemp){
            return ResponseResult.failByParam("组织名已存在");
        }

        //类型为门店时，经纬度不能为空
        Dictionary type=DictUtils.getByCode(Global.DICT_ORG_TYPE_MD);
        if(type.getId().longValue()==organization.getTypeId().longValue()){
            if(null==organization.getLat() || null==organization.getLng()){
                return ResponseResult.failByParam("经纬度不能为空");
            }
        }

        int rs=service.save(organization);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @EnableDetailLog(serviceclass =OrganizationService.class,handleName = "更新一个组织")
    @Override
    public ResponseResult update(@RequestBody  Organization organization, HttpServletRequest request, Model model) throws Exception {
        if(null==organization.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        Organization temp= (Organization) service.get(organization.getId());

        if(null==temp){
            return ResponseResult.failByParam("id 无效");
        }

        //新name和旧name不相等情况下，要判断新name是否会重复
        if(StringUtils.isNotBlank(organization.getOrgName()) && !organization.getOrgName().equals(temp.getOrgName())){
            Organization orgByName=service.getByName(organization.getOrgName());
            if(null!=orgByName){
                return ResponseResult.failByParam("组织名称已存在");
            }
        }

        //类型为门店时，经纬度不能为空
        Dictionary type=DictUtils.getByCode(Global.DICT_ORG_TYPE_MD);
        if(type.getId().longValue()==organization.getTypeId().longValue()){
            if(null==organization.getLat() || null==organization.getLng()){
                return ResponseResult.failByParam("经纬度不能为空");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(organization, temp);//将非NULL值覆盖temp中的值

        service.save(temp);
        return ResponseResult.ok(null);
    }

    /**
     * 插入组织范围
     * @param orgAreas 组织范围列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertOrgArea", method = RequestMethod.POST)
    public ResponseResult insertOrgArea(@RequestBody List<OrgArea> orgAreas, HttpServletRequest request){
        int rs=orgAreaService.addOrgAreaList(orgAreas);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入组织范围失败");
        }
    }

    /**
     * 根据组织id获取组织范围
     * @param orgId 组织id
     * @return
     */
    @RequestMapping(value = "/getOrgAreaList/{orgId}", method = RequestMethod.GET)
    public ResponseResult getOrgAreaList(@PathVariable(name="orgId") Long orgId){
        return ResponseResult.ok(orgAreaService.findList(new OrgArea(orgId)));
    }

    /**
     * 根据用户id查找组织信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/listByUser/{userId}", method = RequestMethod.GET)
    public ResponseResult findListByUserId(@PathVariable("userId") Long userId){
        return ResponseResult.ok(service.findListByUserId(userId));
    }

    /**
     * 插入用户组织
     * @param userOrgs 用户组织列表
     *                 <p>每个子项的userId必须一样,即只可以为一个用户批量插入组织</p>
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertUserOrg", method = RequestMethod.POST)
    public ResponseResult insertUserOrg(@RequestBody List<UserOrg> userOrgs, HttpServletRequest request){
        int rs=service.insertUserOrg(userOrgs);
        if(1<=rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("插入用户组织失败");
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
            String dataTableName=new Organization().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<Organization> list = ei.getDataList(Organization.class);
            //####### 批量插入 ############
            for(int i=0;i<list.size();i++){
                try{
                    int rs=service.saveForExcel(list.get(i));
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

    /**
     * 根据门店id生成分享token
     * @param orgId 门店id（组织id）
     * @return
     */
    @RequestMapping("/getsharetoken/{orgId}")
    public ResponseResult getShareToken(@PathVariable Long orgId){
        return service.getShareToken(orgId);
    }

    /**
     * 根据token获取门店信息
     * @param shareToken 门店分享token
     * @return
     */
    @GetMapping("/getbysharetoken/{shareToken}")
    public ResponseResult getByShareToken(Organization organization,@PathVariable(name = "shareToken") String shareToken){
        return service.getByShareToken(organization,shareToken);
    }

    /**
     * 不分页查找列表 (供小程序调用)
     * @param organization 封装查询参数
     * @return
     */
    @RequestMapping(value = "/list/xcx", method = RequestMethod.GET)
    public ResponseResult findAllByXcx(Organization organization){
        return ResponseResult.ok(service.findList(organization));
    }
}
