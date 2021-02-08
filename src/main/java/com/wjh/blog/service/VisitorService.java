package com.wjh.blog.service;

import com.wjh.blog.entity.Visitor;

public interface VisitorService {

    Visitor findByVisitorNameAndPassword(String visitorName, String password);

    Visitor findByVisitorName(String visitorName);

    void addVisitor(Visitor newVisitor);
}
