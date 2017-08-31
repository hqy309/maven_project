package com.smart.dao;

import com.smart.entity.User;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectId(String username);

    List<User> queryAll();

    List<User> selectEmail(String email);
}