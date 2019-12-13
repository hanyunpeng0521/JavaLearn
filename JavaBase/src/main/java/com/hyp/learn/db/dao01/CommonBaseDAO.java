package com.hyp.learn.db.dao01;

import com.hyp.learn.db.commons.Page;

import java.util.List;

/**
 * @author hyp
 * Project name is blog
 * Include in com.hyp.blog.dao
 * hyp create at 19-12-11
 **/
public interface CommonBaseDAO<T, K> {


    public K insert(T t) throws Exception;

    public int update(T t) throws Exception;

    public int delete(K k) throws Exception;

    public T findById(K k) throws Exception;

    public List<T> findAll() throws Exception;

    public Page findAll(Integer page, Integer size) throws Exception;


}
