package com.dongpeng.common.db.parser;

import com.dongpeng.common.db.annotation.EnableDetailLog;

import java.util.Map;

/**
 * 解析接口
 */
public interface IContentParser {

    /**
     * 获取信息返回查询出的对象
     * @param feildValues 查询条件的参数值
     * @param enableDetailLog 注解
     * @return
     */
    public Object getResult(Map<String, Object> feildValues, EnableDetailLog enableDetailLog);
}
