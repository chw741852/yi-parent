package com.yi.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by caihongwei on 2018/5/9 16:38.
 */
public class RandomUtil {
    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBER_CHAR = "0123456789";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯数字字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateStringNUM(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuilder sb = new StringBuilder();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 创建订单号
     * @return string
     */
    public static String generateOrderNo() {
        Random random = new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String rdNum = df.format(new Date());
        int ird = random.nextInt(9999999);
        String srd = String.format("%07d", ird);

        return rdNum + srd;
    }
}
