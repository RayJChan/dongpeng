package com.dongpeng.common.db.config;

import com.dongpeng.common.db.annotation.MyBatisDao;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


//@AutoConfigureAfter(name = "sharding")
//@AutoConfigureAfter(MyBatisConfig.class)
@Configuration
public class MyBatisMapperScannerConfig{

    /*public MyBatisMapperScannerConfig(){
        System.out.println(basePackage);
        System.out.println(env.getProperty("db.jdbc.mapperScannerConfigurer.basePackage"));
        this.setSqlSessionFactoryBeanName("sqlSessionFactory");
        this.setBasePackage(basePackage);
        this.setAnnotationClass(MyBatisDao.class);
    }*/

    /**
     * - 设置SqlSessionFactory；
     * - 设置dao所在的package路径；
     * - 关联注解在dao类上的Annotation名字；
     */
    @Bean("mapperScannerConfigurer")
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.dongpeng.**.dao");
        mapperScannerConfigurer.setAnnotationClass(MyBatisDao.class);
        return mapperScannerConfigurer;
    }
}
