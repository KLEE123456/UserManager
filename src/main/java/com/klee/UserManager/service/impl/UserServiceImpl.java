package com.klee.UserManager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.klee.UserManager.dao.UserDao;
import com.klee.UserManager.pojo.User;
import com.klee.UserManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(User user) {
        User user1 = userDao.login(user);
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

    @Override
    public List<User> findUser(Map map,Integer pageNum) {
        if (pageNum==null){
            pageNum=1;
        }
        PageHelper.startPage(pageNum,5);
        List<User> userList = userDao.findUser(map);
        return userList;
    }
}
