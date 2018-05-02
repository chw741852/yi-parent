package com.yi.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by caihongwei on 2018/5/2 13:34.
 */
public class CipherUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtil.class);

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp;
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else
                hs = hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException(
                    "the length of the byte[] is not even:[" + b.length + "]");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static String getMd5(String txt){
        try {
            return CipherUtil.byte2hex(MessageDigest.getInstance("MD5").digest(txt.getBytes("utf-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    public static Aes getAes(String key, String iv) {
        LoadingCache<String, Aes> cache = CacheBuilder.newBuilder().maximumSize(1024).expireAfterAccess(30, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Aes load(String kv) {
                        String k = kv.substring(0, 16);
                        String v = kv.substring(16, 32);
                        return new Aes(k, v);
                    }
                });

        try {
            return cache.get(key + iv);
        } catch (ExecutionException e) {
            LOGGER.error(e + "");
        }

        return null;
    }

    /**
     * 生成签名
     * 按参数字母排序生成字符串，再MD5签名
     * @param params 参数
     * @param md5Salt md5盐
     * @return string
     */
    public static String generateSign(JSONObject params, String md5Salt){
        String sig = null;
        try{
            StringBuilder str = new StringBuilder();

            if(params != null){

                Object[] keyArr = params.keySet().toArray();
                Arrays.sort(keyArr);

                Arrays.asList(keyArr).forEach(key -> {
                    String val = params.getString((String) key);
                    str.append(key)
                            .append('=')
                            .append(val)
                            .append('&');
                });
                str.deleteCharAt(str.length() -  1);
            }

            str.append(md5Salt);
            sig = CipherUtil.getMd5(str.toString());
        }catch (Exception e) {
            LOGGER.error(e + "");
        }

        return sig;
    }
}
