package com.yi.admin.demo.controller;

import com.yi.admin.demo.model.DemoModel;
import com.yi.admin.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Cai on 2015/5/14 10:09.
 */
@Controller
@RequestMapping("/admin/demo")
public class DemoController {
    @Autowired
    private IDemoService demoService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap) {
        List<DemoModel> demoList = demoService.findAll();
        modelMap.addAttribute("demoList", demoList);

        return "/admin/demo/list";
    }
}
