package com.klee.UserManager.dao;

import com.klee.UserManager.pojo.User;


public interface UserDao {
    User findUser(User user);
    User checkName(String  userName);
    int register(User user);
}
