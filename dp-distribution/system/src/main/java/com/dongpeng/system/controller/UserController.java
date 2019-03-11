package com.dongpeng.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.CrSmsUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 用户管理接口controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseDataController<UserService,User> {
   /* @Autowired
    private UserService service;*/

    /**
     * 分页查找用户列表
     * @param user 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(User user, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        if(pageNo==1){
            return ResponseResult.ok(service.findPage(new Page<User>(pageNo, pageSize), user));
        }else{
            return ResponseResult.ok(service.findPage(new Page<User>(pageNo, pageSize,-1), user));
        }
    }

    /**
     * 获取单个用户，根据id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 获取单个用户，根据用户名称
     * @param userName 用户名称
     * @return
     */
    @RequestMapping(value = "/getByName/{userName}", method = RequestMethod.GET)
    public ResponseResult getByUserName(@PathVariable("userName") String userName) {
        return ResponseResult.ok(service.getByUserName(userName));
    }

    /**
     * 用户登录
     * @param user 封装 userCode、password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody User user,HttpServletRequest request) {
        if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())){
            return ResponseResult.failByParam("用户名或密码为空");
        }
        user=service.login(user);
        if(null!=user){
            List<Menu> menus=service.getUserMenus(user.getId());

            ResponseResult responseResult=ResponseResult.ok();
            responseResult.put("user", user);
            responseResult.put("menus", menus);
            responseResult.put("token", J2CacheUtils.get(Global.TOKEN_REGION, UserAgentUtils.getIpAddr(request)));
            return responseResult;
        }else{
            return ResponseResult.failByBusiness("登录失败，用户名或密码不正确");
        }
    }

    /**
     * 用户微信登录
     * @param user 封装 wxId
     * @return
     */
    @RequestMapping(value = "/loginForWx", method = RequestMethod.POST)
    public ResponseResult loginForWx(@RequestBody User user,HttpServletRequest request) throws Exception {
        if(StringUtils.isBlank(user.getWxCode())){
            return ResponseResult.failByParam("微信code为空");
        }
        return service.loginForWx(user);
    }

    /**
     * 用户手机登录/注册
     * @param user 封装 phone
     * @param verifyCode 验证码
     * @return
     */
    @RequestMapping(value = "/loginForPhone/{verifyCode}", method = RequestMethod.POST)
    public ResponseResult loginForPhone(@RequestBody User user
            ,@PathVariable(name = "verifyCode") String verifyCode
            ,HttpServletRequest request) throws Exception {
        if(StringUtils.isBlank(user.getPhone()) && StringPlusUtils.isPhone(user.getPhone())){
            return ResponseResult.failByParam("手机号码不合法");
        }
        return service.loginAndRegisterForPhone(user,verifyCode);
    }

    /**
     * 用户登出
     * @return
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public ResponseResult loginOut(HttpServletRequest request) {
        service.loginOut(request);
        return ResponseResult.ok();
    }

    /**
     * 新增一个用户（后台增加）
     * @param user 封装用户数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody User user,HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, user)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        User userTemp=service.getByUserName(user.getUserName());
        if(null!=userTemp){
            return ResponseResult.failByParam("用户名称已存在");
        }

        if(StringUtils.isNotBlank(user.getPhone())){
            userTemp=service.getByPhone(user);
            if(null!=userTemp){
                return ResponseResult.failByParam("电话号码已存在");
            }
        }

        Dictionary userTypeBackstage=DictUtils.getByCode(Global.DICT_USER_TYPE_BACKSTAGE);
        Dictionary userTypeEmployee=DictUtils.getByCode(Global.DICT_USER_TYPE_EMPLOYEE);
        Dictionary userTypeFriend=DictUtils.getByCode(Global.DICT_USER_TYPE_FRIEND);
        if(user.getTypeId().intValue()==userTypeBackstage.getId().intValue()){
            //后台用户，密码不能为空
            /*if(StringUtils.isBlank(user.getPassword())){
                return ResponseResult.failByParam("密码不能为空");
            }*/

        }else if(user.getTypeId().intValue()==userTypeEmployee.getId().intValue()){
            //员工用户，姓名、组织、职级不能为空
            if(StringUtils.isBlank(user.getPersonName())){
                return ResponseResult.failByParam("姓名不能为空");
            }
            if(null==user.getOrgId()){
                return ResponseResult.failByParam("组织不能为空");
            }
            if(null==user.getRankId()){
                return ResponseResult.failByParam("职级不能为空");
            }

        }else if(user.getTypeId().intValue()==userTypeFriend.getId().intValue()){
            //朋友汇用户
            return ResponseResult.failByParam("后台不能添加鹏友汇用户");
        }


        int rs=service.addFromBackstage(user);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存用户失败");
        }
    }

    /**
     * 注册一个用户（从小程序增加）
     * @param user 封装用户数据
     * @param type 用户类型 1=员工 2=朋友汇
     * @param verifyCode 验证码
     * @param request
     * @param model
     * @return
     * @throws OptimisticLockException
     */
    /*@RequestMapping(value = "/addFromMiniprogram/{type}", method = RequestMethod.POST)
    public ResponseResult addFromMiniprogram(@RequestBody User user
            ,@PathVariable("type") Integer type
            ,@RequestParam(name = "verifyCode") String verifyCode
            , HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, user)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        User userTemp;
        if(StringUtils.isNotBlank(user.getUserName())){
            userTemp=service.getByUserName(user.getUserName());
            if(null!=userTemp){
                return ResponseResult.failByParam("用户名称已存在");
            }
        }

        if(StringUtils.isNotBlank(user.getPhone())){
            userTemp=service.getByPhone(user);
            if(null!=userTemp){
                return ResponseResult.failByParam("电话号码已存在");
            }
        }

        int rs=0;
        switch (type){
            case 1:
                rs=service.addEmployeeFromMiniprogram(user,verifyCode);
                break;
            case 2:
                rs=service.addFriendsFromMiniprogram(user,verifyCode);
                break;
        }

        if(1==rs){
            return ResponseResult.ok(null);
        }else if(-1==rs){
            return ResponseResult.failByBusiness("验证码无效");
        }else{
            return ResponseResult.failByBusiness("保存用户失败");
        }
    }*/

    /**
     * 发送 登录/注册 手机验证码
     * @param phone 手机号码
     * @return
     */
    @RequestMapping(value = "/sendVerifyCode/loginOrRegister",method = RequestMethod.POST)
    public ResponseResult sendVerifyCodeForLoginOrRegister(@RequestParam(name = "phone") String phone){
        return ResponseResult.ok(CrSmsUtils.sendVerificationCodeForLoginOrRegister(phone));
    }

    /**
     * 更新一个用户信息
     * @param user 封装需要更新的信息
     * @return
     * @throws Exception
     */
    @EnableDetailLog(serviceclass = UserService.class,handleName = "更新一个用户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody User user,HttpServletRequest request, Model model) throws Exception {
        if(null==user.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        //从数据库获取该用户旧信息
        User userTemp=service.get(user.getId());

        if(null==userTemp){
            return ResponseResult.failByParam("id 无效");
        }

        //新用户名称和旧用户名称不相等情况下，要判断新用户名称是否会重复
        if(StringUtils.isNotBlank(user.getUserName()) && !user.getUserName().equals(userTemp.getUserName())){
            User userByName=service.getByUserName(user.getUserName());
            if(null!=userByName){
                return ResponseResult.failByParam("用户名称已存在");
            }

        }

        //新电话号码和旧电话号码不相等情况下，要判断新电话号码是否会重复
        if(StringUtils.isNotBlank(user.getPhone()) && !user.getPhone().equals(userTemp.getPhone())){
            User userByName=service.getByPhone(user);
            if(null!=userByName){
                return ResponseResult.failByParam("电话号码已存在");
            }
        }

        int rs=service.update(user,userTemp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            //return ResponseResult.failByBusiness("更新用户失败");
            return checkVersion(userTemp);
        }*/
    }

    /**
     * 更新一个用户密码
     * @param user 封装id、新密码和旧密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestBody User user,HttpServletRequest request) throws Exception {
        if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getNewPassword())){
            return ResponseResult.failByParam("password、newPassword不能为空");
        }
       String result=service.updatePassword(user);
       if(ResponseCode.SUCCESS.getMsg().equals(result)){
          return ResponseResult.ok();
       }else{
           return ResponseResult.failByBusiness(result);
       }
    }

    /**
     * 重置一个用户密码
     * @param id 用户id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetPassword/{id}", method = RequestMethod.POST)
    public ResponseResult resetPassword(@PathVariable("id") Long id,HttpServletRequest request) throws Exception {

        String result=service.resetPassword(new User(id));
        if(ResponseCode.SUCCESS.getMsg().equals(result)){
            return ResponseResult.ok();
        }else{
            return ResponseResult.failByBusiness(result);
        }
    }

    /**
     * 停用/启用 一个用户
     * <p>实际为逻辑删除</p>
     * @param id 用户id
     * @param deleteFlag true：停用；false：启用
     * @return
     */
    @Override
    @RequestMapping(value = "/deleteToggle/{id}/{deleteFlag}", method = RequestMethod.POST)
    public ResponseResult deleteToggle(@PathVariable("id") Long id,@PathVariable("deleteFlag")Boolean deleteFlag){
        int rs=service.deleteToggle(new User(id,deleteFlag));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness(deleteFlag?"启用用户失败":"停用用户失败");
        }
    }

    /**
     * 物理删除一个用户
     * @param id 用户id
     * @return
     */
    /*@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id){
        int rs=service.delete(new User(id));
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("删除用户失败");
        }
    }*/

    /**
     * 批量物理删除用户
     * @param ids 用户id集合，多个用逗号分隔
     * @return
     */
    /*@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResponseResult delete(@RequestParam(required = true) String ids){
        String idArray[] =ids.split(",");
        for(String id : idArray){
            service.delete(new User(Long.valueOf(id)));
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
            String dataTableName=new User().getDataTableName();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcelUtils ei = new ImportExcelUtils(file, 1, 0);

            List<User> list = ei.getDataList(User.class);
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
     * 获取小程序全局唯一后台接口调用凭据
     * @param type 1=员工 2=朋友汇
     * @return
     */
    @RequestMapping(value = "/getAccessToken/{type}", method = RequestMethod.POST)
    public ResponseResult getAccessToken(@PathVariable("type") Integer type){
       return ResponseResult.ok(service.getAccessToken(type));
    }

    /**
     * 不分页查找拥有审批权限的用户列表
     * @param type 类型<ul><li>1=优惠券审批权限</li><li>2=员工申请优惠券审批权限</li></ul>
     * @return
     */
    @RequestMapping(value = "/listByExamine/{type}", method = RequestMethod.GET)
    public ResponseResult findAllByExamine(@PathVariable(name = "type") Integer type){
        return ResponseResult.ok(service.findListByMenu(type));
    }

    /**
     * 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制
     * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字
     * @param page 必须是已经发布的小程序存在的页面（否则报错）,默认主页
     * @param width 二维码的宽度，单位 px，最小 280px，最大 1280px。默认430
     * @param autoColor 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     * @param lineColor auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     * @param hyaline 是否需要透明底色，为 true 时，生成透明底色的小程序
     * @return
     */
    @RequestMapping(value = "/getWxAcodeUnlimit", method = RequestMethod.POST)
    public ResponseResult getWxAcodeUnlimit(@RequestParam(name = "scene") String scene,
                                            @RequestParam(required = false,name = "page")String page,
                                            @RequestParam(required = false,name = "width")Integer width,
                                            @RequestParam(required = false,name = "autoColor")Boolean autoColor,
                                            @RequestParam(required = false,name = "lineColor")String lineColor,
                                            @RequestParam(required = false,name = "hyaline")Boolean hyaline) {
        return  service.getWxacodeUnlimit(scene, page, width, autoColor, lineColor, hyaline);
    }

}
