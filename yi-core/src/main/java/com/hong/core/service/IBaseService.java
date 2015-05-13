package com.hong.core.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cai on 2014/9/25 9:26.
 */
@Transactional
public interface IBaseService<T, PK extends Serializable> {
    public T save(T t);

    public T merge(T t);

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public T get(PK pk);

    public boolean delete(PK pk);

    public boolean delete(T t);

    public boolean batchDelete(String ids);

    public boolean remove(PK pk);

    public boolean batchRemove(String ids);

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public long count();

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<T> findAll();

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<T> findAll(int currentPage, int len);

    /**
     * 将persistence context中所有的托管实体都变成游离对象
     */
    public void clear();
}
