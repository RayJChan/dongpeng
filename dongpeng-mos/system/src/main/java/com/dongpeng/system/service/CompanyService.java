package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.entity.system.Company;
import com.dongpeng.entity.system.UserCompany;
import com.dongpeng.system.dao.CompanyDao;
import com.google.common.collect.Lists;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CompanyService extends BaseCrudService<CompanyDao,Company> {

    /**
     * 根据公司编码查询公司
     * @param companyCode 公司编码
     * @return
     */
    public Company getByCompanyCode(String companyCode){
        return dao.getByCompanyCode(companyCode);
    }

    /**
     * 根据公司编码查询公司
     * companyName 公司编码
     * @return
     */
    public List<Company> getByCompanyName(String companyName){
        return dao.getByCompanyName(companyName);
    }

    /**
     * 删除用户公司
     * @param userCompany 封装用户id 和其他删除条件参数
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUserCompany(UserCompany userCompany){
        int rs=dao.deleteUserCompany(userCompany);
        return rs;
    }

    /**
     * 插入用户公司
     * @param userId 用户id
     * @param companyIds 公司id集合，多个用逗号分隔
     * @return
     */
    @Transactional(readOnly = false)
    public int insertUserCompany(Long userId,String companyIds){
        int rs=0;
        List<UserCompany> userCompanys= Lists.newArrayList();
        String idArray[] =companyIds.split(",");
        for(String companyId : idArray){
            UserCompany userCompany=new UserCompany(userId,Long.valueOf(companyId));
            //校验是否重复
            List<Company> companies= findListByUserId(userCompany);
            if(null==companies || companies.isEmpty()){
                //设置用户公司属性
                userCompany.setCurrentUser();
                userCompany.preInsert();
                userCompanys.add(userCompany);
            }

        }
        if(0<userCompanys.size()){
            rs=dao.insertUserCompany(userCompanys);
        }
        return rs;
    }

    /**
     *  根据用户id查找公司信息
     * @param userCompany 封装用户id
     * @return
     */
    public List<Company> findListByUserId(UserCompany userCompany){
        //超级管理员拥有全部公司权限
        if(1==userCompany.getUserId()){
            return dao.findAllList(new Company());
        }else{
            return dao.findListByUserId(userCompany);
        }
    }

    /**
     *  根据用户id查找该用户未有的公司信息
     * @param userCompany 封装用户id
     * @return
     */
    public List<Company> findListNotInUserId(UserCompany userCompany){
        //超级管理员不需要返回
        if(Global.ADMINISTRATOR_ID ==userCompany.getUserId()){
            return null;
        }else{
            return dao.findListNotInUserId(userCompany);
        }
    }

    @Override
    public String createDataScopeSql(Company entity) {
        return null;
    }
}
