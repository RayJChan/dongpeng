package com.dongpeng.system.service;

import com.alibaba.fastjson.JSONObject;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.CrSmsUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.JWTUtils;
import com.dongpeng.common.utils.PasswordUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.dongpeng.common.utils.WxMiniProgramUtils;
import com.dongpeng.entity.system.BlPersonExamine;
import com.dongpeng.entity.system.BlRank;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.Organization;
import com.dongpeng.entity.system.User;
import com.dongpeng.entity.system.UserMenu;
import com.dongpeng.system.dao.UserDao;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService extends BaseCrudService<UserDao,User> {
    @Autowired
    private MenuService menuService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private BlRankService blRankService;


    @Override
    public String createDataScopeSql(User user) {
        return null;
    }

    /**
     * 新增一个用户
     * @param user 封装用户数据
     * @return
     */
    @Transactional(readOnly = false)
    public int add(User user) throws OptimisticLockException {

        //如果角色id不为空，角色名称为空，自动查找补全角色名称
        if(null!=user.getRoleId() && StringUtils.isBlank(user.getRoleName())){
            Dictionary role=DictUtils.getById(user.getRoleId());
            user.setRoleName(null==role?"":role.getDetailName());
        }

        //如果组织id不为空，组织名称为空，自动查找补全组织名称
        if(null!=user.getOrgId() && StringUtils.isBlank(user.getOrgName())){
            Organization organization=organizationService.get(user.getOrgId());
            user.setOrgName(null==organization?"":organization.getOrgName());
        }

        //如果职级id不为空，职级名称为空，自动查找补全职级名称
        if(null!=user.getRankId() && StringUtils.isBlank(user.getRankName())){
            BlRank rank=blRankService.get(user.getRankId());
            user.setRankName(null==rank?"":rank.getRankName());
        }

        //如果用户类型id不为空，用户类型为空，自动查找补全用户类型名称
        if(null!=user.getTypeId() && StringUtils.isBlank(user.getTypeName())){
            Dictionary userType=DictUtils.getById(user.getTypeId());
            user.setTypeName(null==userType?"":userType.getDetailName());
        }

        int rs=super.save(user);

        //############# 权限相关操作 ##############

        //为用户插入对应角色的 用户菜单
        if(null!=user.getRoleId()){
            List<Menu> menus=menuService.findListByRoleId(user.getRoleId());
            List<UserMenu> userMenus= Lists.newArrayList();
            for (Menu menu: menus) {
                userMenus.add(new UserMenu(user.getId(),user.getUserName(),menu.getId(),user.getRoleId()));
            }
            menuService.insertUserMenu(userMenus);
        }
        return rs;
    }

    /**
     * 从后台添加用户
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public int addFromBackstage(User user) throws OptimisticLockException {
        Dictionary examineReviewed=DictUtils.getByCode(Global.REVIEWED);
        user.setExamineStatusId(examineReviewed.getId());
        user.setExamineStatusName(examineReviewed.getDetailName());
        user.setPassword(Global.getConfig(Global.DEFAULT_PASSWORD));//默认密码
        return add(user);
    }

    /**
     * 从小程序添加朋友汇用户
     * @param user
     */
    @Transactional(readOnly = false)
    public int addFriendsFromMiniprogram(User user) throws OptimisticLockException {
        Dictionary friend=DictUtils.getByCode(Global.DICT_USER_TYPE_FRIEND);
        user.setTypeId(friend.getId());
        user.setTypeName(friend.getDetailName());

        //设置为未审核
        Dictionary unreviewStatus=DictUtils.getByCode(Global.UNREVIEW);
        user.setExamineStatusId(unreviewStatus.getId());
        user.setExamineStatusName(unreviewStatus.getDetailName());

        //默认职级
        BlRank rank=blRankService.get(58417025914912768l);
        if(null!=rank){
            user.setRankId(rank.getId());
            user.setRankName(rank.getRankName());
        }

        //朋友汇用户，默认赋予朋友汇角色
        Dictionary userType=DictUtils.getByCode(Global.DICT_USER_TYPE_FRIEND);
        if(null!=userType && user.getTypeId().longValue()==userType.getId().longValue()){
            Dictionary role=DictUtils.getByCode(Global.DICT_ROLE_FRIEND);
            if(null!=role){
                user.setRoleId(role.getId());
                user.setRoleName(role.getDetailName());
            }
        }

        //朋友汇用户姓名置空
        user.setPersonName(null);

        if(StringPlusUtils.isNotBlank(user.getWxCode())){
            //从根据code从微信服务器获取openId
            boolean wxResult=WxMiniProgramUtils.code2Session(2,user.getWxCode(), new WxMiniProgramUtils.WxCallback() {
                @Override
                public void success(JSONObject result) {
                    user.setWxId(result.get("openid").toString());
                    //session_key
                    //unionid
                }

                @Override
                public void error(JSONObject result) {

                }
            });
        }
        return add(user);
    }

    /**
     * 从小程序添加员工用户
     * @param user
     */
    @Transactional(readOnly = false)
    public int addEmployeeFromMiniprogram(User user) throws OptimisticLockException {
        Dictionary employee=DictUtils.getByCode(Global.DICT_USER_TYPE_EMPLOYEE);
        user.setTypeId(employee.getId());
        user.setTypeName(employee.getDetailName());

        //设置为未审核
        Dictionary unreviewStatus=DictUtils.getByCode(Global.UNREVIEW);
        user.setExamineStatusId(unreviewStatus.getId());
        user.setExamineStatusName(unreviewStatus.getDetailName());

        //默认职级
        BlRank rank=blRankService.get(58417025914912768l);
        if(null!=rank){
            user.setRankId(rank.getId());
            user.setRankName(rank.getRankName());
        }

        if(StringPlusUtils.isNotBlank(user.getWxCode())){
            //从根据code从微信服务器获取openId
            boolean wxResult=WxMiniProgramUtils.code2Session(1,user.getWxCode(), new WxMiniProgramUtils.WxCallback() {
                @Override
                public void success(JSONObject result) {
                    user.setWxId(result.get("openid").toString());
                    //session_key
                    //unionid
                }

                @Override
                public void error(JSONObject result) {

                }
            });
        }
        return add(user);
    }

    /**
     * 更新一个用户信息
     * @param formUser 封装 接收到的 用户信息
     * @param dataUser 封装 数据库中 用户的信息
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public int update(User formUser,User dataUser) throws Exception {

        //############# 权限相关操作 ##############
        if(null!=formUser.getRoleId()){
            //如果角色id不相等
            if(formUser.getRoleId()!=dataUser.getRoleId()){
                //如果角色名称为空，自动查找补全角色名称
                if(StringUtils.isBlank(formUser.getRoleName())){
                    Dictionary role=DictUtils.getById(formUser.getRoleId());
                    formUser.setRoleName(null==role?"":role.getDetailName());
                }

                //删除该用户-角色 对应所有用户菜单(即系旧菜单)
                UserMenu userMenu=new UserMenu(dataUser.getId());
                userMenu.setDictionaryId(dataUser.getRoleId());
                menuService.deleteUserMenu(userMenu);
            }

            //为用户插入对应角色的 用户菜单
            List<Menu> menus=menuService.findListByRoleId(formUser.getRoleId());
            List<UserMenu> userMenus= Lists.newArrayList();
            for (Menu menu: menus) {
                userMenus.add(new UserMenu(formUser.getId(),formUser.getUserName(),menu.getId(),formUser.getRoleId()));
            }
            menuService.insertUserMenu(userMenus);
        }

        //如果组织id不为空，组织名称为空，自动查找补全组织名称
        if(null!=formUser.getOrgId()){
            Organization organization=organizationService.get(formUser.getOrgId());
            formUser.setOrgName(null==organization?"":organization.getOrgName());
        }

        //如果职级id不为空，职级名称为空，自动查找补全职级名称
        if(null!=formUser.getRankId()){
            BlRank rank=blRankService.get(formUser.getRankId());
            formUser.setRankName(null==rank?"":rank.getRankName());
        }

        //如果用户类型id不为空，用户类型为空，自动查找补全用户类型名称
        if(null!=formUser.getTypeId()){
            Dictionary userType=DictUtils.getById(formUser.getTypeId());
            formUser.setTypeName(null==userType?"":userType.getDetailName());
        }

        BeanUtils.copyBeanNotNull2Bean(formUser, dataUser);//将formUser非NULL值覆盖dataUser中的值
        dataUser.setIsNewRecord(false);
        return super.save(dataUser);
    }

    /**
     * 更新用户积分
     * @param userId 用户id
     * @param score 积分
     * @return
     * @throws OptimisticLockException
     */
    @Transactional
    public int updateUserScore(Long userId,int score) throws OptimisticLockException {
        User user=get(userId);
        user.setScore(score);
        return save(user);
    }

    /**
     * 更新用户密码
     * @param user 封装id、新密码和旧密码
     * @return ResponseCode.SUCCESS.getMsg() 表示成功，其他表示失败原因
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public String updatePassword(User user) throws Exception {
        User userTemp=null==user.getId()?getCurrentUser():get(user.getId());//如果id为空就是代表修改当前用户密码
        String result=null;
        if(PasswordUtils.validatePasswordBySha1(user.getPassword(), userTemp.getPassword())){
            user.setPassword(PasswordUtils.entryptPasswordBySha1(user.getNewPassword()));//新密码加密
            BeanUtils.copyBeanNotNull2Bean(user, userTemp);//将user非NULL值覆盖userTemp中的值
            userTemp.setIsNewRecord(false);
            int rs=save(userTemp);
            if(1==rs){
                result= ResponseCode.SUCCESS.getMsg();
            }else {
                if(compareVersion(userTemp)){
                    result="更新密码失败";
                }else{
                    result=userTemp.getModifierName()+" 用户正在操作该数据，请稍后再试";
                }
            }
        }else{
            result= "旧密码不正确";
        }
        return result;
    }

    /**
     * 重置密码
     * @param user 封装id
     * @return ResponseCode.SUCCESS.getMsg() 表示成功，其他表示失败原因
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public String resetPassword(User user) throws Exception {
        if(1==user.getId()){
            return "该用户不允许重置密码";
        }
        User userTemp=get(user.getId());
        String result=null;
        user.setPassword(PasswordUtils.entryptPasswordBySha1(Global.getConfig(Global.DEFAULT_PASSWORD)));//默认重置密码加密
        BeanUtils.copyBeanNotNull2Bean(user, userTemp);//将user非NULL值覆盖userTemp中的值

        int rs=save(userTemp);
        if(1==rs){
            result= ResponseCode.SUCCESS.getMsg();
        }else {
            if(compareVersion(userTemp)){
                result="重置密码失败";
            }else{
                result=userTemp.getModifierName()+" 用户正在操作该数据，请稍后再试";
            }
        }
        return result;
    }

    /**
     * 更新用户审核状态
     * @param userId 用户id
     * @param examineStatus 审核状态字典
     * @return
     * @throws Exception
     */
    @Transactional
    public int updateExamineStatus(Long userId,Dictionary examineStatus,BlPersonExamine blPersonExamine) throws Exception {
        User userTemp=get(userId);

        User user=new User(userId);
        user.setExamineStatusName(examineStatus.getDetailName());
        user.setExamineStatusId(examineStatus.getId());

        //如果为审核通过
        if(Global.REVIEWED.equals(examineStatus.getDetailCode())){
            //用户类型修改为员工用户
            Dictionary employee=DictUtils.getByCode(Global.DICT_USER_TYPE_EMPLOYEE);
            user.setTypeId(employee.getId());
            user.setTypeName(employee.getDetailName());

            //赋予员工角色
            Dictionary role=DictUtils.getByCode(Global.DICT_ROLE_EMPLOYEE);
            if(null!=role){
                user.setRoleId(role.getId());
                user.setRoleName(role.getDetailName());
            }

            //设置组织
            user.setOrgId(blPersonExamine.getOrgId());
            user.setOrgName(blPersonExamine.getOrgName());

            //设置姓名
            user.setPersonName(blPersonExamine.getPersonName());
        }

        //BeanUtils.copyBeanNotNull2Bean(user, userTemp);//将user非NULL值覆盖userTemp中的值
        //userTemp.setIsNewRecord(false);
        int rs=update(user, userTemp);
        return rs;
    }

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return
     */
    public User getByUserName(String userName){
        return dao.getByUserName(new User(userName));
    }

    /**
     * 用户登录
     * @param user 封装 userCode、password
     * @return 登录成功返回user信息 <br/>登录失败，返回null
     */
    public User login(User user){
        User userFromDataBase=getByUserName(user.getUserName());
        if(null!=userFromDataBase && StringPlusUtils.isNotBlank(userFromDataBase.getPassword())){
            if(PasswordUtils.validatePasswordBySha1( user.getPassword(),userFromDataBase.getPassword())){
                //生成token,并写入缓存,以ip地址为key,供网关层调用验证
                JWTUtils jwt=JWTUtils.getInstance();
                String token=jwt.getToken(userFromDataBase.getId().toString()+","+userFromDataBase.getUserName());
                J2CacheUtils.put(Global.TOKEN_REGION, UserAgentUtils.getIpAddr(), token);
                logger.info("login ip is "+UserAgentUtils.getIpAddr());

                return userFromDataBase;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 用户微信登录
     * @param user 封装 微信code
     * @param
     * @return ResponseResult
     */
    @Transactional
    public ResponseResult loginForWx(User user) throws Exception {
        //从根据code从微信服务器获取openId
        boolean wxResult=WxMiniProgramUtils.code2Session(0,user.getWxCode(), new WxMiniProgramUtils.WxCallback() {
            @Override
            public void success(JSONObject result) {
                user.setWxId(result.get("openid").toString());
                //session_key
                //unionid
            }

            @Override
            public void error(JSONObject result) {

            }
        });
        if(wxResult){
            User userFromDataBase=dao.getByWxId(user);
            if(null!=userFromDataBase){
                //姓名置空,不更新
                user.setPersonName(null);
                BeanUtils.copyBeanNotNull2Bean(user, userFromDataBase);//将user非NULL值覆盖userFromDataBase中的值
                userFromDataBase.setIsNewRecord(false);
                save(userFromDataBase);
                return generateMiniprogramLoginInfo(userFromDataBase);
            }else{
                return ResponseResult.failByBusiness("登录失败");
            }
        }else{
            return ResponseResult.failByBusiness("获取微信openId失败");
        }

    }

    /**
     * 用户手机号码登录/注册
     * <p>自动注册或登录</p>
     * @param user 封装 电话号码
     * @return ResponseResult
     */
    @Transactional(readOnly = false)
    public ResponseResult loginAndRegisterForPhone(User user,String verifyCode) throws Exception {
        //手机验证码验证
        if(CrSmsUtils.varifySmsCode(user.getPhone(), verifyCode)){
            User userFromDataBase=dao.getByPhone(user);
            if(null!=userFromDataBase){
                //匹配成功，更新数据（wxid等来自微信信息）
                if(StringPlusUtils.isNotBlank(user.getWxCode())){
                    //从根据code从微信服务器获取openId
                    boolean wxResult=WxMiniProgramUtils.code2Session(0,user.getWxCode(), new WxMiniProgramUtils.WxCallback() {
                        @Override
                        public void success(JSONObject result) {
                            user.setWxId(result.get("openid").toString());
                            //session_key
                            //unionid
                        }

                        @Override
                        public void error(JSONObject result) {

                        }
                    });
                }
                //姓名置空,不更新
                user.setPersonName(null);
                BeanUtils.copyBeanNotNull2Bean(user, userFromDataBase);//将user非NULL值覆盖userFromDataBase中的值
                userFromDataBase.setIsNewRecord(false);
                save(userFromDataBase);
                return generateMiniprogramLoginInfo(userFromDataBase);
            }else{
                //无法匹配到，则添加用户,然后再登录
                if(StringUtils.isNotBlank(user.getUserName())){
                    userFromDataBase=getByUserName(user.getUserName());
                    if(null!=userFromDataBase){
                        return ResponseResult.failByParam("用户名称已存在");
                    }
                }
                int rs=0;
                /*switch (userType){
                    case 1:
                        rs=addEmployeeFromMiniprogram(user);
                        break;
                    case 2:
                        rs=addFriendsFromMiniprogram(user);
                        break;
                }*/
                rs=addFriendsFromMiniprogram(user);//默认注册是朋友汇用户

                if(1==rs){
                    //为该用户登录
                    return generateMiniprogramLoginInfo(user);
                }else {
                    return ResponseResult.failByBusiness("登录失败");
                }
            }
        }else{
            return ResponseResult.failByBusiness("验证码无效");
        }
    }

    /**
     * 快速构建小程序登录信息
     * @param user
     * @return
     */
    private ResponseResult generateMiniprogramLoginInfo(User user){
        //生成token
        JWTUtils jwt=JWTUtils.getInstance();
        String token=jwt.getToken(user.getId().toString()+","+user.getUserName());

        List<Menu> menus=getUserMenus(user.getId());

        ResponseResult responseResult=ResponseResult.ok();
        responseResult.put("user", user);
        responseResult.put("menus", menus);
        responseResult.put("token", token);
        return responseResult;
    }

    /**
     * 加载指定用户权限
     * <br/><strong>优先从缓存加载</strong>
     * @param userId 用户id
     * @return
     */
    public List<Menu> getUserMenus(Long userId){
        //从缓存加载该用户权限
        List<Menu> menus= (List<Menu>) J2CacheUtils.get(Global.USER_MENU_REGION, userId.toString());
        if(null==menus || menus.isEmpty()){
            //缓存没有找到，从数据库加载
            menus=menuService.findListByUserId(userId);
            //写入缓存
            J2CacheUtils.put(Global.USER_MENU_REGION, userId.toString(), menus);

            //拼接菜单code 字符串,用于权限验证框架
            List<String> menuCodeList=(List<String>) J2CacheUtils.get(Global.USER_MENU_CODE_REGION, userId.toString());
            if(null==menuCodeList || menuCodeList.isEmpty()){
                menuCodeList=Lists.newArrayList();
                for (Menu menuTemp:menus) {
                    menuCodeList.add(menuTemp.getMenuCode());
                }
                //写入缓存
                J2CacheUtils.put(Global.USER_MENU_CODE_REGION, userId.toString(), menuCodeList);
            }
        }
        return menus;
    }

    /**
     * 用户登出
     */
    public void loginOut(HttpServletRequest request){
        J2CacheUtils.remove(Global.TOKEN_REGION, UserAgentUtils.getIpAddr(request));
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteToggle(User user) {
        if(1==user.getId()){//超级管理员不允许禁用
            return 0;
        }
        return super.deleteToggle(user);
    }


    /**
     * 根据手机号码查询用户
     * @param user 封装phone
     * @return
     */
    public User getByPhone(User user) {
        return dao.getByPhone(user);
    }

    /**
     * 获取当前用户
     * @return
     */
    public User getCurrentUser(){
        HttpServletRequest request=UserAgentUtils.getCurrentRequest();
        if (request==null){
            return null;
        }
        long userId=Long.valueOf(request.getHeader(Global.SECURITY_TOKEN_USERID));
        User currentUser = get(userId);
        return currentUser;

    }

    /**
     * 从excel导入插入数据
     * @param user
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public int saveForExcel(User user) throws OptimisticLockException {
        //处理非法输入的phone
        user.setPhone(StringPlusUtils.clearInvisibleUnicode(user.getPhone()));
        //匹配角色信息
        if(StringPlusUtils.isBlank(user.getRoleName())){
            return 0;
        }else{
            List<Dictionary> roles=DictUtils.getByDictionaryName("角色");
            for (Dictionary role:roles) {
                if(user.getRoleName().equals(role.getDetailName())){
                    user.setRoleId(role.getId());
                }
            }
            if(null==user.getRoleId()){
                return 0;
            }
        }

        //匹配用户类型信息
        if(StringPlusUtils.isBlank(user.getTypeName())){
            return 0;
        }else{
            List<Dictionary> userTypes=DictUtils.getByDictionaryName("用户类型");
            for (Dictionary type:userTypes) {
                if(user.getTypeName().equals(type.getDetailName())){
                    user.setTypeId(type.getId());
                    //后台用户，密码初始化为默认
                    if(user.getTypeId()==DictUtils.getByCode(Global.DICT_USER_TYPE_BACKSTAGE).getId()){
                        user.setPassword(Global.getConfig(Global.DEFAULT_PASSWORD));
                    }
                }
            }
            if(null==user.getTypeId()){
                return 0;
            }
        }

        //匹配组织
        if(StringPlusUtils.isNotBlank(user.getOrgName())){
            Organization org=organizationService.getByName(user.getOrgName());
            if(null==org){
                return 0;
            }else {
                user.setOrgId(org.getId());
            }
        }

        //匹配职级
        if(StringPlusUtils.isNotBlank(user.getRankName())){
            BlRank rank=blRankService.findUniqueByRankName(user.getRankName());
            if(null==rank){
                return 0;
            }else {
                user.setRankId(rank.getId());
            }
        }

        //默认已审核
        Dictionary examineReviewed=DictUtils.getByCode(Global.REVIEWED);
        user.setExamineStatusId(examineReviewed.getId());
        user.setExamineStatusName(examineReviewed.getDetailName());

        //匹配审核状态
        /*if(StringPlusUtils.isBlank(user.getExamineStatusName())){
            return 0;
        }else{
            List<Dictionary> examineStatuss=DictUtils.getByDictionaryName("审核状态");
            for (Dictionary status:examineStatuss) {
                if(user.getExamineStatusName().equals(status.getDetailName())){
                    user.setExamineStatusId(status.getId());
                }
            }
            if(null==user.getExamineStatusId()){
                return 0;
            }
        }*/

        return add(user);
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据
     * @param type 1=员工 2=朋友汇
     * @return
     */
    public String getAccessToken(int type){
        return WxMiniProgramUtils.getAccessToken(type);
    }

    /**
     * 获取拥有对应菜单的用户列表
     * @param type 类型<ul><li>1=优惠券审批权限</li><li>2=员工申请优惠券审批权限</li></ul>
     * @return
     */
    public Set<User> findListByMenu(int type){
        List<User> users=Lists.newArrayList();
        Set<User> userSet= Sets.newHashSet();
        switch (type){
            case 1://优惠券审批权限
                users= dao.findListByMenuId(new UserMenu(menuService.getByMenuCode("approval:examineCouponEmployee")));
                break;
            case 2://员工申请优惠券审批权限
                users=  dao.findListByMenuId(new UserMenu(menuService.getByMenuCode("approval:examineInfo")));
                break;
        }
        userSet.addAll(users);
        userSet.add(get(1l));//默认有超级管理员
        return userSet;
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
    public ResponseResult  getWxacodeUnlimit(String scene,String page,Integer width,Boolean autoColor,String lineColor,Boolean hyaline){
        String result=WxMiniProgramUtils.getWxacodeUnlimit(WxMiniProgramUtils.getAccessToken(1), scene, page, width, autoColor, lineColor, hyaline);
        if(StringPlusUtils.isNotBlank(result)){
            return ResponseResult.ok(result);
        }else {
            return ResponseResult.failByBusiness("获取小程序二维码失败");
        }
    }
}
