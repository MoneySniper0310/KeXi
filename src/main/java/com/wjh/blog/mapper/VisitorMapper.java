package com.wjh.blog.mapper;

import com.wjh.blog.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitorMapper {

    Visitor findByVisitorNameAndPassword(String visitorName, String password);

    Visitor findByVisitorName(String visitorName);

    void addVisitor(Visitor newVisitor);

}
