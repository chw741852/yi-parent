package com.yi.d1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class Aes {
    private static final Logger logger = LoggerFactory.getLogger(Aes.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM = "AES/CBC/NoPadding";
    private static final String ENCODING = "utf-8";

    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher decCipher;
    private Cipher encCipher;
    private String secretKey;
    private String iv;

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
            logger.error(e.toString());
        }
    }

    public String encrypt(String text) {
        byte[] encrypted;

        try {
            encrypted = encCipher.doFinal(padString(text).getBytes(ENCODING));
            return byte2hex(encrypted);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    private static String padString(String source) throws UnsupportedEncodingException {
        char paddingChar = ' ';
        int size = 16;
        int x = source.getBytes(ENCODING).length % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }

    public String decrypt(String text) {
        byte[] decrypted;

        try {
            decrypted = decCipher.doFinal(hex2byte(text.getBytes(ENCODING)));
            String plain = new String(decrypted, ENCODING).trim();
            return plain;
        } catch (Exception e) {
        }
        return null;
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
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
}
