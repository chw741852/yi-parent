package com.hong.core.service.helper;

/**
 * Created by Cai on 2014/12/25 15:49.
 *
 * service返回数据bean
 */
public class BaseResultBean {
    private int code;
    private String message;
    private Object object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
