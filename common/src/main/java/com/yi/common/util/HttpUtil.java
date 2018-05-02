package com.yi.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static final RestTemplate restTemplate = new RestTemplateBuilder().requestFactory(() -> {
        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);
        requestFactory.setWriteTimeout(10000);
        return requestFactory;
    }).messageConverters(new FastJsonHttpMessageConverter()).build();

    public static final String ERROR = "HTTP_ERROR";

    public static String get(String url, Object... urlVariables) {
        ResponseEntity<String> res;

        LOGGER.info("Request Remote API: {}, params: {}", url, urlVariables);

        try {
            res = restTemplate.getForEntity(url, String.class, urlVariables);
        } catch (Exception e) {
            LOGGER.error("Request Remote API error: " + e);

            return ERROR;
        }

        LOGGER.info("Response Remote API: {}, response: {}", url, res.getBody());

        return res.getBody();
    }

    public static String post(String url, Object params, Map<String, String> headerMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                headers.add(key, headerMap.get(key));
            }
        }
        HttpEntity<Object> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> res;

        LOGGER.info("Request Remote API: {}, params: {}", url, JSONObject.toJSONString(params));

        try {
            res = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            LOGGER.error("Request Remote API error: " + e);
            return ERROR;
        }

        LOGGER.info("Response Remote API: {}, response: {}", url, res.getBody());

        return res.getBody();
    }

    public static String post(String url, Object params) {
        return post(url, params, null);
    }

    /**
     * post 请求，请求参数放入url中
     * @param url 请求url
     * @param params 请求参数
     * @return string
     */
    public static String postWithoutBody(String url, JSONObject params) {
        StringBuilder str = new StringBuilder();
        params.keySet().forEach(key -> {
            String val = params.getString(String.valueOf(key));
            str.append(key)
                    .append('=')
                    .append(val)
                    .append('&');
        });

        url += "?" + str;
        return post(url, new JSONObject(), null);
    }
}