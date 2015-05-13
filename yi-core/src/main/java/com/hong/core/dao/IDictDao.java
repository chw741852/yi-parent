package com.hong.core.dao;

import com.hong.core.model.DictModel;

import java.util.List;

/**
 * Created by Cai on 2014/11/27 15:26.
 */
public interface IDictDao extends IBaseDao<DictModel, String> {
    DictModel findByCodeAndParent(String code, DictModel parent);

    /**
     * 查询父节点下，最大的序号实体
     * @param parent
     */
    DictModel findMaxSequenceByParent(DictModel parent);

    /**
     * 通过父节点code查询
     * @param parentCode
     * @return
     */
    List<DictModel> findByParentCode(String parentCode);
}
