package com.yi.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by caihongwei on 2018/4/24 15:58.
 */
public class IdUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdUtil.class);

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建订单号
     * @return string
     */
    public static String orderNum() {
        Random random = new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String rdNum = df.format(new Date());
        int ird = random.nextInt(9999999);
        String srd = String.format("%07d", ird);

        return rdNum + srd;
    }
}
