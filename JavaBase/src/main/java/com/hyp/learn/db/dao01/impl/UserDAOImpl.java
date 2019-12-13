package com.hyp.learn.db.dao01.impl;


import com.hyp.learn.db.User;
import com.hyp.learn.db.commons.Page;
import com.hyp.learn.db.dao01.BaseDAO;
import com.hyp.learn.db.dao01.UserDao;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 缺点：容易拼接错误，扩展性差,实体类属性必须跟数据库字段相同
 *
 * @author hyp
 * Project name is blog
 * Include in com.hyp.blog.dao.impl
 * hyp create at 19-12-11
 **/
public class UserDAOImpl
        extends BaseDAO<User>
        implements UserDao {

    @Override
    public String getTableName() {
        return "user";
    }

    private String[] oColumns =
            new String[]{"name", "passwd", "is_deleted", "birthday"};
    private String[] columns =
            new String[]{"name", "passwd", "birthday"};
    private String[] allColumns =
            new String[]{"id", "name", "passwd", "birthday", "is_deleted", "create_time"};

    @Override
    public Long insert(User user) throws Exception {
        if (Objects.isNull(user)
                || Objects.isNull(user.getName())
                || Objects.isNull(user.getPasswd()))
            return -1L;


        return super.insert(columns,
                new ScalarHandler<Long>(),
                user.getName(), user.getPasswd(), user.getBirthday());
    }

    @Override
    public int update(User user) throws Exception {
        if (Objects.isNull(user)
                || Objects.isNull(user.getId()))
            return -1;
        return super.update(oColumns, "id=?",
                user.getName(),
                user.getPasswd(),
                user.getIsDeleted(),
                user.getBirthday(),
                user.getId());
    }

    @Override
    public int delete(Long id) throws Exception {
        if (Objects.isNull(id))
            return -1;
        return super.delete("id=?", id);
    }

    @Override
    public User findById(Long id) throws Exception {
        if (Objects.isNull(id))
            return null;
        return super.query(allColumns, "id=?",
                new BeanHandler<User>(User.class
                        , new BasicRowProcessor(new GenerousBeanProcessor())), id);
    }

    @Override
    public List<User> findAll() throws Exception {
        return super.queryAll(allColumns, null,
                new BeanListHandler<User>(User.class
                        , new BasicRowProcessor(new GenerousBeanProcessor())),
                "id asc");
    }

    @Override
    public Page findAll(Integer page, Integer size) throws Exception {

        return super.queryAll(allColumns, null,
                new BeanListHandler<User>(User.class
                        , new BasicRowProcessor(new GenerousBeanProcessor()))
                , page, size,
                "id asc");
    }

    public static void main(String[] args) throws Exception {
        User user = new User(
                "pingxin",
                "12346",
                Timestamp.valueOf("1999-5-21 00:00:00")
        );
        UserDao userDao = new UserDAOImpl();
        Long id = userDao.insert(user);

        User user1 = userDao.findById(id);
        user1.setName("hyp");
        userDao.update(user1);
        System.out.println(userDao.findAll(1, 10));
        userDao.delete(id);
    }
}
