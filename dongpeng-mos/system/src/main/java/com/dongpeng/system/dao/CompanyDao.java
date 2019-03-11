package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Company;
import com.dongpeng.entity.system.UserCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface CompanyDao  extends BaseCrudDao<Company> {
    /**
     * 根据公司编码查询公司
     * @param companyCode 公司编码
     * @return
     *
     */
    public Company getByCompanyCode(String companyCode);

    /**
     * 根据公司名称查询公司
     * @return
     *
     */
    public List<Company> getByCompanyName(@Param("companyName") String companyName);


    /**
     * 删除用户公司
     * @param userCompany 封装用户id 和其他删除条件参数
     * @return
     */
    public int deleteUserCompany(UserCompany userCompany);

    /**
     * 插入用户公司
     * @param userCompanyList 用户菜单列表
     * @return
     */
    public int insertUserCompany(@Param(value = "userCompanyList")List<UserCompany> userCompanyList);

    /**
     * 根据用户id查找公司信息
     * @param userCompany 封装用户id
     * @return
     */
    public List<Company> findListByUserId(UserCompany userCompany);

    /**
     * 根据用户id查找该用户未有的公司信息
     * @param userCompany 封装用户id
     * @return
     */
    public List<Company> findListNotInUserId(UserCompany userCompany);


}
