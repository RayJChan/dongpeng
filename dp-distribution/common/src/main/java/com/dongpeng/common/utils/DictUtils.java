package com.dongpeng.common.utils;

import com.dongpeng.common.config.Global;
import com.dongpeng.entity.system.Dictionary;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * 系统字典项 工具类
 */
public class DictUtils {

    /**
     * 根据字典项名称获取字典列表
     * @param dictionaryName 字典项名称
     * @return 字典List
     */
    public static List<Dictionary> getByDictionaryName(String dictionaryName){
        Map<Long, Dictionary> dictMap= (Map<Long, Dictionary>) J2CacheUtils.get(Global.DICT_REGION, dictionaryName);
        return Lists.newArrayList(dictMap.values());
    }

    /**
     * 根据code获取一个字典
     * @param code 字典code
     * @return
     */
    public static Dictionary getByCode(String code){
        Dictionary dictionary=null;
        List<String> keys=J2CacheUtils.getKeysByRegion(Global.DICT_REGION);
        if(null!=keys){
            loop :for (String key:keys) {
                Map<String, Dictionary> dictMap= (Map<String, Dictionary>) J2CacheUtils.get(Global.DICT_REGION,key);
                for (String codeKey : dictMap.keySet()) {
                    if(code.equals(codeKey)){
                        dictionary=dictMap.get(code);
                        break loop;//跳出所有循环
                    }
                }
            }
        }
        return dictionary;
    }

    /**
     * 根据id获取一个字典
     * @param id 字典id
     * @return
     */
    public static Dictionary getById(Long id){
        Dictionary dictionary=null;
        List<String> keys=J2CacheUtils.getKeysByRegion(Global.DICT_REGION);
        if(null!=keys){
            loop :for (String key:keys) {
                Map<String, Dictionary> dictMap= (Map<String, Dictionary>) J2CacheUtils.get(Global.DICT_REGION,key);
                for (Dictionary dict : dictMap.values()) {
                    if(id.longValue()==dict.getId().longValue()){
                        dictionary=dict;
                        break loop;//跳出所有循环
                    }
                }
            }
        }
        return dictionary;
    }
}
