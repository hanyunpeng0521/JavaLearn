package com.hyp.learn.db.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db.h2
 * hyp create at 19-12-9
 **/
public class H2ConnTest {
    //数据库连接URL，
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/dev/h2/data/quickstart;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";
    //连接数据库时使用的用户名
    private static final String USER = "sa";
    //连接数据库时使用的密码
    private static final String PASSWORD = "123456";
    //连接H2数据库时使用的驱动类，org.h2.Driver这个类是由H2数据库自己提供的，在H2数据库的jar包中可以找到
    private static final String DRIVER_CLASS="org.h2.Driver";

    public static void main(String[] args) throws Exception {
        // 加载H2数据库驱动
        Class.forName(DRIVER_CLASS);
        // 根据连接URL，用户名，密码获取数据库连接
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();

        //如果存在USER_INFO表就先删除USER_INFO表
        stmt.execute("DROP TABLE IF EXISTS USER_INFO");
        //创建USER_INFO表
        stmt.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
        //新增
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','大日如来','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','青龙','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','白虎','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','朱雀','女')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','玄武','男')");
        stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','苍狼','男')");
        //删除
        stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
        //修改
        stmt.executeUpdate("UPDATE USER_INFO SET name='孤傲苍狼' WHERE name='苍狼'");
        //查询
        ResultSet rs = stmt.executeQuery("SELECT * FROM USER_INFO");
        //遍历结果集
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name")+ "," + rs.getString("sex"));
        }
        //释放资源
        stmt.close();
        //关闭连接
        conn.close();
    }
}
