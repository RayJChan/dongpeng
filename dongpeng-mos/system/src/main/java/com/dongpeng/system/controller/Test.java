package com.dongpeng.system.controller;

import com.dongpeng.entity.system.Company;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        Class clz = Company.class;
        //获取方法
        Method m  = clz.getDeclaredMethod("setCompanyCode",String.class);
        Object obj = clz.newInstance();

        m.invoke(obj,"222");
        Method get  = clz.getMethod("getCompanyCode");
        System.out.println(get.invoke(obj));


        //无参构造器
        Constructor con = clz.getConstructor(null);


    }


}
