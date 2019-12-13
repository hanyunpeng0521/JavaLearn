package com.hyp.learn.redis;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.redis
 * hyp create at 19-11-22
 **/
public class RedisClientByResp {
    private Socket socket;

    public RedisClientByResp() {
        try {
            socket = new Socket("127.0.0.1", 6379);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接失败" + e.getMessage());
        }
    }

    /**
     * 请求redis
     *
     * @param cr
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    private String postRequest(CommandRedis cr, String key, String value) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("*3").append("\r\n");
        sb.append("$").append(cr.name().length()).append("\r\n");
        sb.append(cr.name()).append("\r\n");
        // 注意中文汉字。一个汉字两个字节，长度为2
        sb.append("$").append(key.getBytes().length).append("\r\n");
        sb.append(key).append("\r\n");
        if (null != value) {
            sb.append("$").append(value.getBytes().length).append("\r\n");
            sb.append(value).append("\r\n");
        }
        System.out.println(sb.toString());
        socket.getOutputStream().write(sb.toString().getBytes());
        byte[] b = new byte[64];
        sb = new StringBuffer();
        InputStream is = socket.getInputStream();
        while (is.available() > 0) {
            int len = is.read(b);
            if (len <= 0) {
                break;
            }
            sb.append(new String(b, 0, len));
        }
        return sb.toString();
    }


    /**
     * 设置值
     *
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    public String set(String key, String value) throws IOException {
        return postRequest(CommandRedis.SET, key, value);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key) throws Exception {
        return postRequest(CommandRedis.GET, key, null);

    }

    /**
     * 设置值：不会覆盖存在的值
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public String setnx(String key, String value) throws Exception {
        return postRequest(CommandRedis.SETNX, key, value);
    }

    public static void main(String[] args) {
        RedisClientByResp resp = new RedisClientByResp();
        try {
            System.out.println(resp.set("mykey", "myvalue"));
            System.out.println(resp.get("mykey"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!resp.socket.isConnected()) {
                try {
                    resp.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
