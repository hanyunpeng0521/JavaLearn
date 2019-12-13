package com.hyp.learn.db.dao02;

import com.hyp.learn.db.User;
import com.hyp.learn.db.commons.Page;

import java.util.List;

/**
 * @author hyp
 * Project name is JavaLearn
 * Include in com.hyp.learn.db.dao02
 * hyp create at 19-12-11
 **/
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    @Override
    public Long insert(User user) throws Exception {
        String sql =
                "insert into user(name,passwd,birthday) values(?,?,?)";
        return Long.valueOf(super.add(sql, user));
    }

    @Override
    public int update(User user) throws Exception {
        return 0;
    }

    @Override
    public int delete(Long aLong) throws Exception {
        return 0;
    }

    @Override
    public User findById(Long aLong) throws Exception {
        return null;
    }

    @Override
    public List<User> findAll() throws Exception {
        return null;
    }

    @Override
    public Page findAll(Integer page, Integer size) throws Exception {
        return null;
    }
}
