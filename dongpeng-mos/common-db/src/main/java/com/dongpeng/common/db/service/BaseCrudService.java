package com.dongpeng.common.db.service;

import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.DataEntity;
import com.dongpeng.common.entity.Page;
import io.shardingsphere.core.api.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 封装基础curd操作,实现BaseCrudDao
 * @param <D> dao interface
 * @param <T> entity
 */
@Service
@Transactional(readOnly = true)
public abstract class BaseCrudService<D extends BaseCrudDao<T>, T extends DataEntity<T>> extends BaseService<T> {
    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据,强制从主库获取数据
     *
     * @param id
     * @return
     */
    public T getFromMaster(Long id) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();//强制主库路由
        return dao.get(id);
    }


    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        if(entity.isDataScopeEnbale()){
            dataScopeFilter(entity);
        }
        return dao.findList(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, T entity) {
        if(entity.isDataScopeEnbale()){
            dataScopeFilter(entity);
        }
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public int save(T entity) throws OptimisticLockException {
        int rs;
        entity.setCurrentUser();
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            rs= dao.insert(entity);
        } else {
            entity.preUpdate();
            rs= dao.update(entity);
            if(0>=rs){
                throw new OptimisticLockException(this,entity);
            }
        }
        return rs;
    }

    /**
     * 批量插入数据
     * @param entitys 数据集合
     * @return
     */
    @Transactional(readOnly = false)
    public int insertBatch(Collection<T> entitys){
        for (T entity:entitys) {
            entity.setCurrentUser();
            entity.preInsert();
        }
        int rs=dao.insertBatch(entitys);
        return rs;
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    /*@Transactional(readOnly = false)
    public int delete(T entity) {
        int rs=dao.delete(entity);
        return rs;
    }*/


    /**
     * 删除全部数据
     *
     * @param entitys
     */
    /*@Transactional(readOnly = false)
    public void deleteAll(Collection<T> entitys) {
        for (T entity : entitys) {
            dao.delete(entity);
        }
    }*/

    /**
     * 逻辑删除/恢复，更新delete_flag字段为1/0,在表包含字段del_flag时，可以调用此方法，将数据隐藏/显示）
     *
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteToggle(T entity){
        return dao.deleteToggle(entity);
    }


    /**
     * 获取单条数据
     *
     * @param propertyName
     * @param value
     * @return
     */
    public T findUniqueByProperty(String propertyName, Object value) {
        return dao.findUniqueByProperty(propertyName, value);
    }

    /**
     * 当前数据版本号是否与数据库一致
     * @param entity
     * @return
     */
    public boolean compareVersion(T entity){
        T fromDataBase=get(entity.getId());
        entity.setModifierName(fromDataBase.getModifierName());//将数据库中的修改者名称赋值到entity中，以供后续显示
        return fromDataBase.getVersion()==entity.getVersion();
    }


    /**
     * 执行自定义查询sql
     * @param sql
     */
    public List<T> executeSelectSql(String sql){
        return dao.execSelectSql(sql);
    }

    /**
     * 执行自定义插入sql
     * @param sql
     */
    @Transactional(readOnly = false)
    public int executeInsertSql(String sql){
        return dao.execInsertSql(sql);
    }

    /**
     * 执行自定义更新sql
     * @param sql
     */
    @Transactional(readOnly = false)
    public int executeUpdateSql(String sql){
        return dao.execUpdateSql(sql);
    }

    /**
     * 执行自定义删除sql
     * @param sql
     */
    @Transactional(readOnly = false)
    public int executeDeleteSql(String sql){
        return dao.execDeleteSql(sql);
    }

    /**
     * 移除当前缓存
     * @param rs 操作成功数
     */
    protected void removeCurrentCache(int rs){
        if(0<rs){
            //System.out.println(ReflectionsUtils.getClassGenricType(getClass()).getName());
            //System.out.println(J2CacheUtils.getKeysByRegion(ReflectionsUtils.getClassGenricType(getClass()).getName()).size());
            //J2CacheUtils.clear(ReflectionsUtils.getClassGenricType(getClass()).getName());
            //System.out.println(J2CacheUtils.getKeysByRegion(ReflectionsUtils.getClassGenricType(getClass()).getName()).size());

        }
    }
}
