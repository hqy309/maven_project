package com.smart.service;

import com.smart.entity.User;

import java.util.List;

/**
 * Created by Mars on 2017/8/30.
 */
public interface UserService {

    public User doUserLogin(User user);

    public User findByUserName(String username);

    public List<User> queryAll();

    public void insertUser(User user);

    public User findByEmail(String email);
}
