package com.yi.d1;

import com.alibaba.fastjson.JSONObject;
import com.yi.logging.AbstractLogAspect;
import com.yi.logging.LogBean;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * Created by caihongwei on 2018/5/3 9:30.
 */
@Component
@Aspect
@RefreshScope
public class LogAspect extends AbstractLogAspect {
    public LogAspect() {
        super(Executors.newFixedThreadPool(3));
    }

    @Override
    public String getUsername() {
//        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        return request.getParameter("username");
        return "Admin";
    }

    @Override
    public void sendLog(LogBean logBean) {
        LOGGER.info(JSONObject.toJSONString(logBean));

    }

    @Override
    @Pointcut("execution(public * com.yi.d1.controller.*.*(..))")
    public void aspectMethod() {}
}
