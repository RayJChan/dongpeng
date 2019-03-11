package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 定时任务表 实体类
 */
public class ScheduleJob extends DataEntity<ScheduleJob> {
    @DataName("任务名")
    private String taskName;
    @DataName("任务组")
    private String taskGroup;
    @DataName("定时规则")
    private String expression;
    @DataName("启用状态")
    private String status;
    @DataName("通知用户id")
    private String notifyUser;
    @DataName("任务类")
    private String classname;
    @DataName("描述")
    private String description;

    public ScheduleJob(){
        super();
        this.dataTableName="定时任务";
    }

    public ScheduleJob(Long id){
        super(id);
    }

    public ScheduleJob(Long id,boolean deleteFlag){
        this(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull(message="任务名不能为空")
    @ExcelField(title="任务名", align=2, sort=10)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @NotNull(message="任务组不能为空")
    @ExcelField(title="任务组", align=2, sort=20)
    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    @NotNull(message="定时规则不能为空")
    @ExcelField(title="定时规则", align=2, sort=30)
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @NotNull(message="启用状态不能为空")
    @ExcelField(title="启用状态", align=2, sort=40)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ExcelField(title="通知用户", align=2, sort=50)
    public String getNotifyUser() {
        return notifyUser;
    }

    public void setNotifyUser(String isInfo) {
        this.notifyUser = isInfo;
    }

    @NotNull(message="任务类不能为空")
    @ExcelField(title="任务类", align=2, sort=60)
    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @ExcelField(title="描述", align=2, sort=70)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
