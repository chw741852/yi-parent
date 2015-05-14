package com.yi.admin.service.demo.impl;

import com.yi.admin.model.demo.DemoModel;
import com.yi.admin.service.demo.IDemoService;
import com.yi.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by Cai on 2015/5/14 10:08.
 */
@Service("demoService")
public class DemoServiceImpl extends BaseServiceImpl<DemoModel, String> implements IDemoService {
}
