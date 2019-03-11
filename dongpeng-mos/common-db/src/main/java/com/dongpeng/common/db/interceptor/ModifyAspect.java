package com.dongpeng.common.db.interceptor;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.parser.IContentParser;
import com.dongpeng.common.db.service.OperatingRecordService;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.CglibBeanCopierUtils;
import com.dongpeng.common.utils.ReflectionsUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.dongpeng.entity.system.OperatingRecord;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring aop 切面 拦截@EnableDetailLog注解的方法,将具体修改存储到数据库中
 */
@Aspect
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConditionalOnBean(OperatingRecordService.class)
public class ModifyAspect {
    private final static Logger logger = LoggerFactory.getLogger(ModifyAspect.class);

    private Object oldObject;
    private Object newObject;
    private Map<String,Object> feildValues;
    private OperatingRecord operatingRecord=new OperatingRecord();

    @Autowired
    private OperatingRecordService operatingRecordService;

    /**
     * 在目标方法的执行之前执行，即在连接点之前进行执行
     * @param joinPoint
     * @param enableDetailLog
     */
    @Before("@annotation(enableDetailLog)")
    public void doBefore(JoinPoint joinPoint, EnableDetailLog enableDetailLog){
        //暂只对 Global.MODIFY_UPDATE 的进行记录
        if(Global.MODIFY_UPDATE.equals(enableDetailLog.name())){
            //获取EnableDetailLog注解的方法的第一个参数，约定第一个参数就是data entity
            Object info=joinPoint.getArgs()[0];
            //获取EnableDetailLog注解的属性名称，默认只有一个 id
            String[] feilds=enableDetailLog.feildName();

            //设置当前操作用户信息
            operatingRecord.setCurrentUser();

            for(String feild:feilds){
                feildValues=new HashMap<>();
                Object result= ReflectionsUtils.getFieldValue(info,feild);//根据enableDetailLog注解注入的参数，查询对应参数值
                feildValues.put(feild,result);
            }

            //设置具体操作名称/描述
            if(StringUtils.isBlank(enableDetailLog.handleName())){
                operatingRecord.setHandledescribe(UserAgentUtils.getCurrentRequest().getRequestURL().toString());
            }else {
                operatingRecord.setHandledescribe(enableDetailLog.handleName()+",id="+feildValues.get("id"));
            }

            try {
                IContentParser contentParser= (IContentParser) enableDetailLog.parseclass().newInstance();//实例化默认的解析类

                //此处不能直接把结果赋值到oldObject中，因为会从缓存中获取到对象，该对象有可能在业务处理中被修改值，这样连带oldObject的值都会修改,那样就无法比对真正改变的值了
                Object objectTemp=contentParser.getResult(feildValues,enableDetailLog);//调用解析类,根据feildValues加载data entity 更新前数据
                if(null!=objectTemp){
                    oldObject=objectTemp.getClass().newInstance();
                    CglibBeanCopierUtils.copyProperties(objectTemp, oldObject);
                    newObject=info;
                }
            } catch (Exception e) {
                logger.error("service加载失败:",e);
            }
        }
    }


    /**
     * 当连接点方法成功执行后，返回通知方法才会执行，如果连接点方法出现异常，则返回通知方法不执行
     * @param point
     * @param result
     * @param enableDetailLog
     */
    @AfterReturning(pointcut = "@annotation(enableDetailLog)",returning = "result")
    public void doAfterReturing(JoinPoint point,Object result, EnableDetailLog enableDetailLog){
        ResponseResult rs= (ResponseResult) result;
        //只有返回成功才执行记录
        if(null!=rs && ResponseCode.SUCCESS.getCode()== rs.getCode()){
            if(Global.MODIFY_UPDATE.equals(enableDetailLog.name())){
                /*IContentParser contentParser= null;
                try {
                    contentParser = (IContentParser) enableDetailLog.parseclass().newInstance();
                    newObject=contentParser.getResult(feildValues,enableDetailLog);//调用解析类,根据feildValues加载data entity 更新后数据
                } catch (Exception e) {
                    logger.error("service加载失败:",e);
                }*/

                try {
                    List<Map<String ,Object>> changelist= ReflectionsUtils.compareTwoClass(oldObject,newObject);
                    StringBuilder str=new StringBuilder();
                    for(Map<String,Object> map:changelist){
                        str.append(map.get("name")+": "+map.get("old")+"->"+map.get("new")+" | ");
                    }
                    operatingRecord.setHandlerecord(str.toString());
                    logger.info("改变的值："+operatingRecord.getHandlerecord());
                    operatingRecordService.save(operatingRecord);//保存到数据库
                    operatingRecord=new OperatingRecord();//此处必须重新实例化，不然下次进入切面会变成update日志的操作
                } catch (Exception e) {
                    logger.error("对象同名属性比较异常",e);
                }
            }

        }



    }

}
