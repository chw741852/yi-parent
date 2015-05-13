package com.hong.core.service.impl;

import com.hong.core.dao.IBaseDao;
import com.hong.core.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Cai on 2014/9/25 10:00.
 */
@Service("baseService")
public class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
    protected final static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private IBaseDao<T, PK> baseDao;

    @Override
    public T save(T t) {
        return baseDao.save(t);
    }

    @Override
    public T merge(T t) {
        return baseDao.merge(t);
    }

    @Override
    public T get(PK pk) {
        return baseDao.get(pk);
    }

    @Override
    public boolean delete(PK pk) {
        return baseDao.delete(pk);
    }

    @Override
    public boolean delete(T t) {
        return baseDao.delete(t);
    }

    @Override
    public boolean batchDelete(String ids) {
        Collection<PK> pks = new ArrayList<>();
        for (String id : ids.split(",")) {
            if (!StringUtils.isEmpty(id)) {
                pks.add((PK) id);
            }
        }
        return baseDao.batchDelete(pks);
    }

    @Override
    public boolean remove(PK pk) {
        return baseDao.invalidate(pk);
    }

    @Override
    public boolean batchRemove(String ids) {
        Collection<PK> pks = new ArrayList<>();
        for (String id : ids.split(",")) {
            if (!StringUtils.isEmpty(id)) {
                pks.add((PK) id);
            }
        }

        return baseDao.batchInvalidate(pks);
    }

    @Override
    public long count() {
        return baseDao.count();
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> findAll(int currentPage, int len) {
        int position = (currentPage - 1) * len;
        return baseDao.findAll(position, len);
    }

    @Override
    public void clear() {
        baseDao.clear();
    }
}
