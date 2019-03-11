package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.RoleMenu;
import com.dongpeng.entity.system.UserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface MenuDao extends BaseCrudDao<Menu> {
    /**
     * 根据上级id获得菜单
     * @param upId 上级id
     * @return
     */
    public List<Menu> getByUp(Long upId);

    /**
     * 根据根据菜单编码获得菜单
     * @param menuCode 菜单编码
     * @return
     */
    public Menu getByMenuCode(String menuCode);

    /**
     * 根据菜单名称获得菜单
     * @param menuName 菜单名称
     * @return
     */
    public Menu getByMenuName(String menuName);

    /**
     * 删除数据（物理删除，从数据库中彻底删除）
     *
     * @param menu
     * @return
     */
    public int delete(Menu menu);

    /**
     * 删除角色所有菜单
     * @param roleId 角色id（即系字典项id）
     * @return
     */
    public int deleteRoleMenu(Long roleId);

    /**
     * 插入角色菜单
     * @param roleMenuList 角色菜单列表
     * @return
     */
    public int insertRoleMenu(@Param(value = "roleMenuList") List<RoleMenu> roleMenuList);

    /**
     * 根据角色id查找菜单信息
     * @param roleMenu 封装角色id（即系字典项id）
     * @return
     */
    public List<Menu> findListByRoleId(RoleMenu roleMenu);

    /**
     * 删除用户菜单
     * @param userMenu 封装用户id 和其他删除条件参数
     * @return
     */
    public int deleteUserMenu(UserMenu userMenu);

    /**
     * 插入用户菜单
     * @param userMenuList 用户菜单列表
     * @return
     */
    public int insertUserMenu(@Param(value = "userMenuList") List<UserMenu> userMenuList);

    /**
     * 根据用户id查找菜单信息
     * @param userMenu 封装用户id
     * @return
     */
    public List<Menu> findListByUserId(UserMenu userMenu);

    /**
     * 更新子节点parentids
     * @param menu 封装id，parentIds
     * @return
     */
    public int updateParentids(Menu menu);
}
