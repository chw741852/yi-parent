package com.yi.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by cai on 2014/10/12.
 * 反射工具类
 */
public class ReflectUtil {
    protected final static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 通过字段名称，获取字段
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field findField(Class clazz, String fieldName) {
        Class cl = clazz;
        Field field = null;
        while (cl != null && cl != Object.class) {
            try {
                field = cl.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
                if (cl.getSuperclass() == Object.class) {
                    logger.error(cl.getName() + "中没有字段" + fieldName);
                }
            } finally {
                if (field != null)
                    return field;
                cl = cl.getSuperclass();
                continue;
            }
        }

        return null;
    }
}
