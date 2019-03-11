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
 */
public class User extends DataEntity<User> {
    private static final long serialVersionUID = 1L;
    @DataName("用户名称")
    private String userName;      //用户名称
    @DataName("帐号密码")
    private String password;      //帐号密码
    @DataName("角色ID")
    private Long roleId;          //角色ID（字典项ID）
    @DataName("角色名称")
    private String roleName;      //角色名称（字典项名称）
    @DataName("联系方式")
    private String phone;         //联系方式

    @DataName("姓名")
    private String personName;
    @DataName("头像")
    private String head;
    @DataName("微信号")
    private String wxAccount;
    @DataName("微信id")
    private String wxId;
    @DataName("用户类型id")
    private Long typeId;
    @DataName("用户类型名称")
    private String typeName;
    @DataName("组织id")
    private Long orgId;
    @DataName("组织名称")
    private String orgName;
    @DataName("职级id")
    private Long rankId;
    @DataName("职级名称")
    private String rankName;
    @DataName("总积分")
    private Integer score;
    @DataName("审核状态id")
    private Long examineStatusId;
    @DataName("审核状态名称")
    private String examineStatusName;

    //######### 以下字段跟数据库无关 #########
    private String newPassword;// 新密码
    private String wxCode;//微信code，根据此code换去openid，即微信id


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

    public User(String userName){
        super();
        this.userName=userName;
    }

    public User(Long id,String userName){
        super();
        this.id=id;
        this.userName=userName;
    }

    @Override
    public void preInsert() {
        super.preInsert();
        this.password=StringUtils.isBlank(this.password)?null:PasswordUtils.entryptPasswordBySha1(password);
        this.score=0;
    }

    @Length(min = 2, max = 10, message = "用户名称必须介于 2 和 10 之间")
    @ExcelField(title = "用户名称")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //@NotNull(message = "角色id不能为空")
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

    @NotNull
    @Length(max = 11, message = "联系方式长度不能超过 11 位")
    @ExcelField(title = "联系方式")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonIgnore
    @Length(min = 6, max = 40, message = "密码长度必须介于 6 和 40 之间")
    //@ExcelField(title = "密码")
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

    @Length(min = 2, max = 20, message = "姓名必须介于 2 和 20 之间")
    @ExcelField(title = "姓名")
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Length(min = 2, max = 255, message = "头像必须介于 2 和 255 之间")
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Length(min = 2, max = 40, message = "微信号必须介于 2 和 40 之间")
    @ExcelField(title = "微信号",type = 1)
    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    @Length(min = 2, max = 64, message = "微信id必须介于 2 和 64 之间")
    @ExcelField(title = "微信id",type = 1)
    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    @NotNull(message = "用户类型id不能为空")
    //@ExcelField(title = "用户类型id")
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Length(min = 2, max = 20, message = "用户类型名称必须介于 2 和 20 之间")
    @ExcelField(title = "用户类型名称")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    //@ExcelField(title = "组织id")
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    //@Length(min = 2, max = 20, message = "组织名称必须介于 2 和 20 之间")
    @ExcelField(title = "组织名称")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    //@ExcelField(title = "职级id")
    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    //@Length(min = 2, max = 20, message = "职级名称必须介于 2 和 20 之间")
    @ExcelField(title = "职级名称")
    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    //@ExcelField(title = "总积分")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    //@ExcelField(title = "审核状态id")
    public Long getExamineStatusId() {
        return examineStatusId;
    }

    public void setExamineStatusId(Long examineStatusId) {
        this.examineStatusId = examineStatusId;
    }

    @ExcelField(title = "审核状态名称",type = 1)
    public String getExamineStatusName() {
        return examineStatusName;
    }

    public void setExamineStatusName(String examineStatusName) {
        this.examineStatusName = examineStatusName;
    }

    @JsonIgnore
    public String getWxCode() {
        return wxCode;
    }
    @JsonProperty
    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }
}
