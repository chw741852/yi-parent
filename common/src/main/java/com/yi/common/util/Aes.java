package com.yi.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

import static com.yi.common.util.CipherUtil.byte2hex;
import static com.yi.common.util.CipherUtil.hex2byte;

/**
 * Created by caihongwei on 04/12/2017 5:05 PM.
 */
public class Aes {
    private static final Logger LOGGER = LoggerFactory.getLogger(Aes.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM = "AES/CBC/NoPadding";
    private static final String ENCODING = "utf-8";

    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher decCipher;
    private Cipher encCipher;
    private String secretKey;
    private String iv;

    /**
     * aes构造函数
     * @param key 必须16位字符串
     * @param iv 必须16位字符串
     */
    public Aes(String key, String iv) {
        this.secretKey = key;
        this.iv = iv;

        ivspec = new IvParameterSpec(this.iv.getBytes());
        keyspec = new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);

        try {
            decCipher = Cipher.getInstance(ALGORITHM);
            encCipher = Cipher.getInstance(ALGORITHM);

            encCipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            decCipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public String encrypt(String text) {
        byte[] encrypted;

        try {
            encrypted = encCipher.doFinal(padString(text).getBytes(ENCODING));
            return byte2hex(encrypted);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    private static String padString(String source) throws UnsupportedEncodingException {
        char paddingChar = ' ';
        int size = 16;
        int x = source.getBytes(ENCODING).length % size;
        int padLength = size - x;

        StringBuilder sourceBuilder = new StringBuilder(source);
        for (int i = 0; i < padLength; i++) {
            sourceBuilder.append(paddingChar);
        }
        source = sourceBuilder.toString();

        return source;
    }

    public String decrypt(String text) {
        byte[] decrypted = null;

        try {
            decrypted = decCipher.doFinal(hex2byte(text.getBytes(ENCODING)));
            return new String(decrypted, ENCODING).trim();
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

}
