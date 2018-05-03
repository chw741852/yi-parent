package com.yi.d1;

import com.yi.logging.AbstractLogAspect;
import com.yi.logging.LogBean;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * Created by caihongwei on 2018/5/3 9:30.
 */
@Component
@Aspect
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
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
    }

    @Override
    @Pointcut("execution(public * com.yi.d1.controller.*.*(..))")
    public void aspectMethod() {}
}
