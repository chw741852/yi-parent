package com.yi.admin.demo.service.impl;

import com.yi.core.service.impl.BaseServiceImpl;
import com.yi.admin.demo.model.DemoModel;
import com.yi.admin.demo.service.IDemoService;
import org.springframework.stereotype.Service;

/**
 * Created by Cai on 2015/5/14 10:08.
 */
@Service("demoService")
public class DemoServiceImpl extends BaseServiceImpl<DemoModel, String> implements IDemoService {
}
