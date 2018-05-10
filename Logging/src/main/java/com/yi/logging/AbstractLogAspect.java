package com.yi.logging;

import com.alibaba.fastjson.JSONObject;
import com.yi.common.util.DateUtil;
import com.yi.logging.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by caihongwei on 21/11/2017 7:03 PM.
 */
public abstract class AbstractLogAspect {
    @Value("${spring.application.name:}")
    private String applicationName;

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
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        try {
            if (threadPool == null) {
                log(joinPoint, log, r, times, request);
            } else {
                CompletableFuture.supplyAsync(() -> log(joinPoint, log, r, times, request), threadPool)
                        .exceptionally(e -> {
                            LOGGER.warn(e + "");
                            return false;
                        });
            }
        } catch (Exception e) {
            LOGGER.error(e + "");
        }
    }

    @AfterThrowing(value = "aspectMethod() && @annotation(log)", throwing = "t")
    public void afterThrowing(JoinPoint joinPoint, Log log, Throwable t) {
        afterReturning(joinPoint, log, t);
    }

    public abstract String getUsername(HttpServletRequest request);

    public abstract void sendLog(LogBean logBean);

    private boolean log(JoinPoint joinPoint, Log log, Object response, long times, HttpServletRequest request) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < args.length; i++) {
//            if (args[i] instanceof ServletRequest) {
//                continue;
//            }
//            String argName = "args" + i;
//            try {
//                JSONObject j = JSONObject.parseObject(JSONObject.toJSONString(args[i]));
//                if (j != null) {
//                    for (String s : j.keySet()) {
//                        sb.append(s).append("=").append(j.get(s)).append("&");
//                    }
//                }
//            } catch (ClassCastException | JSONException e) {
//                sb.append(argName).append("=").append(args[i]).append("&");
//            }
//        }
//        String params;
//        if (sb.length() > 0) {
//            params = sb.toString().substring(0, sb.length() - 1);
//        } else {
//            params = "";
//        }
        LogBean logBean = new LogBean();

        logBean.setUsername(getUsername(request));
        logBean.setCreatedAt(DateUtil.dateToStr(new Date()));
        logBean.setTimes(times);
        logBean.setTitle(log.title());
        logBean.setOperate(log.operate().toString());
        logBean.setDesc(log.desc());
        logBean.setClassName(className);
        logBean.setMethodName(methodName);
        logBean.setIp(request.getRemoteAddr());
        logBean.setRequestUri(request.getRequestURI());
        logBean.setRequestUrl(request.getRequestURL() == null ? "" : request.getRequestURL().toString());
        logBean.setRequestParams(getParams(request));
        logBean.setRequestMethod(request.getMethod());
        logBean.setResponse(JSONObject.toJSONString(response));
        logBean.setSysSource(applicationName);

        LOGGER.info(JSONObject.toJSONString(logBean));
        sendLog(logBean);

        return true;
    }

    public String getParams(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        Map<String, String[]> map = request.getParameterMap();
        map.keySet().forEach(k -> {
            params.append(k).append("=").append(Arrays.toString(map.get(k))).append("&");
        });

        if (request.getMethod().equalsIgnoreCase("post")) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
                br.close();
            } catch (IOException e) {
                LOGGER.error(e + "");
            }
            params.append("body=").append(sb);
        }

        return params.toString();
    }
}
