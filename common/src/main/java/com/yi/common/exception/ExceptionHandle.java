package com.yi.common.exception;

import com.yi.common.enums.ResultCodeEnums;
import com.yi.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by caihongwei on 2018/4/24 11:07.
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler
    @ResponseBody
    public ApiResponse handler(Exception e) {
        ApiResponse response = new ApiResponse();

        if (e instanceof BizException) {
            response.setCode(((BizException) e).getCode());
            response.setMsg(e.getMessage());
        } else {
            response.setCode(ResultCodeEnums.C_102.getCode());
            response.setMsg(ResultCodeEnums.C_102.getValue());
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        LOGGER.error(sw.toString());
        return response;
    }
}
