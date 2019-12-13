package com.hyp.learn.db.h2;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db.h2
 * hyp create at 19-12-9
 **/
public class H2DataBaseTest {

    @Test
    public static void main(String[] args) throws Exception {
        InputStream inputStream = H2DataBaseTest.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String driverClass = properties.getProperty("driverClassName");
        String jdbcUrl = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);


//        String sql = "CREATE TABLE  HE_SHOU_YOU(ID INT , NAME VARCHAR(100))";
//        String sql = "INSERT INTO HE_SHOU_YOU VALUES(122,‘呵呵呵‘)";
        String sql = "SELECT * FROM USER_INFO ";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.execute();
        System.out.println("到这步了");

        ResultSet rs = preparedStatement.executeQuery();
        //遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name") + "," + rs.getString("sex"));
        }
        //释放资源
        preparedStatement.close();
        //关闭连接
        conn.close();
    }
}
