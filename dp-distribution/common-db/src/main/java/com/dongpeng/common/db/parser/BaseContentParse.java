package com.dongpeng.common.db.parser;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.service.BaseCrudService;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.springframework.util.Assert;

import java.util.Map;
/**
 * 基础解析类
 * <p>单表编辑时可以直接使用id来查询</p>
 */
public class BaseContentParse implements IContentParser {

    @Override
    public Object getResult(Map<String, Object> feildValues, EnableDetailLog enableDetailLog) {
        Assert.isTrue(feildValues.containsKey("id"),"未解析到id值");
        Object result= feildValues.get("id");
        Long id=0L;
        if(result instanceof Long){
            id= (Long) result;
        }
        BaseCrudService service= null;
        Class cls=enableDetailLog.serviceclass();
        service = (BaseCrudService) SpringUtil.getBean(cls);

        if(Boolean.valueOf(Global.getConfig(Global.DB_MASTER_SLAVE_ENABLE))){
            return  service.getFromMaster(id);
        }else{
            return service.get(id);
        }
    }
}
