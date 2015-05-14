package com.yi.core.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cai on 2014/11/27 14:26.
 *
 * 字典表
 */
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity(name = "orochi_core_dict")
public class DictModel extends BaseModel {
    @Column(nullable = false, length = 36)
    private String code;
    @Column(nullable = false, length = 36)
    private String name;
    private int sequence;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JSONField(serialize = false)
    private DictModel parent;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER, mappedBy = "parent")
    @OrderBy("sequence asc, version desc")
    private List<DictModel> children;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public DictModel getParent() {
        return parent;
    }

    public void setParent(DictModel parent) {
        this.parent = parent;
    }

    public List<DictModel> getChildren() {
        return children;
    }

    public void setChildren(List<DictModel> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
