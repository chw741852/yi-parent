package com.yi.logging;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yi.common.util.DateUtil;
import com.yi.logging.annotation.Log;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by caihongwei on 21/11/2017 7:03 PM.
 */
public abstract class AbstractLogAspect {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractLogAspect.class);
    private ExecutorService threadPool;
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    public abstract void aspectMethod();

    public AbstractLogAspect() {
    }

    public AbstractLogAspect(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Before("aspectMethod()")
    public void before() {
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(value = "aspectMethod() && @annotation(log)", returning = "r")
    public void afterReturning(JoinPoint joinPoint, Log log, Object r) {
        long times = System.currentTimeMillis() - startTime.get();
        if (threadPool == null) {
            log(joinPoint, log, r, times);
        } else {
            CompletableFuture.supplyAsync(() -> log(joinPoint, log, r, times), threadPool);
        }
    }

    @AfterThrowing(value = "aspectMethod() && @annotation(log)", throwing = "t")
    public void afterThrowing(JoinPoint joinPoint, Log log, Throwable t) {
        afterReturning(joinPoint, log, t);
    }

    public abstract String getUsername();

    public abstract void sendLog(LogBean logBean);

    private boolean log(JoinPoint joinPoint, Log log, Object response, long times) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest) {
                continue;
            }
            String argName = "args" + i;
            try {
                JSONObject j = JSONObject.parseObject(JSONObject.toJSONString(args[i]));
                if (j != null) {
                    for (String s : j.keySet()) {
                        sb.append(s).append("=").append(j.get(s)).append("&");
                    }
                }
            } catch (ClassCastException | JSONException e) {
                sb.append(argName).append("=").append(args[i]).append("&");
            }
        }
        String request;
        if (sb.length() > 0) {
            request = sb.toString().substring(0, sb.length() - 1);
        } else {
            request = "";
        }
        String api = log.api();
        if (api.trim().equals("")) {
            api = getApi(joinPoint);
        }
        LogBean logBean = new LogBean();
        logBean.setUsername(getUsername());
        logBean.setOpDate(DateUtil.dateToStr(new Date()));
        logBean.setTimes(times);
        logBean.setTitle(log.title());
        logBean.setApi(api);
        logBean.setOpDate(log.operate().toString());
        logBean.setDesc(log.desc());
        logBean.setClassName(className);
        logBean.setMethodName(methodName);
        logBean.setRequest(request);
        logBean.setResponse(JSONObject.toJSONString(response));

        sendLog(logBean);

        return true;
    }

    private String getApi(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        RequestMapping classRequestMapping = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
        if (classRequestMapping != null) {
            sb.append(classRequestMapping.value()[0]);
        }
        RequestMapping requestMapping = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            String a = requestMapping.value()[0];
            if (!a.startsWith("/")) {
                sb.append("/");
            }
            sb.append(requestMapping.value()[0]);
        } else {
            GetMapping getMapping = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(GetMapping.class);
            if (getMapping != null) {
                String a = getMapping.value()[0];
                if (!a.startsWith("/")) {
                    sb.append("/");
                }
                sb.append(getMapping.value()[0]);
            } else {
                PostMapping postMapping = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(PostMapping.class);
                String a = postMapping.value()[0];
                if (!a.startsWith("/")) {
                    sb.append("/");
                }
                sb.append(postMapping.value()[0]);
            }
        }

        return sb.toString();
    }
}
