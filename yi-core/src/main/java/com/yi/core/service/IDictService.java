package com.yi.core.service;

import com.yi.core.model.DictModel;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Cai on 2014/11/27 15:37.
 */
@Transactional
public interface IDictService extends IBaseService<DictModel, String> {
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    List<Map<String,Object>> findTreeEntities();

    /**
     * 验证编号是否已存在
     * @param code      编号
     * @param parentId  父节点ID
     * @param id        实体ID
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    boolean checkCode(String code, String parentId, String id);

    /**
     * 通过父节点code查询
     * @param parentCode
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    List<DictModel> findByParentCode(String parentCode);

    boolean batchUpdateParent(List<DictModel> children, DictModel parent, String moveType);
}
