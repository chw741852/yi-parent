package com.hong.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Cai on 2014/9/23 17:51.
 */
public interface IBaseDao<T, PK extends Serializable> {
    T save(T t);

    /**
     * merge后处于托管状态
     * @param t
     * @return
     */
    T merge(T t);

    T get(PK pk);

    boolean delete(PK pk);

    boolean batchDelete(Collection<PK> pks);

    boolean delete(T t);

    /**
     * 逻辑删除
     * @param pk
     * @return
     */
    boolean invalidate(PK pk);

    boolean batchInvalidate(Collection<PK> pks);

    int updateByHql(String hql, Object[] params);

    long count();

    long countByHql(String hql, Object[] objects);

    List<T> findAll();

    List<T> findAll(int position, int len);

    List<T> findByHql(String hql, Object[] objects);

    List<T> findByHql(String hql, Object[] objects, int position, int len);

    void clear();

    List<Map<String, Object>> findBySql(String sql);

    long countBySql(String sql);

    int updateBySql(String sql);
}
