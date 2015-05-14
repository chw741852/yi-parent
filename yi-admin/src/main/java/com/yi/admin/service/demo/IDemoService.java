package com.yi.admin.service.demo;

import com.yi.admin.model.demo.DemoModel;
import com.yi.core.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Cai on 2015/5/14 10:07.
 */
@Transactional
public interface IDemoService extends IBaseService<DemoModel, String> {
}
