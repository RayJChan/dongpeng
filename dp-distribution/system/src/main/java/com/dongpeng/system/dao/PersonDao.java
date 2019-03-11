package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Person;
@MyBatisDao
public interface PersonDao extends BaseCrudDao<Person> {

    /**
     * 根据小程序账户名查找
     * @param person 封装account
     * @return
     */
    public Person getByAccount(Person person);

    /**
     * 根据微信id查找
     * @param person 封装Wxid
     * @return
     */
    public Person getByWxId(Person person);
}
