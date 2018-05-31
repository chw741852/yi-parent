package com.yi.common.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).build();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String ERROR = "HTTP_ERROR";

    public static String get(String url, Object params, Map<String, String> headers) {
        return request(url, params, null, headers, null);
    }

    public static String get(String url, Object params) {
        return get(url, params, null);
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String post(String url, Object params, Object body, Map<String, String> headers, MediaType mediaType) {
        if (body == null) body = new JSONObject();
        return request(url, params, body, headers, mediaType);
    }

    public static String post(String url, Object body, Map<String, String> headers, MediaType mediaType) {
        return post(url, null, body, headers, mediaType);
    }

    public static String post(String url, Object body, MediaType mediaType) {
        return post(url, null, body, null, mediaType);
    }

    public static String post(String url, Object body) {
        return post(url, null, body, null, null);
    }

    public static String post(String url) {
        return post(url, null, null, null, null);
    }

    private static String request(String url, Object params, Object body, Map<String, String> headers, MediaType mediaType) {
        Request.Builder builder = new Request.Builder();
        if (params != null) {
            String s = object2String(params);
            if (url.contains("?")) {
                builder.url(url + "&" + s);
            } else {
                builder.url(url + "?" + s);
            }
        } else {
            builder.url(url);
        }
        if (body != null) {
            if (mediaType != null) {
                builder.post(RequestBody.create(mediaType, JSONObject.toJSONString(body)));
            } else {
                FormBody.Builder formBody = new FormBody.Builder();
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(body));
                json.keySet().forEach(k -> formBody.add(k, json.getString(k)));
                builder.post(formBody.build());
            }
        }
        if (headers != null) {
            headers.keySet().forEach(key -> builder.header(key, headers.get(key)));
        }

        try {
            Response response = okHttpClient.newCall(builder.build()).execute();
            assert response.body() != null;
            String result = response.body().string();

            LOGGER.info("{}, Response Body: {}", response, result);
            System.out.println(response + ", Response Body: " + result);
            return result;
        } catch (IOException e) {
            LOGGER.error(e + "");
            return ERROR;
        }
    }

    /**
     * 对象转字符串参数
     *
     * @param o 对象
     * @return string
     */
    public static String object2String(Object o) {
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(o));
        StringBuilder str = new StringBuilder();
        params.keySet().forEach(key -> {
            String val = params.getString(String.valueOf(key));
            str.append(key)
                    .append('=')
                    .append(val)
                    .append('&');
        });
        return str.toString();
    }

    public String getRealIpByHeader(HttpServletRequest request, String header) {
        return request.getHeader(header);
    }

    public String getRealIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    LOGGER.error(e.toString());
                    return null;
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
