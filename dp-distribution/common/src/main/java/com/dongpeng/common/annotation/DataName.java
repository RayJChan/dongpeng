package com.dongpeng.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需日志记录操作字段中文的请在entity中使用DataName注解
 * <p>日志详细记录，只有使用该注解的字段才会记录 更新前/更新后 的值</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DataName {

    /**
     * 字段名称
     * @return
     */
    @AliasFor("name")
    String value() default "";
}
