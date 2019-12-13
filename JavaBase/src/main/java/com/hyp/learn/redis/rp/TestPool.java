package com.hyp.learn.redis.rp;

import com.hyp.learn.redis.RedisUtils;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: weishenpeng
 * Date: 2018/6/10
 * Time: 下午 02:54
 */
public class TestPool {
	public static void main(String[] args) throws InterruptedException {

		//初始化连接池
		RedisUtils utils = RedisUtils.getRedisUtil();

		Jedis jedis = utils.getConn();

		Thread sendThread = new Thread(new SendRedPacket(10, 5, "send")) ;
		sendThread.start();

		sendThread.join();

		for(int i = 0; i<9; i++) {
			Thread grabThread = new Thread(new GrabRedPacket("send", "grabId" + UUID.randomUUID())) ;
			grabThread.start();
		}

		TimeUnit.SECONDS.sleep(2222);


	}
}
