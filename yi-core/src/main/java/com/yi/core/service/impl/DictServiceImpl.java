package com.yi.core.service.impl;

import com.yi.core.dao.IDictDao;
import com.yi.core.model.DictModel;
import com.yi.core.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Cai on 2014/11/27 15:38.
 */
@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<DictModel, String> implements IDictService {
    @Autowired
    private IDictDao dictDao;

    @Override
    public List<Map<String, Object>> findTreeEntities() {
        String sql = "select id, parent_id as pId, name from orochi_core_dict where discard=0 order by sequence asc, version desc";
        return dictDao.findBySql(sql);
    }

    @Override
    public boolean checkCode(String code, String parentId, String id) {
        DictModel parent = null;
        if (!StringUtils.isEmpty(parentId)) {
            parent = new DictModel();
            parent.setId(parentId);
        }
        DictModel dictModel = dictDao.findByCodeAndParent(code, parent);
        if (dictModel != null && !dictModel.getId().equals(id)) {
            return false;
        }

        return true;
    }

    @Override
    public List<DictModel> findByParentCode(String parentCode) {
        return dictDao.findByParentCode(parentCode);
    }

    @Override
    public boolean batchUpdateParent(List<DictModel> children, DictModel parent, String moveType) {
        if (moveType.equals("inner")) {
            for (DictModel child : children) {
                child.setParent(parent);
                if (dictDao.merge(child) == null) return false;
            }
        } else {
            for (DictModel child : children) {
                if (child.getParent() != parent.getParent()) {
                    child.setParent(parent.getParent());
                    if (dictDao.merge(child) == null) return false;
                }
            }
        }
        return true;
    }

    @Override
    public DictModel merge(DictModel dict) {
        if (dict.getParent() != null && !StringUtils.isEmpty(dict.getParent().getId())) {
            dict.getParent().setVersion(0);
        } else {
            dict.setParent(null);
        }
        if (StringUtils.isEmpty(dict.getId())) {
            // 设置序号
            DictModel maxSequenceDict = dictDao.findMaxSequenceByParent(dict.getParent());
            if (maxSequenceDict != null) {
                dict.setSequence(maxSequenceDict.getSequence() + 1);
            }
        }
        dictDao.merge(dict);

        return dict;
    }
}
