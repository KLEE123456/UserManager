package com.klee.UserManager.service;

import com.klee.UserManager.pojo.User;

public interface UserService {
    User findUser(User user);
    User checkName(String  userName);
    int register(User user);
}
