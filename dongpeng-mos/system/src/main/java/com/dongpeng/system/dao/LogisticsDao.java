package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Logistics;
import com.dongpeng.entity.system.Logistics;
import com.dongpeng.entity.system.UserLogistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface LogisticsDao extends BaseCrudDao<Logistics> {


    /**
     * 删除用户物流
     * @param userLogistics 封装用户id 和其他删除条件参数
     * @return
     */
    public int deleteUserLogistics(UserLogistics userLogistics);

    /**
     * 插入用户物流
     * @param userLogisticsList 用户菜单列表
     * @return
     */
    public int insertUserLogistics(@Param(value = "userLogisticsList")List<UserLogistics> userLogisticsList);

    /**
     * 根据用户id查找物流信息
     * @param userLogistics 封装用户id
     * @return
     */
    public List<Logistics> findListByUserId(UserLogistics userLogistics);

    /**
     * 根据用户id查找该用户未有的物流信息
     * @param userLogistics 封装用户id
     * @return
     */
    public List<Logistics> findListNotInUserId(UserLogistics userLogistics);

    /**
     * 根据物流名称查询是否有相同物流
     * @param logisticsName
     * @return
     */
    Logistics getLogisticsName(String logisticsName);
}
