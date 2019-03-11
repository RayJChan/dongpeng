package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.User;

@MyBatisDao
public interface UserDao extends BaseCrudDao<User> {

    /**
     * 根据工号查询用户
     * @param user 封装用户工号
     * @return
     */
    public User getByUserCode(User user);

}
