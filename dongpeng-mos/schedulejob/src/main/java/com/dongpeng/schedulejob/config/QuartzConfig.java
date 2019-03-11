package com.dongpeng.schedulejob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class QuartzConfig {
    //@Autowired
    //private DataSource dataSource;

    @Value("${quartz.location}")
    private String location;

    /**
     * 声明任务工厂
     * @param dataSource 来自common-db模块的数据源
     * @return
     * @throws IOException
     */
    @Bean("schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,TaskSchedulerFactory schedulerFactory) throws IOException {
        SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(schedulerFactory);
        schedulerFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(location));
        return schedulerFactoryBean;
    }
}
