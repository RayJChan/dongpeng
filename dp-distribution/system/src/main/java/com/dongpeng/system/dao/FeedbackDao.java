package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Feedback;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.RoleMenu;

import java.util.List;

@MyBatisDao
public interface FeedbackDao extends BaseCrudDao<Feedback> {

    public List<Feedback> findListByPersonId(Long person_id);

    /**
     * @param content
     * @return
     */
    public List<Feedback> findListIndistinct(String content);

}
