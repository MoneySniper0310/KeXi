package com.wjh.blog.service;

import com.wjh.blog.entity.User;

public interface UserService {

    User findByUsernameAndPassword(String username, String password);
}
