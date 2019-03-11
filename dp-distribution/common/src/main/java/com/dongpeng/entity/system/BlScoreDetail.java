package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.entity.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 积分明细
 */
public class BlScoreDetail extends DataEntity<BlScoreDetail> {

    @DataName("用户ID")
    private Long userId;

    @DataName("用户优惠券id")
    private  Long pcId; //用户优惠券id

    @DataName("积分来源id")
    private  Long sourceId; //积分来源id（字典项）

    @DataName("积分来源名称")
    private  String sourceName; //积分来源名称

    @DataName("消费组织名称")
    private  String orgName; //消费组织名称

    @DataName("消费时间")
    private  Date produceTime; //消费时间

    @DataName("分值")
    private  int score; //分值（+增 -减）

    private Date produceBeginTime;

    private Date produceEndTime;

    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPcId() {
        return pcId;
    }

    public void setPcId(Long pcId) {
        this.pcId = pcId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getProduceBeginTime() {
        return produceBeginTime;
    }

    public void setProduceBeginTime(Date produceBeginTime) {
        this.produceBeginTime = produceBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getProduceEndTime() {
        return produceEndTime;
    }

    public void setProduceEndTime(Date produceEndTime) {
        this.produceEndTime = produceEndTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
