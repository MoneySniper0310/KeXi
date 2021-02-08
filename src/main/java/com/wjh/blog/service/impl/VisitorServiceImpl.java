package com.wjh.blog.service.impl;

import com.wjh.blog.entity.Visitor;
import com.wjh.blog.mapper.VisitorMapper;
import com.wjh.blog.service.VisitorService;
import com.wjh.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public Visitor findByVisitorNameAndPassword(String visitorName, String password) {
        Visitor visitor = visitorMapper.findByVisitorNameAndPassword(visitorName, MD5Utils.code(password));
//        System.out.println(visitor);
//        visitor.setNickname(visitor.getNickname());
        return visitor;
    }

    @Override
    public Visitor findByVisitorName(String visitorName) {
        Visitor visitor = visitorMapper.findByVisitorName(visitorName);
        return visitor;
    }

    @Override
    public void addVisitor(Visitor newVisitor) {
        newVisitor.setCreatTime(new Date());
        visitorMapper.addVisitor(newVisitor);
    }
}
