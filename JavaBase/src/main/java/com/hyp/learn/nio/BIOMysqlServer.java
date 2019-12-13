package com.hyp.learn.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hyp Project name is JavaLearn Include in com.hyp.learn.nio hyp create
 *         at 19-11-14
 **/
public class BIOMysqlServer {

    static byte[] bs = new byte[1024];

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(9098);) {

            while (true) {
                System.out.println("wait conn");
                // 阻塞1

                Socket cs = ss.accept();
                System.out.println("conn success");
                InputStream is = cs.getInputStream();
                System.out.println("reading data");

                int len = 0;
                while (true) {
                    // 阻塞2,放弃CPU，不支持并发
                    // 1.为每个连接创建线程，造成资源浪费
                    // 2.、nio，单线程处理并发
                    len = is.read(bs);
                    System.out.println("reading success");
                    if (len == -1) {
                        break;
                    }
                    System.out.print(new String(bs, 0, len));
                }
                System.out.println("\nread finish");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
