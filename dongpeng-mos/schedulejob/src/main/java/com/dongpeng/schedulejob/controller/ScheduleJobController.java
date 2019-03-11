package com.dongpeng.schedulejob.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.ScheduleJob;
import com.dongpeng.schedulejob.service.ScheduleJobService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 定时任务管理接口controller
 */
@RestController
@RequestMapping("/job")
public class ScheduleJobController extends BaseDataController<ScheduleJobService,ScheduleJob> {

    @Override
    public ResponseResult add(@RequestBody ScheduleJob scheduleJob, HttpServletRequest request, Model model) throws OptimisticLockException {
        if(!beanValidator(model, scheduleJob)){
            return ResponseResult.failByParam(model.asMap().get(MESSAGE).toString());
        }

        //验证cron表达式
        if(!CronExpression.isValidExpression(scheduleJob.getExpression())){
            return ResponseResult.failByParam("cron表达式不正确");
        }

        int rs=service.save(scheduleJob);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    @Override
    @EnableDetailLog(serviceclass = ScheduleJobService.class,handleName = "更新定时任务")
    public ResponseResult update(@RequestBody ScheduleJob scheduleJob, HttpServletRequest request, Model model) throws Exception {
        if(null==scheduleJob.getId()){
            return ResponseResult.failByParam("id不能为空");
        }
        ScheduleJob temp= service.get(scheduleJob.getId());

        if(null==temp){
            return ResponseResult.failByParam("id 无效");
        }

        //验证cron表达式
        if(StringUtils.isNotBlank(scheduleJob.getExpression()) && !CronExpression.isValidExpression(scheduleJob.getExpression())){
            return ResponseResult.failByParam("cron表达式不正确");
        }

        BeanUtils.copyBeanNotNull2Bean(scheduleJob, temp);//将非NULL值覆盖temp中的值

        int rs=service.save(temp);
        return ResponseResult.ok(null);
        /*if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return checkVersion(temp);
        }*/
    }

    /**
     * 暂停任务
     */
    @RequestMapping(value="/stop/{id}")
    public ResponseResult stop(@PathVariable("id") Long id) throws OptimisticLockException {
        ScheduleJob scheduleJob=service.get(id);
        int rs=service.stopJob(scheduleJob);
        if(0==rs){
            return ResponseResult.failByBusiness("暂停任务失败");
        }else {
            return ResponseResult.ok("暂停成功!");
        }

    }


    /**
     * 立即运行一次
     */
    @RequestMapping("/startNow/{id}")
    public ResponseResult stratNow(@PathVariable("id") Long id) {
        ScheduleJob scheduleJob=service.get(id);
        int rs=service.startNowJob(scheduleJob);
        if(0==rs){
            return ResponseResult.failByBusiness("运行一次失败");
        }else {
            return ResponseResult.ok("运行一次成功!");
        }
    }

    /**
     * 恢复任务
     */
    @RequestMapping("/resume/{id}")
    public ResponseResult resume(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws OptimisticLockException {
        ScheduleJob scheduleJob=service.get(id);
        int rs=service.restartJob(scheduleJob);
        service.startNowJob(scheduleJob);//恢复之后，立即触发一次激活定时任务，不然定时任务有可能不会执行，这是bug的回避案，具体原因我没找到。
        if(0==rs){
            return ResponseResult.failByBusiness("恢复任务失败");
        }else {
            return ResponseResult.ok("恢复任务成功!");
        }
    }

    /**
     * 验证类任务类是否存在
     */
    @RequestMapping("/existsClass")
    public ResponseResult existsClass(String className) {
        Class job = null;
        try {
            job = Class.forName(className);
            return ResponseResult.ok(true);
        } catch (ClassNotFoundException e1) {
            return ResponseResult.ok(false);
        }
    }
}
