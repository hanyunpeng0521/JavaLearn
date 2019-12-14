package com.hyp.learn.db.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * 阿里druid数据库连接池
 *
 * @author hyp
 * Project name is blog
 * Include in com.hyp.blog.utils
 * hyp create at 19-12-9
 **/
public class DruidUtils {
    private static volatile DruidUtils INSTANCE;

    public static DruidUtils getINSTANCE() {
        if (null == INSTANCE) {
            synchronized (DruidUtils.class) {
                if (null == INSTANCE) {
                    INSTANCE = new DruidUtils();
                }
            }
        }
        return INSTANCE;
    }

    private DataSource ds = null;

    private DruidUtils() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("db.properties"));
            String sql = DruidUtils.class.getClassLoader().getResource("blog.sql").getPath();
            ds = DruidDataSourceFactory.createDataSource(properties);
            if (StringUtils.isNotBlank(sql) && sql.endsWith(".sql")) {
                QueryRunner qr = new QueryRunner(ds);
                System.out.println(qr.execute("RUNSCRIPT FROM '" + sql + "'"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Connection
     * @throws SQLException
     * @Method: getConnection
     * @Description: 从数据源中获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        DruidUtils instance = DruidUtils.getINSTANCE();
        return instance.ds.getConnection();
    }

    public static DataSource getDataSource() {
        DruidUtils instance = DruidUtils.getINSTANCE();
        return instance.ds;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(DruidUtils.getConnection());

    }

}
