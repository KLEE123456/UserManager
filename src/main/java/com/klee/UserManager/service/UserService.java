package com.klee.UserManager.service;

import com.klee.UserManager.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User login(User user);
    User checkName(String  userName);
    int register(User user);
    List<User> findUser(Map map,Integer pageNum);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(Integer userId);
}
