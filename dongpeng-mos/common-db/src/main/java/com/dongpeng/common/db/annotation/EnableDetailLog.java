package com.dongpeng.common.db.annotation;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.parser.BaseContentParse;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录编辑详细信息的注解
 * <p>使用说明：
 * <ul>
 *     <li>在需要记录的controller方法上使用注解EnableDetailLog</li>
 *     <li>约定controller方法第一个参数就是data entity</li>
 *     <li>约定controller方法返回值是{@link com.dongpeng.common.entity.ResponseResult}</li>
 * </ul>
 * </p>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EnableDetailLog {

    /**
     * 操作的中文说明 可以直接调用Global.MODIFY_XXX
     * <p>默认 Global.MODIFY_UPDATE</p>
     * @return
     */
    String name() default Global.MODIFY_UPDATE;

    /**
     * 获取编辑信息的解析类，目前为使用id获取，默认不填写
     * 则使用默认解析类
     * @return
     */
    Class parseclass() default BaseContentParse.class;

    /**
     * 查询数据库所调用的service class
     * @return
     */
    Class serviceclass() default BaseCrudService.class;

    /**
     * 查询字段名称
     */
    String[] feildName() default {"id"};
    /**
     * 具体业务操作名称
     */
    String handleName() default "";

}
