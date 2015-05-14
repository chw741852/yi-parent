package com.yi.admin.dao.demo.impl;

import com.yi.admin.dao.demo.IDemoDao;
import com.yi.admin.model.demo.DemoModel;
import com.yi.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Cai on 2015/5/14 10:05.
 */
@Repository("demoDao")
public class DemoDaoImpl extends BaseDaoImpl<DemoModel, String> implements IDemoDao {
    public DemoDaoImpl() {
        super(DemoModel.class);
    }
}
