package com.hyp.learn.redis;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.redis
 * hyp create at 19-11-23
 **/
public class Test {
    public static void main(String[] args) {
        RedisUtils redisUtils = RedisUtils.getRedisUtil();
        System.out.println(redisUtils.info());
    }
}
