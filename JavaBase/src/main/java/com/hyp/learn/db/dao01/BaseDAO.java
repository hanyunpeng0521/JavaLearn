package com.hyp.learn.db.dao01;

import com.hyp.learn.db.commons.Page;
import com.hyp.learn.db.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 拼装SQL语句的Dao
 *
 * @author hyp
 * Project name is blog
 * Include in com.hyp.blog.dao
 * hyp create at 19-12-11
 **/
public abstract class BaseDAO<T> {
    protected final Logger log = LoggerFactory.getLogger(getTableName());

    protected QueryRunner qr =
            new QueryRunner(DruidUtils.getDataSource());

    //return AUTO_INCREMENT id
    public long insert(String[] columns, ScalarHandler<Long> rsh,
                       Object... params) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " ("
                + getColumnsName(columns) + ") VALUES ("
                + getColumnsValue(columns) + ")";

        log.debug("{} insert sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(params));

        return qr.insert(sql, rsh, params);
    }

    public int insert(String[] columns,
                      Object... params) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " ("
                + getColumnsName(columns) + ") VALUES ("
                + getColumnsValue(columns) + ")";

        log.debug("{} insert sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(params));

        return qr.update(sql, params);
    }

    public int delete(String whereClause, Object... whereArgs)
            throws SQLException {
        String sql = "delete from " + getTableName() + " where " + whereClause;


        log.debug("{} delete sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(whereArgs));

        return qr.update(sql, whereArgs);
    }

    public int update(String[] updateColumns, String whereClause,
                      Object... params) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET "
                + getUpdateColumns(updateColumns) + " WHERE " + whereClause;

        log.debug("{} update sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(params));

        return qr.update(sql, params);
    }

    public T query(String[] columns, String selection, ResultSetHandler<T> rsh,
                   Object... selectionArgs) throws SQLException {
        String sql = "SELECT " + getColumnsName(columns) + " FROM "
                + getTableName() + " WHERE " + selection;


        log.debug("{} query sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(selectionArgs));

        return qr.query(sql, rsh, selectionArgs);
    }

    public List<T> queryAll(String[] columns, String selection,
                            BeanListHandler<T> rsh, String orderBy, Object... selectionArgs)
            throws SQLException {

        String whereClause = " ";
        if (selection != null && selection.length() > 0) {
            whereClause = " WHERE " + selection;
        }

        String sql = "SELECT " + getColumnsName(columns) + " FROM "
                + getTableName() + whereClause + " ORDER BY " + orderBy;


        log.debug("{} queryAll sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(selectionArgs));

        return qr.query(sql, rsh, selectionArgs);
    }

    public Page queryAll(String[] columns, String selection,
                         BeanListHandler<T> rsh,
                         int page, int size, String orderBy, Object... selectionArgs) throws SQLException {
        String whereClause = " ";
        if (selection != null && selection.length() > 0) {
            whereClause = " WHERE " + selection;
        }

        String sql = "SELECT " + getColumnsName(columns) + " FROM "
                + getTableName() + whereClause + " ORDER BY " + orderBy
                + " limit " + (page - 1) * size + "," + size;

        log.debug("{} queryAll limit sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(selectionArgs));
        List<T> data = qr.query(sql, rsh, selectionArgs);
        long count = this.count(selection, selectionArgs);
        return Page.of(count, page, size, data);
    }

    public boolean find(String selection, ResultSetHandler<T> rsh,
                        Object... selectionArgs) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + selection;


        log.debug("{} find sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(selectionArgs));

        return qr.query(sql, rsh, selectionArgs) != null;
    }

    public long count(String selection, Object... selectionArgs)
            throws SQLException {

        String whereClause = " ";
        if (selection != null && selection.length() > 0) {
            whereClause = " WHERE " + selection;
        }
        String sql = "SELECT COUNT(*) as count FROM " + getTableName()
                + whereClause;


        log.debug("{} count sql= {} **params= {} "
                , getTableName(), sql, Arrays.toString(selectionArgs));

        return qr.query(sql, new ScalarHandler<Long>());

    }

    public abstract String getTableName();

    public String getUpdateColumns(String[] columns) {

        if (columns == null || columns.length < 1) {
            throw new NullPointerException("getColumnsValue columns is empty");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
//            if (columns[i] != null) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(columns[i]).append("=?");

//            }
        }
        return sb.toString();
    }

    public String getColumnsName(String[] columns) {

        if (columns == null || columns.length < 1) {
            throw new NullPointerException("getColumnsValue columns is empty");
        }

        if ("*".equals(columns[0])) {
            return "*";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            sb.append(columns[i]);
            if (i != columns.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String getColumnsValue(String[] columns) {

        if (columns == null || columns.length < 1) {
            throw new NullPointerException("getColumnsValue columns is empty");
        }

        if ("*".equals(columns[0])) {
            return "*";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            sb.append("?");
            if (i != columns.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
