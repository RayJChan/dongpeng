package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.RoleMenu;
import com.dongpeng.entity.system.User;
import com.dongpeng.entity.system.UserMenu;
import com.dongpeng.system.dao.MenuDao;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuService extends BaseCrudService<MenuDao,Menu> {
    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = false)
    public int save(Menu menu) throws OptimisticLockException {
        //如果父id不为空，则获取其名称、parentids
        if(null!=menu.getParentId() && 0!=menu.getParentId()){
            Menu parentMenu=dao.get(menu.getParentId());
            menu.setParentName(parentMenu.getMenuName());
            menu.setParentIds(
                    StringUtils.isNotBlank(parentMenu.getParentIds())?
                            parentMenu.getParentIds()+","+parentMenu.getId()
                            :parentMenu.getId().toString());
        }
        int rs=super.save(menu);
        if(rs>0){
            if(0<rs && !menu.getIsNewRecord()){
                //更新其子节点parentids
                dao.updateParentids(menu);
            }
            J2CacheUtils.clear(Global.USER_MENU_REGION);
            J2CacheUtils.clear(Global.USER_MENU_CODE_REGION);
        }
        return rs;
    }

    /**
     * 物理删除菜单
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public int delete(Menu entity) {
        int rs=dao.delete(entity);
        if(rs>0){
            J2CacheUtils.clear(Global.USER_MENU_REGION);
            J2CacheUtils.clear(Global.USER_MENU_CODE_REGION);
        }
        return rs;
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteToggle(Menu menu) {
        Menu temp=get(menu.getId());//根据id查找region，加载出parentId
        if(false==menu.getDeleteFlag() && null!=temp.getParentId()){//如果启用，且父节点不为空，则要判断父节点是否被禁用，父节点被禁用时不能执行启用操作
            Menu parent=get(temp.getParentId());
            if(true==parent.getDeleteFlag()){
                return 0;
            }
        }
        int rs=super.deleteToggle(menu);
        if(rs>0){
            J2CacheUtils.clear(Global.USER_MENU_REGION);
            J2CacheUtils.clear(Global.USER_MENU_CODE_REGION);
        }
        return rs;
    }

    /**
     * 根据上级id获得菜单
     * @param upId 上级id
     * @return
     */
    public List<Menu> getByUp(Long upId){
        return  dao.getByUp(upId);
    }

    /**
     * 根据根据菜单编码获得菜单
     * @param menuCode 菜单编码
     * @return
     */
    public Menu getByMenuCode(String menuCode){
        return dao.getByMenuCode(menuCode);
    }

    /**
     * 删除角色所有菜单
     * @param roleId 角色id（即系字典项id）
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteRoleMenu(Long roleId){
        return dao.deleteRoleMenu(roleId);
    }

    /**
     * 插入角色菜单
     * @param roleId 角色id
     * @param menuIds 角色菜单id集合，多个用英文逗号分隔
     * @return
     */
    @Transactional(readOnly = false)
    public int insertRoleMenu(Long roleId, String menuIds){
        String[] menuIdList=menuIds.split(",");
        //删除该角色所有旧菜单
        deleteRoleMenu(roleId);
        List<RoleMenu> roleMenuList=Lists.newArrayList();
        for (String menuId:menuIdList) {
            //设置角色菜单属性
            if(StringPlusUtils.isNotBlank(menuId)){
                RoleMenu roleMenu=new RoleMenu(roleId,Long.valueOf(menuId));
                roleMenu.preInsert();

                //加入到角色菜单列表
                roleMenuList.add(roleMenu);
            }
        }
        if(roleMenuList.size()>0){
            return dao.insertRoleMenu(roleMenuList);
        }else {
            return 1;
        }

    }

    /**
     * 根据角色id查找菜单信息
     * @param roleId 角色id（即系字典项id）
     * @return
     */
    public List<Menu> findListByRoleId(Long roleId){
        return dao.findListByRoleId(new RoleMenu(roleId));
    }

    /**
     * 删除用户菜单
     * @param userMenu 封装用户id 和其他删除条件参数
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUserMenu(UserMenu userMenu){
        int rs=dao.deleteUserMenu(userMenu);
        if(rs>0){
            J2CacheUtils.clear(Global.USER_MENU_REGION);
            J2CacheUtils.clear(Global.USER_MENU_CODE_REGION);
        }
        return rs;
    }

    /**
     * 插入用户菜单
     * @param userId 用户id
     * @param menuIds 菜单id集合，多个用英文逗号分隔
     * @return
     */
    @Transactional(readOnly = false)
    public int insertUserMenu(Long userId, String menuIds){
        User user=userService.get(userId);
        if(null!=user){
            String[] menuIdList=menuIds.split(",");
            //如果新菜单数据id有值，则删除该用户所有旧菜单
            if(0<menuIdList.length){
                deleteUserMenu(new UserMenu(userId));
            }
            List<UserMenu> userMenuList=Lists.newArrayList();
            for (String menuId:menuIdList) {
                //设置用户菜单属性
                UserMenu userMenu=new UserMenu(userId,user.getUserName(),Long.valueOf(menuId));
                userMenu.preInsert();

                //加入到用户菜单列表
                userMenuList.add(userMenu);
            }
            int rs=dao.insertUserMenu(userMenuList);
            if(rs>0){
                J2CacheUtils.clear(Global.USER_MENU_REGION);
                J2CacheUtils.clear(Global.USER_MENU_CODE_REGION);
            }
            return rs;
        }else{
            //没有匹配的用户，插入数为0
            return 0;
        }

    }

    /**
     * 插入用户菜单
     * @param userMenus 用户菜单列表
     * @return
     */
    @Transactional(readOnly = false)
    public int insertUserMenu(List<UserMenu> userMenus){
        //如果新菜单数据id有值，则删除该用户所有旧菜单
        if(0<userMenus.size()){
            User user=userService.get(userMenus.get(0).getUserId());
            if(null!=user){
                deleteUserMenu(new UserMenu(userMenus.get(0).getUserId()));
                for (UserMenu userMenu:userMenus) {
                    //设置用户菜单属性
                    userMenu.setCurrentUser();
                    userMenu.preInsert();
                    userMenu.setUserName(user.getUserName());
                }
            }

            int rs=dao.insertUserMenu(userMenus);
            if(rs>0){
                J2CacheUtils.clear(Global.USER_MENU_REGION);
                J2CacheUtils.clear(Global.USER_MENU_CODE_REGION);
            }
            return rs;
        }else {
            return 0;
        }
    }

    /**
     * 根据用户id查找菜单信息
     * @param userId 用户id
     * @return
     */
    public List<Menu> findListByUserId(Long userId){
        if(Global.ADMINISTRATOR_ID==userId){
            //超级管理员加载全部菜单
            return dao.findAllList(new Menu());
        }else{
            return dao.findListByUserId(new UserMenu(userId));
        }
    }

    @Override
    public String createDataScopeSql(Menu entity) {
        return null;
    }

    /**
     * 从excel导入插入数据
     * @param menu
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public int saveForExcel(Menu menu) throws OptimisticLockException {
        if(StringUtils.isNotBlank(menu.getParentName())){
            Menu menuParent=dao.getByMenuName(menu.getParentName());
            if(null!=menuParent){
                menu.setParentId(menuParent.getId());
            }else{
                return 0;//该条数据插入失败
            }
        }
        return save(menu);
    }

}
