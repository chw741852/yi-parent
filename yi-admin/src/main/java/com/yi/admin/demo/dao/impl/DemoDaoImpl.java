package com.yi.admin.demo.dao.impl;

import com.yi.core.dao.impl.BaseDaoImpl;
import com.yi.admin.demo.dao.IDemoDao;
import com.yi.admin.demo.model.DemoModel;
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
