package com.hyp.learn.db.utils;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db.utils
 * hyp create at 19-12-11
 **/
public class DruidUtilsTest {

    @Test
    public void getConnection() throws SQLException {
        DruidUtils.getConnection();
    }
}