package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.utils.PasswordUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户表 实体类
 * @author wuhongda
 */
public class User extends DataEntity<User> {
    private static final long serialVersionUID = 1L;
    /**
     * 用户工号
     */
    @DataName("用户工号")
    private String userCode;
    /**
     *  用户名称
     */
    @DataName("用户名称")
    private String userName;
    /**
     *  帐号密码
     */
    @DataName("帐号密码")
    private String password;
    /**
     *  部门ID
     */
    @DataName("部门ID")
    private Long departmentId;
    /**
     *  部门名称
     */
    @DataName("部门名称")
    private String departmentName;
    /**
     *角色ID
     */
    @DataName("角色ID")
    private Long roleId;
    /**
     *角色名称
     */
    @DataName("角色名称")
    private String roleName;
    /**
     *联系方式
     */
    @DataName("联系方式")
    private String phone;
    /**
     *用户类型ID
     */
    @DataName("用户类型ID")
    private Long userType;
    /**
     *用户类型名称
     */
    @DataName("用户类型名称")
    private String userTypename;
    /**
     *绑定用户
     */
    @DataName("绑定用户")
    private String userBinding;


    /**
     * #########以下字段跟数据库无关#########
     */
    /**
     * 新密码
     */
    private String newPassword;


    public User(){
        super();
        this.dataTableName="用户";
    }

    public User(Long id){
        super(id);
    }

    public User(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }

    public User(String userCode){
        super();
        this.userCode=userCode;
    }

    @Override
    public void preInsert() {
        super.preInsert();
        this.password=StringUtils.isBlank(this.password)?null:PasswordUtils.entryptPasswordBySha1(password);
    }

    @NotNull
    @Length(min = 2, max = 10, message = "用户工号必须介于 2 和 10 之间")
    @ExcelField(title = "用户工号")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "用户名称必须介于 2 和 10 之间")
    @ExcelField(title = "用户名称")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NotNull(message = "部门id不能为空")
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @ExcelField(title = "部门名称")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @NotNull(message = "角色id不能为空")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @ExcelField(title = "角色名称")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Length(max = 11, message = "联系方式长度不能超过 11 位")
    @ExcelField(title = "联系方式")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotNull(message = "用户类型不能为空")
    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    @ExcelField(title = "用户类型")
    public String getUserTypename() {
        return userTypename;
    }

    public void setUserTypename(String userTypename) {
        this.userTypename = userTypename;
    }

    public String getUserBinding() {
        return userBinding;
    }

    @ExcelField(title = "绑定用户")
    public void setUserBinding(String userBinding) {
        this.userBinding = userBinding;
    }


    @JsonIgnore
    @NotNull
    @Length(min = 6, max = 40, message = "密码长度必须介于 6 和 40 之间")
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getNewPassword() {
        return newPassword;
    }

    @JsonProperty
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
