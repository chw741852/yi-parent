package com.yi.d1.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url, Object... urlVariables) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res;

        LOGGER.info("调用远程API: url={}, params={}", url, urlVariables);

        try {
            res = restTemplate.getForEntity(url, String.class, urlVariables);
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("code", -1);
            error.put("msg", "远程服务错误: " + e.getMessage());
            LOGGER.warn(error.toJSONString());

            return error.toJSONString();
        }

        LOGGER.info("调用远程API: 返回结果: {}", res.getBody());

        return res.getBody();
    }

    public static String post(String url, JSONObject params, Map<String, String> headerMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                headers.add(key, headerMap.get(key));
            }
        }
        HttpEntity<JSONObject> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> res;

        LOGGER.info("调用远程API: url={}, params={}", url, params.toJSONString());

        try {
            res = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return "远程服务错误: " + e;
        }

        LOGGER.info("调用远程API: 返回结果: {}", res.getBody());

        return res.getBody();
    }

    public static String post(String url, JSONObject params) {
        return post(url, params, null);
    }
}