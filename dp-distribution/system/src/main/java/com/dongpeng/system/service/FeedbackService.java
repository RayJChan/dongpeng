package com.dongpeng.system.service;


import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;

import com.dongpeng.common.entity.Page;
import com.dongpeng.entity.system.Feedback;
import com.dongpeng.system.dao.FeedbackDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Service
@Transactional(readOnly = true)
public class FeedbackService extends BaseCrudService<FeedbackDao,Feedback> {

    @Override
    public String createDataScopeSql(Feedback entity) {
        return null;
    }

    /**
     * 新增一条反馈
     * @param feedback 封装用户数据
     * @return
     */
    @Transactional(readOnly = false)
    public int add(Feedback feedback) throws OptimisticLockException {
        //等待需求逻辑处理
        return super.save(feedback);
    }

    /**
     * 根据用户id查找反馈列表
     * @param person_id 用户id
     * @return
     */
    public List<Feedback> findListByPersonId(Long person_id){
        return dao.findListByPersonId(person_id);
    }

    public List<Feedback> searchList(String content) {
        List<Feedback> list = dao.findListIndistinct(content);
        System.out.println("list size:" + list.size());
        return dao.findListIndistinct(content);
    }

}
