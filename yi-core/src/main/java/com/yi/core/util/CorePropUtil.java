package com.yi.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Cai on 2014/12/15 14:29.
 */
public class CorePropUtil {
    protected final static Logger logger = LoggerFactory.getLogger(CorePropUtil.class);
    private Properties props = new Properties();

    private static class CorePropUtilHolder {
        static final CorePropUtil INSTANCE = new CorePropUtil();
    }

    public static CorePropUtil getInstance() {
        return CorePropUtilHolder.INSTANCE;
    }

    private CorePropUtil() {
        InputStream inpf = CorePropUtil.class.getClassLoader().getResourceAsStream("property/core.properties");
        try {
            props.load(inpf);
        } catch (IOException e) {
            logger.error("读取core.properties异常：" + e);
        }
    }

    public String getKey(String key) {
        return props.getProperty(key);
    }
}
