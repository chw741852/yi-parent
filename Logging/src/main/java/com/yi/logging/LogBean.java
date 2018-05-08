package com.yi.logging;

import lombok.Data;

/**
 * Created by caihongwei on 2018/5/3 10:52.
 *
 * TODO
 */
@Data
public class LogBean {
    private String username;    // 用户名
    private String createdAt;   // 创建时间
    private long times;         // 耗时
    private String title;       // 日志标题
    private String operate;     // 操作类型 建表
    private String desc;        // 日志描述
    private String className;   // 类名
    private String methodName;  // 方法名

    private String ip;          // 请求ip
    private String requestUri;  // 请求uri /api/order
    private String requestUrl;  // 请求url http://weijian.com/api/order
    private String requestParams;   // 请求参数
    private String requestMethod;   // 请求类型 get post
    private String response;        // 返回参数

    // 请求系统来源，PC管理页面 前段H5展示（API） 第三方商户 平台内部系统 渠道，建表
    private String sysSource;
}
