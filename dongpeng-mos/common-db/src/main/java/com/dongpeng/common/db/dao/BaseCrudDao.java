package com.dongpeng.common.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 */
public interface BaseCrudDao<T> {
    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id);

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 根据实体名称和字段名称和字段值获取唯一记录
     *
     * @param propertyName
     * @param value
     * @return
     */
    public T findUniqueByProperty(@Param(value = "propertyName") String propertyName, @Param(value = "value") Object value);


    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 查询所有数据列表
     *
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 批量插入数据
     * @param entitys
     * @return
     */
    public int insertBatch(@Param(value = "entitys")Collection<T> entitys);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    public int update(T entity);


    /**
     * 删除数据（物理删除，从数据库中彻底删除）
     *
     * @param entity
     * @return
     */
    //public int delete(T entity);

    /**
     * 逻辑删除/恢复，更新delete_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏/显示）
     *
     * @param entity
     * @return
     */
    public int deleteToggle(T entity);


    //################# 动态自定义sql ################
    /**
     * 执行自定义查询sql
     * @param sql
     */
    @Select("${sql}")
    public List<T> execSelectSql(@Param(value="sql")String sql);

    /**
     * 执行自定义插入sql
     * @param sql
     */
    @Update("${sql}")
    public int execUpdateSql(@Param(value="sql")String sql);

    /**
     * 执行自定义更新sql
     * @param sql
     */
    @Insert("${sql}")
    public int execInsertSql(@Param(value="sql")String sql);

    /**
     * 执行自定义删除sql
     * @param sql
     */
    @Delete("${sql}")
    public int execDeleteSql(@Param(value="sql")String sql);
}
