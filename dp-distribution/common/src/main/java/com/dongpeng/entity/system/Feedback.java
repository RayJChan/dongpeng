package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * 意见反馈 实体类
 *
 * @author tmz
 */
public class Feedback extends DataEntity<Feedback> {
    private static final long serialVersionUID = 1L;

    //@DataName("评分")
    private BigDecimal score;
    @DataName("内容")
    private String content;
    //@DataName("用户ID")
    private Long personId;
    //@DataName("用户账号")
    private String personAccount;

    public Feedback(){
        super();
        this.dataTableName = "意见反馈";
    }

    //@ExcelField(title = "评分")
    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @NotNull
    @ExcelField(title = "提交内容" )
    @Length(min = 1, max = 200, message = "评价内容长度限制为1-200个字符长度")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //@ExcelField(title = "用户账号" )
    //@Length(min = 1, max = 40, message = "用户账号过长")
    public String getPersonAccount() {

        return personAccount;
    }

    public void setPersonAccount(String personAccount) {
        this.personAccount = personAccount;
    }

}
