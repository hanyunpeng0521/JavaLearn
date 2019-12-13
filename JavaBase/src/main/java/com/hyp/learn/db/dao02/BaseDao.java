package com.hyp.learn.db.dao02;

import java.util.List;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db.dao02
 * hyp create at 19-12-11
 **/
public interface BaseDao {
    int add(String sql, Object obj);

    void delete(String sql, int[] ids);

    int update(String sql, Object[] params);//可选

    int update(String sql, Object obj);

    <T> T query(String sql, Object[] params, Class<T> clazz);

    <T> List<T> queryAll(String sql, Object[] params, Class<T> clazz);
}
