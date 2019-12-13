package com.hyp.learn.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.nio
 * hyp create at 19-11-14
 **/
public class BIOMysqlClient {
    public static void main(String[] args) {
        try (Socket s = new Socket("127.0.0.1", 9098);
             OutputStream os = s.getOutputStream();) {
            Scanner scanner=new Scanner(System.in);
            scanner.nextLine();
            os.write("Hello World".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
