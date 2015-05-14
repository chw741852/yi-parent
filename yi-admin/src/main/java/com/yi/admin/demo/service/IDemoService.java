package com.yi.admin.demo.service;

import com.yi.core.service.IBaseService;
import com.yi.admin.demo.model.DemoModel;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Cai on 2015/5/14 10:07.
 */
@Transactional
public interface IDemoService extends IBaseService<DemoModel, String> {
}
