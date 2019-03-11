package com.dongpeng.common.entity;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.utils.Encodes;
import com.dongpeng.common.utils.IdGen;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.SnowflakeIdUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Entity公用字段父类
 * @param <T>
 */
public class DataEntity<T> extends BaseEntity<T> {
    private static final long serialVersionUID = 1L;

    protected Long createrId; //创建人id
    protected String createrName; //创建人名称
    protected Date createTime;    // 创建日期
    protected Long modifierId; //修改id
    protected String modifierName; //修改人名称
    protected Date modifyTime;    // 修改日期
    protected Boolean deleteFlag;    // 删除标记（true：删除；false：正常；）
    protected Integer version;  //数据版本号

    public DataEntity() {
        super();
        this.deleteFlag = DEL_FLAG_NORMAL;
    }

    public DataEntity(Long id) {
        super(id);
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
        if (!this.isNewRecord) {
            setId(SnowflakeIdUtils.getInstance().nextId());
        }
        this.modifyTime = new Date();
        this.createTime = this.modifyTime;
        this.version=0;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate() {

        this.modifyTime = new Date();
    }

    /**
     * 设置当前操作用户
     */
    public void setCurrentUser(){
        HttpServletRequest request=UserAgentUtils.getCurrentRequest();
        if(null!=request && StringPlusUtils.isNotBlank(request.getHeader(Global.SECURITY_TOKEN_USERNAME))){
            String userName=Encodes.decodeBase64String(request.getHeader(Global.SECURITY_TOKEN_USERNAME));//base64解码，解决中文乱码
            long userId=Long.valueOf(request.getHeader(Global.SECURITY_TOKEN_USERID));
            if (this.getIsNewRecord()) {
                this.createrId=userId;
                this.createrName=userName;
            }
            this.modifierId=userId;
            this.modifierName=userName;

            this.currentId=userId;
        }
    }

    @ExcelField(title = "创建时间"  ,type = 1,sort = 1000,isMust = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ExcelField(title = "修改时间",type = 1,sort = 1100,isMust = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @ExcelField(title = "是否有效",type = 1,sort = 1050,isMust = true)
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = null==deleteFlag?DEL_FLAG_NORMAL: deleteFlag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    @ExcelField(title = "创建人",type = 1,sort = 1200,isMust = true)
    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    @ExcelField(title = "修改人",type = 1,sort = 1300,isMust = true)
    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }
}
