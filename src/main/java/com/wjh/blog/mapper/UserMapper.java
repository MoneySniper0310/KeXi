package com.wjh.blog.mapper;

import com.wjh.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
