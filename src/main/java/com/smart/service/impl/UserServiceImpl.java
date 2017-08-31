package com.smart.service.impl;

import com.smart.dao.UserDao;
import com.smart.entity.User;
import com.smart.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mars on 2017/8/30.
 */
@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User doUserLogin(User user) {
        logger.info("username" + user.getName());
        List<User> list = userDao.selectId(user.getUsername());

        logger.info("list" + list.size());

        if (list.size() == 0) {
            return null;
        } else {
            if (user.getPassword().equals(list.get(0).getPassword())) {
                return list.get(0);
            } else {
                return null;
            }
        }
    }

    @Override
    public User findByUserName(String username) {
        List<User> list = userDao.selectId(username);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> queryAll() {
        return userDao.queryAll();
    }

    @Override
    @Transactional
    public void insertUser(User user) {
      /*  String insertSql = "INSERT INTO user(username, password, age, name, role, email, phone) " +
                "VALUE('" + user.getUsername() + "','" + user.getPassword() + "','" + user.getAge() +
                "','" + user.getName() + "','" + user.getRole() + "','" + user.getEmail() + "','" + user.getPhone() + "');";
        super.insert(insertSql);*/
        userDao.insert(user);
    }

    @Override
    public User findByEmail(String email) {
        List<User> list = userDao.selectEmail(email);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
