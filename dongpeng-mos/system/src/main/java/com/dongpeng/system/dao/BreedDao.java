package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Breed;

import java.util.List;

@MyBatisDao
public interface BreedDao extends BaseCrudDao<Breed> {
    /**
     * 根据上级id获得品类
     * @param upId 上级id
     * @return
     */
    public List<Breed> getByUp(Long upId);

    /**
     * 根据根据品类编码获得品类
     * @param breedCode 品类编码
     * @return
     */
    public Breed getByBreedCode(String breedCode);
}
