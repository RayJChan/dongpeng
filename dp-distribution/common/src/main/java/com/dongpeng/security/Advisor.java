package com.dongpeng.security;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class Advisor extends StaticMethodMatcherPointcutAdvisor {

    private Advice advice=new com.dongpeng.security.Advice();
    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return classMatches(aClass)||methodMatches(method);
    }

    private  boolean classMatches(Class<?> aClass){
        RequiresPermissions permissions = aClass.getAnnotation(RequiresPermissions.class);
        RequiresRoles roles = aClass.getAnnotation(RequiresRoles.class);
        return permissions!=null||roles!=null;
    }

    private boolean methodMatches(Method method){
        RequiresPermissions permissions = method.getAnnotation(RequiresPermissions.class);
        RequiresRoles roles = method.getAnnotation(RequiresRoles.class);
        return permissions!=null||roles!=null;
    }
}
