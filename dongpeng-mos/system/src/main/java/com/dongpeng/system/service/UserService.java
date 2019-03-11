package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.JWTUtils;
import com.dongpeng.common.utils.PasswordUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.dongpeng.entity.system.Department;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.User;
import com.dongpeng.entity.system.UserMenu;
import com.dongpeng.system.dao.UserDao;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class UserService extends BaseCrudService<UserDao,User> {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MenuService menuService;

    /*@Override
    @Transactional(readOnly = false)
    public int save(User user) {
        //如果部门id不为空，部门名称为空，自动查找补全部门名称
        if(null!=user.getDepartmentId() && StringUtils.isBlank(user.getDepartmentName())){
            Department department=departmentService.get(user.getDepartmentId());
            user.setDepartmentName(null==department?"":department.getDepartmentName());
        }
        //如果角色id不为空，角色名称为空，自动查找补全角色名称
        if(null!=user.getRoleId() && StringUtils.isBlank(user.getRoleName())){
            Dictionary role=DictUtils.getById(user.getRoleId());
            user.setRoleName(null==role?"":role.getDetailName());
        }
        //如果用户类型id不为空，用户类型名称为空，自动查找补全用户类型名称
        if(null!=user.getUserType() && StringUtils.isBlank(user.getUserTypename())){
            Dictionary userType=DictUtils.getById(user.getUserType());
            user.setUserTypename(null==userType?"":userType.getDetailName());
        }

        //############# 权限相关操作 ##############
        if(!user.getIsNewRecord() && null!=user.getRoleId()){
            //删除该用户-角色 对应所有用户菜单
            UserMenu userMenu=new UserMenu(user.getId());
            userMenu.setDictionaryId(user.getRoleId());
            menuService.deleteUserMenu(userMenu);
        }
        //为用户插入对应角色的 用户菜单
        List<Menu> menus=menuService.findListByRoleId(user.getRoleId());
        List<UserMenu> userMenus= Lists.newArrayList();
        for (Menu menu: menus) {
            userMenus.add(new UserMenu(user.getId(),menu.getId(),user.getRoleId()));
        }

        return super.save(user);
    }*/

    @Override
    public String createDataScopeSql(User user) {
        /*StringBuilder sql=new StringBuilder();
        sql.append(" AND ");*/
        return null;
    }

    /**
     * 新增一个用户
     * @param user 封装用户数据
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int add(User user) throws OptimisticLockException {
        //如果部门id不为空，部门名称为空，自动查找补全部门名称
        if(null!=user.getDepartmentId() && StringUtils.isBlank(user.getDepartmentName())){
            Department department=departmentService.get(user.getDepartmentId());
            user.setDepartmentName(null==department?"":department.getDepartmentName());
        }
        //如果角色id不为空，角色名称为空，自动查找补全角色名称
        if(null!=user.getRoleId() && StringUtils.isBlank(user.getRoleName())){
            Dictionary role=DictUtils.getById(user.getRoleId());
            user.setRoleName(null==role?"":role.getDetailName());
        }
        //如果用户类型id不为空，用户类型名称为空，自动查找补全用户类型名称
        if(null!=user.getUserType() && StringUtils.isBlank(user.getUserTypename())){
            Dictionary userType=DictUtils.getById(user.getUserType());
            user.setUserTypename(null==userType?"":userType.getDetailName());
        }

        //############# 权限相关操作 ##############
        user.preInsert();
        //为用户插入对应角色的 用户菜单
        if(null!=user.getRoleId()){
            List<Menu> menus=menuService.findListByRoleId(user.getRoleId());
            List<UserMenu> userMenus= Lists.newArrayList();
            for (Menu menu: menus) {
                userMenus.add(new UserMenu(user.getId(),user.getUserName(),menu.getId(),user.getRoleId()));
            }
            menuService.insertUserMenu(userMenus);
        }

        return super.save(user);
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
        //如果部门id不相等，部门名称为空，自动查找补全部门名称
        if(null!=formUser.getDepartmentId() && formUser.getDepartmentId()!=dataUser.getDepartmentId()
                && StringUtils.isBlank(formUser.getDepartmentName())){
            Department department=departmentService.get(formUser.getDepartmentId());
            formUser.setDepartmentName(null==department?"":department.getDepartmentName());
        }
        //如果用户类型id不相等，用户类型名称为空，自动查找补全用户类型名称
        if(null!=formUser.getUserType() && formUser.getUserType()!=dataUser.getUserType()
                && StringUtils.isBlank(formUser.getUserTypename())){
            Dictionary userType=DictUtils.getById(formUser.getUserType());
            formUser.setUserTypename(null==userType?"":userType.getDetailName());
        }
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

        BeanUtils.copyBeanNotNull2Bean(formUser, dataUser);//将formUser非NULL值覆盖dataUser中的值
        return super.save(dataUser);
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
     * 根据工号查询用户
     * @param userCode 工号
     * @return
     */
    public User getByUserCode(String userCode){
        return dao.getByUserCode(new User(userCode));
    }

    /**
     * 用户登录
     * @param user 封装 userCode、password
     * @return 登录成功返回user信息 <br/>登录失败，返回null
     */
    public User login(User user){
        User userFromDataBase=getByUserCode(user.getUserCode());
        if(null!=userFromDataBase){
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
     * 获取当前用户
     * @return
     */
    public User getCurrentUser(){
        HttpServletRequest request=UserAgentUtils.getCurrentRequest();
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
        return save(user);
    }
}
