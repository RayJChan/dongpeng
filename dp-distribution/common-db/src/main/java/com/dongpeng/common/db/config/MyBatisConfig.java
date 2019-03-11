package com.dongpeng.common.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dongpeng.common.db.interceptor.PaginationInterceptor;
import com.dongpeng.common.entity.Page;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 单个数据库mybatis配置
 */
/*@RefreshScope
@Configuration
@Order(2) //数字越小加载优先级越高
@EnableTransactionManagement*/
public class MyBatisConfig implements TransactionManagementConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Value("${db.jdbc.type}")
    private String dbType;
    @Value("${db.jdbc.driverClass}")
    private String driverClass;
    @Value("${db.jdbc.url}")
    private String url;
    @Value("${db.jdbc.username}")
    private String username;
    @Value("${db.jdbc.password}")
    private String password;
    @Value("${db.jdbc.pool.initialSize}")
    private Integer initialSize;
    @Value("${db.jdbc.pool.maxActive}")
    private Integer maxActive;
    @Value("${db.jdbc.pool.minIdle}")
    private Integer minIdle;
    @Value("${db.jdbc.pool.maxWait}")
    private Integer maxWait;
    @Value("${db.jdbc.pool.validationQuery}")
    private String validationQuery;
    @Value("${db.jdbc.pool.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${db.jdbc.pool.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${db.jdbc.pool.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${db.jdbc.pool.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${db.jdbc.pool.filters}")
    private String filters;
    @Value("${db.jdbc.pool.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${db.jdbc.pool.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${db.jdbc.pool.typeAliasesPackage}")
    private String typeAliasesPackage;
    @Value("${db.jdbc.pool.typeAliasesSuperType}")
    private String typeAliasesSuperType;
    @Value("${db.jdbc.pool.mapperLocations}")
    private String mapperLocations;


    @Bean("dataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        logger.info("password="+password);
        //logger.info("env dbType="+env.getProperty("db.jdbc.type"));
        dataSource.setDbType(dbType);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);

        //下边仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        ssfb.setTypeAliasesPackage(typeAliasesPackage);
        ssfb.setTypeAliasesSuperType(Class.forName(typeAliasesSuperType));
        ssfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));

        //设置分页插件配置
        ssfb.setTypeAliases(new Class[]{Page.class});
        ssfb.setPlugins(new Interceptor[]{new PaginationInterceptor()});

        //设置全局参数
        SqlSessionFactory sqlSessionFactory = ssfb.getObject();
        sqlSessionFactory.getConfiguration().setCacheEnabled(true);//使全局的映射器启用缓存
        sqlSessionFactory.getConfiguration().setLazyLoadingEnabled(true);//全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。
        sqlSessionFactory.getConfiguration().setAggressiveLazyLoading(true);//当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。
        sqlSessionFactory.getConfiguration().setMultipleResultSetsEnabled(true);//是否允许单条sql 返回多个数据集  (取决于驱动的兼容性) default:true
        sqlSessionFactory.getConfiguration().setUseColumnLabel(true);//否可以使用列的别名 (取决于驱动的兼容性) default:true
        sqlSessionFactory.getConfiguration().setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);//指定 MyBatis 如何自动映射 数据基表的列 NONE：不隐射　PARTIAL:部分  FULL:全部
        sqlSessionFactory.getConfiguration().setDefaultExecutorType(ExecutorType.SIMPLE);//这是默认的执行类型  （SIMPLE: 简单； REUSE: 执行器可能重复使用prepared statements语句；BATCH: 执行器可以重复执行语句和批量更新）
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);//使用驼峰命名法转换字段


        return sqlSessionFactory;
    }

    /**
     * 配置 Annotation 驱动，扫描@Transactional注解的类定义事务
     * @return
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager=new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());

        return dataSourceTransactionManager;
    }

    /*@Bean("mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer msc=new MapperScannerConfigurer();
        msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
        msc.setBasePackage("com.demo");
        return msc;
    }*/
}
