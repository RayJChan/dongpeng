package com.dongpeng.common.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;
/**
 * 使用Cglib的BeanCopier实现Bean的拷贝
 * <p>其性能要比Spring的BeanUtils，Apache的BeanUtils和PropertyUtils要好很多，尤其是数据量比较大的情况下</p>
 * <p>BeanCopier的性能是PropertyUtils (apache-common)的504倍。</p>
 * <p>PropertyUtils的性能是BeanUtils(apache-common)的1.71倍</p>
 * <p><strong>使用BeanCopier，bean的setter 和 getter 必须严格匹配!!</strong></p>
 */
public class CglibBeanCopierUtils {

    /**
     * 用map存储各种bean的BeanCopier，因为构建一次BeanCopier会消耗比较大的性能
     */
    public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    /**
     * 根据目标类型，源类型生成唯一key
     * @param class1 来源bean class
     * @param class2 目标bean class
     * @return
     */
    private static String generateKey(Class<?>class1,Class<?>class2){
        return class1.toString() + class2.toString();
    }

    /**
     * 对象属性拷贝
     * @param source 来源bean
     * @param target 目标bean
     */
    public static void copyProperties(Object source,Object target){
        String beanKey = generateKey(source.getClass(),target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        }else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }
}
