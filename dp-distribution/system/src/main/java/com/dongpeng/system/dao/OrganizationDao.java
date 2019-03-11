package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.Organization;
import com.dongpeng.entity.system.UserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface OrganizationDao extends BaseCrudDao<Organization> {
    /**
     * 根据上级id获得组织
     * @param parentId 上级id
     * @return
     */
    public List<Menu> getByParentId(Long parentId);

    /**
     * 根据name获得组织
     * @param orgName 组织名称
     * @return
     */
    public Organization getByName(@Param("orgName") String orgName);

    /**
     * 根据name和parentname获得组织
     * @param orgName 组织名称
     * @param parentName 上级组织名称
     * @return
     */
    public Organization getByNameAndParentName(@Param("orgName") String orgName,@Param("parentName") String parentName);

    /**
     * 根据code获得组织
     * @param orgCode 组织名称
     * @return
     */
    public Organization getByCode(@Param("orgCode") String orgCode);

    /**
     * 根据用户id查找组织信息
     * @param userOrg 封装用户id
     * @return
     */
    public List<Organization> findListByUserId(UserOrg userOrg);

    /**
     * 插入用户组织
     * @param userOrgList 用户组织列表
     * @return
     */
    public int insertUserOrg(@Param(value = "userOrgList")List<UserOrg> userOrgList);

    /**
     * 删除用户组织
     * @param userOrg 封装用户id 和其他删除条件参数
     * @return
     */
    public int deleteUseOrg(UserOrg userOrg);

}
