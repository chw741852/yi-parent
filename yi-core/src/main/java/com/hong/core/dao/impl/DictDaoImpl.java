package com.hong.core.dao.impl;

import com.hong.core.dao.IDictDao;
import com.hong.core.model.DictModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cai on 2014/11/27 15:34.
 */
@Repository("dictDao")
public class DictDaoImpl extends BaseDaoImpl<DictModel, String> implements IDictDao {
    public DictDaoImpl() {
        super(DictModel.class);
    }

    @Override
    public DictModel findByCodeAndParent(String code, DictModel parent) {
        StringBuffer hql = new StringBuffer();
        hql.append("from " + DictModel.class.getName() + " a where a.discard=0 and a.code='" + code + "'");
        if (parent == null) {
            hql.append(" and a.parent=null");
        } else {
            hql.append(" and a.parent.id='" + parent.getId() + "'");
        }

        List<DictModel> dictList = super.findByHql(hql.toString(), null);
        if (dictList.size() > 0) {
            return dictList.get(0);
        }

        return null;
    }

    @Override
    public DictModel findMaxSequenceByParent(DictModel parent) {
        StringBuffer hql = new StringBuffer("from " + DictModel.class.getName() + " a where a.discard=0");
        hql.append(" and a.sequence=(select max(a.sequence) from " + DictModel.class.getName() + " a where a.discard=0");
        if (parent == null) {
            hql.append(" and a.parent=null) and a.parent=null");
        } else {
            hql.append(" and a.parent.id='" + parent.getId() + "') and a.parent.id='" + parent.getId() + "'");
        }
        List<DictModel> dictList = super.findByHql(hql.toString(), null);
        if (dictList.size() > 0) {
            return dictList.get(0);
        }

        return null;
    }

    @Override
    public List<DictModel> findByParentCode(String parentCode) {
        String hql = "from " + DictModel.class.getName() + " a where a.parent.code=?0";
        Object[] params = {parentCode};
        return super.findByHql(hql.toString(), params);
    }
}
