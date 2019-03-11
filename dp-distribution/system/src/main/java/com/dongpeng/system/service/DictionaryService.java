package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.system.dao.DictionaryDao;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DictionaryService extends BaseCrudService<DictionaryDao,Dictionary> {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    @Override
    @Transactional(readOnly = false)
    public int save(Dictionary dictionary) throws OptimisticLockException {
        int rs=super.save(dictionary);

        //更新缓存
        Map<String, Dictionary> dictMap = (Map<String, Dictionary>) J2CacheUtils.get(Global.DICT_REGION, dictionary.getDictionaryName());
        if (dictMap != null){
            dictMap.put(dictionary.getDetailCode(), dictionary);
        }else{
            dictMap=Maps.newHashMap();
            dictMap.put(dictionary.getDetailCode(), dictionary );
            J2CacheUtils.put(Global.DICT_REGION, dictionary.getDictionaryName(), dictMap);
        }
        return rs;
    }

    /*@Override
    @Transactional(readOnly = false)
    public int delete(Dictionary dictionary) {
        int rs=super.delete(dictionary);

        //从缓存中删除
        Map<Long, Dictionary> dictMap = (Map<Long, Dictionary>) J2CacheUtils.get(Global.DICT_REGION, dictionary.getDictionaryName());
        if (dictMap != null){
            dictMap.remove(dictionary.getId());
        }
        return rs;
    }*/

    /**
     * 加载所有字典，并写入缓存
     */
    @Transactional(readOnly = false)
    public void initDictCache(){
        //List<String> keys=J2CacheUtils.getKeysByRegion(Global.DICT_REGION);
        //if (keys==null || keys.isEmpty()){
            //从数据库加载所有字典
            List<Dictionary> dictionaryList;
            try {
                dictionaryList=findList(new Dictionary());
            }catch (Exception e){
                logger.error("dictionaryList findList error:",e);
                throw e;
            }

            //遍历字典项，按dictionaryName为key
            for (Dictionary dict: dictionaryList) {
                Map<String, Dictionary> dictMap = (Map<String, Dictionary>) J2CacheUtils.get(Global.DICT_REGION, dict.getDictionaryName());
                if (dictMap != null){
                    dictMap.put(dict.getDetailCode(), dict);
                }else{
                    dictMap=Maps.newHashMap();
                    dictMap.put(dict.getDetailCode(), dict );
                    J2CacheUtils.put(Global.DICT_REGION, dict.getDictionaryName(), dictMap);
                }
            }

        //}
    }

    /**
     * 根据code获得字典
     * @param code 字典code
     * @return
     */
    public Dictionary getByCode(String code){
        return dao.getByCode(code);
    }

    @Override
    public String createDataScopeSql(Dictionary entity) {
        return null;
    }

    /**
     * 根据名称和明细获得字典
     * @param dictionaryName 名称
     * @param detailName 明细
     * @return
     */
    public Dictionary getByNameAndDetail(String dictionaryName,String detailName){
        return dao.getByNameAndDetail(dictionaryName, detailName);
    }
}
