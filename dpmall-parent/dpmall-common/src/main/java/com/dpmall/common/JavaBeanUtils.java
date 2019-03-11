package com.dpmall.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import net.sf.cglib.beans.BeanMap;

public class JavaBeanUtils {

    /**
     * 将map装换为javabean对象
     * @param map
     * @param bean
     * @return
     */
    public static  <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将Map转化为Object
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass){
        if (map == null)
            return null;
        Object obj = null;
        try {
            obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
