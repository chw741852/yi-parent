package com.yi.d1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Md5.class);
    private MessageDigest md5;

    public Md5(){
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.toString());
        }
    }

    public String getMd5(String txt){
        try {
            return byte2hex(md5.digest(txt.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    private String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else
                hs = hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }
}
