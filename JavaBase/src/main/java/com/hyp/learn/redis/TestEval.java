package com.hyp.learn.redis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.redis
 * hyp create at 19-11-23
 **/
public class TestEval {

    static String host = "127.0.0.1";
    static int port = 6379;

    static int honBaoCount = 10000;

    static int threadCount = 10;

    static String hongBaoList = "hongBaoList";        //L1 为消费的红包 预先生成的红包 {id、money、userid(暂时未空)}
    static String hongBaoConsumedList = "hongBaoConsumedList";        //L2 已消费的红包队列
    static String hongBaoConsumedMap = "hongBaoConsumedMap";        //去重的map

    static Random random = new Random();

    /**
     * -- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空
     * -- 参数：红包队列名， 已消费的队列名，去重的Map名，用户ID
     * -- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money
     * <p>
     * -- 如果用户已抢过红包，则返回nil
     * if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then
     * return nil
     * else
     * -- 先取出一个小红包
     * local hongBao = redis.call('rpop', KEYS[1]);
     * if hongBao then
     * local x = cjson.decode(hongBao);
     * -- 加入用户ID信息
     * x['userId'] = KEYS[4];
     * local re = cjson.encode(x);
     * -- 把用户ID放到去重的set里
     * redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);
     * -- 把红包放到已消费队列里
     * redis.call('lpush', KEYS[2], re);
     * return re;
     * end
     * end
     * return nil
     */
    static String tryGetHongBaoScript =
//          "local bConsumed = redis.call('hexists', KEYS[3], KEYS[4]);\n"
//          + "print('bConsumed:' ,bConsumed);\n"
            "if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then\n"
                    + "return nil\n"
                    + "else\n"
                    + "local hongBao = redis.call('rpop', KEYS[1]);\n"
//          + "print('hongBao:', hongBao);\n"
                    + "if hongBao then\n"
                    + "local x = cjson.decode(hongBao);\n"
                    + "x['userId'] = KEYS[4];\n"
                    + "local re = cjson.encode(x);\n"
                    + "redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);\n"
                    + "redis.call('lpush', KEYS[2], re);\n"
                    + "return re;\n"
                    + "end\n"
                    + "end\n"
                    + "return nil";

    public static void main(String[] args) throws InterruptedException {
        //generateTestData();
        testTryGetHongBao();
    }

    static public void generateTestData() throws InterruptedException {
        Jedis jedis = new Jedis(host, port);
        jedis.flushAll();
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; ++i) {
            final int temp = i;
            Thread thread = new Thread() {
                public void run() {
                    Jedis jedis = new Jedis(host);
                    int per = honBaoCount / threadCount;
                    JSONObject object = new JSONObject();
                    for (int j = temp * per; j < (temp + 1) * per; j++) {
                        object.put("id", j);
                        object.put("money", j);
                        jedis.lpush(hongBaoList, object.toJSONString());
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }
        latch.await();

        jedis.close();
    }

    static public void testTryGetHongBao() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(threadCount);

        long startTime = System.currentTimeMillis();
        System.err.println("start:" + startTime);

        for (int i = 0; i < threadCount; ++i) {
            final int temp = i;
            Thread thread = new Thread() {
                public void run() {
                    Jedis jedis = new Jedis(host, port);
                    String sha = jedis.scriptLoad(tryGetHongBaoScript);
                    int j = honBaoCount / threadCount * temp;
                    while (true) {
                        //抢红包方法
                        Object object = jedis.eval(tryGetHongBaoScript, 4,
                                hongBaoList/*预生成的红包队列*/,
                                hongBaoConsumedList, /*已经消费的红包队列*/
                                hongBaoConsumedMap, /*去重的map*/
                                "" + j  /*用户id*/
                        );
                        j++;
                        if (object != null) {
                            //do something...
                          System.out.println("get hongBao:" + object);
                        } else {
                            //已经取完了
                            if (jedis.llen(hongBaoList) == 0)
                                break;
                        }
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }

        latch.await();
        long costTime = System.currentTimeMillis() - startTime;

        System.err.println("costTime:" + costTime);
    }
}
