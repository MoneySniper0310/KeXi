package com.wjh.blog.service;

import com.wjh.blog.entity.Type;


import java.util.List;

public interface TypeService {

    int saveType(Type type);

    Type getType(Long id);

    List<Type> listType();   //后台管理分页查询

    List<Type> getAllType();

    Type getTypeByName(String name);

    int updateType(Type type);     //传入id及更新后的参数

    void deleteType(Long id);
}
