package com.wjh.blog.mapper;

import com.wjh.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TypeMapper {
    int saveType(Type type);

    Type getType(Long id);

//    List<Type> listType(Pageable pageable);
    List<Type> listType();

    List<Type> getAllType();    //前端index显示分类

    Type getTypeByName(String name);

    int updateType(Type type);

    void deleteType(Long id);
}
