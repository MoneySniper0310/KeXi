package com.wjh.blog.service.impl;

import com.wjh.blog.entity.User;
import com.wjh.blog.mapper.UserMapper;
import com.wjh.blog.service.UserService;
import com.wjh.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
