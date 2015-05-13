package com.hong.core.exception;

/**
 * Created by Cai on 2015/4/16 15:56.
 */
public class MoreTryException extends CommonException {
    public MoreTryException(String code, String message) {
        super(code, message);
    }

    public MoreTryException() {
        this("100001", "尝试太多");
    }
}
