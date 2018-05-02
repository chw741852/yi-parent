package com.yi.common.response;

import java.io.Serializable;

/**
 * Created by caihongwei on 2018/4/23 18:00.
 */
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1330909718058118801L;
    private int code = 0;       // code 0-成功 其他-失败
    private String msg;     // 描述
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
