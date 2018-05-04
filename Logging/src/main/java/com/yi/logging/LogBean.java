package com.yi.logging;

import lombok.Data;

/**
 * Created by caihongwei on 2018/5/3 10:52.
 */
@Data
public class LogBean {
    private String username;
    private String date;
    private long times;
    private String title;
    private String operate;
    private String desc;
    private String className;
    private String methodName;

    private String ip;
    private String requestUri;
    private String requestUrl;
    private String requestParams;
    private String requestMethod;
    private String response;
}
