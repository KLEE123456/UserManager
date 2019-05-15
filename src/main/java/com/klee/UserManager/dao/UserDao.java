package com.klee.UserManager.dao;

import com.klee.UserManager.pojo.User;

import java.util.List;
import java.util.Map;


public interface UserDao {
    User login(User user);
    User checkName(String  userName);
    int register(User user);
    List<User> findUser(Map map);
}
