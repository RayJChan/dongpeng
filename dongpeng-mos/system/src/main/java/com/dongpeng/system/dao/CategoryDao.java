package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Breed;
import com.dongpeng.entity.system.Category;

import java.util.List;

@MyBatisDao
public interface CategoryDao extends BaseCrudDao<Category> {

}
