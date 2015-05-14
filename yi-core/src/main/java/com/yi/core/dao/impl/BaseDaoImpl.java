package com.yi.core.dao.impl;

import com.yi.core.dao.IBaseDao;
import com.yi.core.util.ReflectUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.*;
import java.util.*;

/**
 * Created by Cai on 2014/9/23 19:38.
 */
@Repository("baseDao")
public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK> {
    protected final static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
    protected Class persistClass;

    public BaseDaoImpl(){}

    public BaseDaoImpl(Class persistClass) {
        this.persistClass = persistClass;
    }

    @PersistenceContext
    private EntityManager entityManager;

    private Connection getConnection() {
        Session session = entityManager.unwrap(Session.class);

        try {
            return SessionFactoryUtils.getDataSource(session.getSessionFactory()).getConnection();
        } catch (SQLException e) {
            logger.error("获取connection异常：" + e);
        }

        return null;
    }

    @Override
    public T save(T t) {
        entityManager.persist(t);
        return t;
    }

    @Override
    public T merge(T t) {
        return entityManager.merge(t);
    }

    @Override
    public T get(PK pk) {
        //noinspection unchecked
        return (T) entityManager.find(persistClass, pk);
    }

    @Override
    public boolean delete(PK pk) {
        try {
            entityManager.remove(get(pk));
            entityManager.flush();
        } catch (Exception e) {
            logger.error("删除失败：" + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean batchDelete(Collection<PK> pks) {
        for (PK pk : pks) {
            if (!delete(pk)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(T t) {
        try {
            entityManager.remove(t);
        } catch (Exception e) {
            logger.error("删除失败：" + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean invalidate(PK pk) {
        String hql = "update " + persistClass.getName() + " a set a.discard=1 where a.id='" + pk + "'";
        Query query = entityManager.createQuery(hql);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean batchInvalidate(Collection<PK> pks) {
        for (PK pk : pks) {
            if (!invalidate(pk)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int updateByHql(String hql, Object[] params) {
        Query query = entityManager.createQuery(hql);
        Set<Parameter<?>> set = query.getParameters();
        for (Parameter<?> parameter : set) {
            int p = parameter.getPosition();
            query.setParameter(p, params[p]);
        }

        return query.executeUpdate();
    }

    @Override
    public long count() {
        String hql = "select count(*) from " + persistClass.getName()  + (ReflectUtil.findField(persistClass, "discard") == null ? "" : " a where a.discard=0");
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Override
    public long countByHql(String hql, Object[] objects) {
        Query query = entityManager.createQuery(hql);
        Set<Parameter<?>> set = query.getParameters();
        for (Parameter<?> parameter : set) {
            int p = parameter.getPosition();
            query.setParameter(p, objects[p]);
        }

        return (Long) query.getSingleResult();
    }

    @Override
    public List<T> findAll() {
        return findAll(-1, -1);
    }

    @Override
    public List<T> findAll(int position, int len) {
        String hql = "from " + persistClass.getName() + " a"
                + (ReflectUtil.findField(persistClass, "discard") == null ? "" : " where a.discard=0")
                + (ReflectUtil.findField(persistClass, "createdAt") == null ? "" : " order by a.updatedAt desc, a.createdAt desc");
        Query query = entityManager.createQuery(hql);
        if (position >= 0 && len > 0) {
            query.setFirstResult(position).setMaxResults(len);
        }

        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public List<T> findByHql(String hql, Object[] objects) {
        return findByHql(hql, objects, -1, -1);
    }

    @Override
    public List<T> findByHql(String hql, Object[] objects, int position, int len) {
        Query query = entityManager.createQuery(hql);
        Set<Parameter<?>> set = query.getParameters();
        for (Parameter<?> parameter : set) {
            int p = parameter.getPosition();
            query.setParameter(p, objects[p]);
        }

        if (position >= 0 && len > 0) {
            query.setFirstResult(position).setMaxResults(len);
        }

        //noinspection unchecked
        return query.getResultList();
    }

    @Override
    public void clear() {
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public List<Map<String, Object>> findBySql(String sql) {
        List<Map<String, Object>> result = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        Connection connection = getConnection();
        try {
            assert connection != null;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                for (int i = 1; i <= cols; i++) {
                    record.put(rsmd.getColumnLabel(i), (rs.getObject(i) == null ? "" : rs.getObject(i)));
                }
                result.add(record);
            }
        } catch (SQLException e) {
            logger.error("findBySql执行错误 -- " + sql + "\n错误详情 -- " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                logger.error("" + e);
            }
        }

        return result;
    }

    @Override
    public long countBySql(String sql) {
        long count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = getConnection();
        try {
            assert connection != null;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getLong(0);
            }
        } catch (SQLException e) {
            logger.error("countBySql执行错误 -- " + sql + "\n错误详情" + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                logger.error("" + e);
            }
        }

        return count;
    }

    @Override
    public int updateBySql(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }
}
