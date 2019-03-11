package com.dongpeng.common.db.interceptor;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.dialect.Dialect;
import com.dongpeng.common.db.dialect.MySQLDialect;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.utils.ReflectionsUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;
import java.util.Map;

/**
 * Mybatis分页拦截器基类
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {

    private static final long serialVersionUID = 1L;

    protected static final String PAGE = "page";

    protected static final String DELEGATE = "delegate";

    protected static final String MAPPED_STATEMENT = "mappedStatement";

    protected Log log = LogFactory.getLog(this.getClass());

    protected Dialect DIALECT;


    /**
     * 对参数进行转换和检查
     * @param parameterObject 参数对象
     * @param page            分页对象
     * @return 分页对象
     * @throws NoSuchFieldException 无法找到参数
     */
    @SuppressWarnings("unchecked")
    protected static Page<Object> convertParameter(Object parameterObject, Page<Object> page) {
        try{
            if (parameterObject instanceof Page) {
                return (Page<Object>) parameterObject;
            }else if(parameterObject instanceof Map ){
                return (Page<Object>) ((Map)parameterObject).get(PAGE);
            } else {
                return (Page<Object>)ReflectionsUtils.getFieldValue(parameterObject, PAGE);
            }
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置属性，支持自定义方言类和制定数据库的方式

     */
    protected void initProperties() {
        Dialect dialect = null;
        String dbType = Global.getConfig("db.jdbc.type");

        if("mysql".equals(dbType)){
            dialect = new MySQLDialect();
        }

        if (dialect == null) {
            throw new RuntimeException("mybatis dialect error.");
        }
        DIALECT = dialect;
    }
}
