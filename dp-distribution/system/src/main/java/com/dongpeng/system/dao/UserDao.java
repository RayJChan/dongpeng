package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.User;
import com.dongpeng.entity.system.UserMenu;

import java.util.List;

@MyBatisDao
public interface UserDao extends BaseCrudDao<User> {

    /**
     * 根据用户名查询用户
     * @param user 封装用户名
     * @return
     */
    public User getByUserName(User user);

    /**
     * 根据微信id查找
     * @param user 封装Wxid
     * @return
     */
    public User getByWxId(User user);

    /**
     * 根据手机号码查找
     * @param user 封装phone
     * @return
     */
    public User getByPhone(User user);

    /**
     * 根据菜单id查找用户信息
     * @param userMenu 封装菜单id
     * @return
     */
    public List<User> findListByMenuId(UserMenu userMenu);

}
