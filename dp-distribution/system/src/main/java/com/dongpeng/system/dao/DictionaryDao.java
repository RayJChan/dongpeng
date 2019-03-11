package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Dictionary;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface DictionaryDao extends BaseCrudDao<Dictionary> {
    /**
     * 根据code获得字典
     * @param code 字典code
     * @return
     */
    public Dictionary getByCode(String code);

    /**
     * 根据名称和明细获得字典
     * @param dictionaryName 名称
     * @param detailName 明细
     * @return
     */
    public Dictionary getByNameAndDetail(@Param("dictionaryName") String dictionaryName,@Param("detailName") String detailName);
}
