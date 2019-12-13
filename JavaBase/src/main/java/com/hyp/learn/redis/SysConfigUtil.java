package com.hyp.learn.redis;

import java.util.Properties;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.redis
 * hyp create at 19-11-23
 **/
public class SysConfigUtil {

    private Properties properties;

    public static SysConfigUtil getSysConfigUtil(String s) throws Exception {
        return new SysConfigUtil(s);
    }

    private SysConfigUtil(String s) throws Exception {
        if (null == s || "".equals(s)) {
            throw new Exception();
        }
        properties = new Properties();
        properties.load(
                Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(s)
        );

    }

    public String getString(String s) {
        return (properties.getProperty(s));
    }

    public Integer getInt(String s) {
        return Integer.parseInt(properties.getProperty(s));
    }

    public Long getLong(String s) {
        return Long.parseLong(properties.getProperty(s));
    }

    public Boolean getBoolean(String s) {
        return Boolean.parseBoolean(properties.getProperty(s));
    }
}
