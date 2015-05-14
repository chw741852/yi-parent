package com.yi.core.exception;

public class CommonException extends RuntimeException {
    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return "错误代号:" + code + "," + ((message != null) ? (s + ": " + message) : s);
    }

    private static final long serialVersionUID = 2332608236621015980L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CommonException(String code, String message) {
        super(message);
        this.code = code;
    }

}