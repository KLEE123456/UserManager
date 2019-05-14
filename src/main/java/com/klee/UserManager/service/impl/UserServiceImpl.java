package com.klee.UserManager.service.impl;

import com.klee.UserManager.dao.UserDao;
import com.klee.UserManager.pojo.User;
import com.klee.UserManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findUser(User user) {
        User user1 = userDao.findUser(user);
        return user1;
    }

    @Override
    public User checkName(String userName) {
        User user = userDao.checkName(userName);
        return user;
    }

    @Override
    public int register(User user) {
        int rows = userDao.register(user);
        return rows;
    }
}
