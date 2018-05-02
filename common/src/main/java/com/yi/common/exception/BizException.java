package com.yi.common.exception;

import com.yi.common.enums.ResultCodeEnums;

/**
 * Created by caihongwei on 2018/4/24 11:21.
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 8449093372919806862L;
    private int code;

    public BizException(ResultCodeEnums result, String msg) {
        super(msg);
        this.code = result.getCode();
    }

    public BizException(ResultCodeEnums rspEnum){
        super(rspEnum.getValue());
        this.code = rspEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
