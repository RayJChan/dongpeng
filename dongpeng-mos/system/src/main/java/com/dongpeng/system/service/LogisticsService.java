package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Logistics;
import com.dongpeng.entity.system.Logistics;
import com.dongpeng.entity.system.UserLogistics;
import com.dongpeng.system.dao.LogisticsDao;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LogisticsService extends BaseCrudService<LogisticsDao,Logistics> {
    @Override
    public String createDataScopeSql(Logistics entity) {
        return null;
    }

    /**
     * 删除用户物流
     * @param userLogistics 封装用户id 和其他删除条件参数
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUserLogistics(UserLogistics userLogistics){
        int rs=dao.deleteUserLogistics(userLogistics);
        return rs;
    }

    /**
     * 插入用户物流
     * @param userId 用户id
     * @param logisticsIds 物流id集合，多个用逗号分隔
     * @return
     */
    @Transactional(readOnly = false)
    public int insertUserLogistics(Long userId,String logisticsIds){
        int rs=0;
        List<UserLogistics> userLogisticss= Lists.newArrayList();
        String idArray[] =logisticsIds.split(",");
        for(String logisticsId : idArray){
            UserLogistics userLogistics=new UserLogistics(userId,Long.valueOf(logisticsId));
            //校验是否重复
            List<Logistics> companies= findListByUserId(userLogistics);
            if(null==companies || companies.isEmpty()){
                //设置用户物流属性
                userLogistics.setCurrentUser();
                userLogistics.preInsert();
                userLogisticss.add(userLogistics);
            }

        }
        if(0<userLogisticss.size()){
            rs=dao.insertUserLogistics(userLogisticss);
        }
        return rs;
    }

    /**
     *  根据用户id查找物流信息
     * @param userLogistics 封装用户id
     * @return
     */
    public List<Logistics> findListByUserId(UserLogistics userLogistics){
        //超级管理员拥有全部物流权限
        if(1==userLogistics.getUserId()){
            return dao.findAllList(new Logistics());
        }else{
            return dao.findListByUserId(userLogistics);
        }
    }

    /**
     *  根据用户id查找该用户未有的物流信息
     * @param userLogistics 封装用户id
     * @return
     */
    public List<Logistics> findListNotInUserId(UserLogistics userLogistics){
        //超级管理员不需要返回
        if(Global.ADMINISTRATOR_ID ==userLogistics.getUserId()){
            return null;
        }else{
            return dao.findListNotInUserId(userLogistics);
        }
    }

    /**
     * 根据物流名称查询是否有相同物流
     * @param logisticsName
     * @return
     */
    public Logistics getLogisticsName(String logisticsName) {
        return dao.getLogisticsName(logisticsName);
    }
}
