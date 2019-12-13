package com.hyp.learn.db;


import java.sql.Timestamp;

/**
 * CREATE TABLE `user`
 * (
 * `id`          bigint       NOT NULL AUTO_INCREMENT,
 * `name`        varchar(255) NOT NULL,
 * `passwd`      varchar(500) NOT NULL,
 * `isDeleted`  tinyint      NULL DEFAULT 0,
 * `createTime` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
 * `birthday`    datetime     NULL,
 * PRIMARY KEY (`id`)
 * )
 * COMMENT = '用户';
 */
public class User {

    private Long id;
    private String name;
    private String passwd;
    private Integer isDeleted;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp birthday;

    public User() {
    }

    public User(Long id, String name, String passwd, Integer isDeleted, Timestamp createTime, Timestamp birthday) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.isDeleted = isDeleted;
        this.createTime = createTime;
        this.birthday = birthday;
    }

    public User(String name, String passwd, Timestamp birthday) {
        this.name = name;
        this.passwd = passwd;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", birthday=" + birthday +
                '}';
    }
}
