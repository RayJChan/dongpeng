package com.dongpeng.system.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.CrSmsUtils;
import com.dongpeng.common.utils.ImportExcelUtils;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.dongpeng.common.web.BaseController;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
 * 用户管理接口controller
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseDataController<UserService,User> {
    /**
     * 分页查找用户列表
     * @param user 封装查询参数
     * @param pageNo 页码
     * @param pageSize 每页数据条目
     * @return
     */
    @Override
    @RequestMapping(value = "/list/{"+Global.PAGE_SIZE+"}/{"+Global.PAGE_NO+"}", method = RequestMethod.GET)
    public ResponseResult findPage(User user, @PathVariable(Global.PAGE_NO)Integer pageNo, @PathVariable(Global.PAGE_SIZE)Integer pageSize){
        return ResponseResult.ok(service.findPage(new Page<User>(pageNo, pageSize,-1), user));
    }

    /**
     * 获取单个用户，根据id
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.ok(service.get(id));
    }

    /**
     * 获取单个用户，根据工号code
     * @param code 工号code
     * @return
     */
    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.GET)
    public ResponseResult getByCode(@PathVariable("code") String code) {
        return ResponseResult.ok(service.getByUserCode(code));
    }

    /**
     * 用户登录
     * @param user 封装 userCode、password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody User user,HttpServletRequest request) {
        if(StringUtils.isBlank(user.getUserCode()) || StringUtils.isBlank(user.getPassword())){
            return ResponseResult.failByParam("工号或密码为空");
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
            return ResponseResult.failByBusiness("登录失败，工号或密码不正确");
        }
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
     * 新增一个用户
     * @param user 封装用户数据
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody User user,HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, user)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }
        User userTemp=service.getByUserCode(user.getUserCode());
        if(null!=userTemp){
            return ResponseResult.failByParam("用户工号已存在");
        }

        int rs=service.add(user);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存用户失败");
        }
    }

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
    @Override
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

        //新工号和旧工号不相等情况下，要判断新工号是否会重复
        if(StringUtils.isNotBlank(user.getUserCode()) && !user.getUserCode().equals(userTemp.getUserCode())){
            User userByCode=service.getByUserCode(user.getUserCode());
            if(null!=userByCode){
                return ResponseResult.failByParam("用户工号已存在");
            }
        }

        service.update(user,userTemp);
        return ResponseResult.ok(null);
    }

    /**
     * 更新一个用户密码
     * @param user 封装id、新密码和旧密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestBody User user) throws Exception {
        if(null==user.getId() || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getNewPassword())){
            return ResponseResult.failByParam("id、password、newPassword不能为空");
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
   /* @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
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
    @Override
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

}
