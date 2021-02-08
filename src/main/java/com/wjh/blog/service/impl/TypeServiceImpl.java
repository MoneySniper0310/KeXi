package com.wjh.blog.service.impl;

//import com.wjh.blog.NotFoundException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjh.blog.entity.Type;
import com.wjh.blog.mapper.TypeMapper;
import com.wjh.blog.service.TypeService;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    @Transactional
    @Override
    public List<Type> listType() {

        //使用PageHelper
//        PageHelper.startPage(2,3);      ?????????????????????????????????????????????????????????
        return typeMapper.listType();
    }

    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }
//    @Transactional
//    @Override
//    public PageInfo<Type> findItemByPage(int currentPage, int pageSize) {
//        PageHelper.startPage(currentPage, pageSize);
//        //使用PageHelper
//        List<Type> allTypes = typeMapper.listType();
//        PageInfo<Type> pageInfo = new PageInfo<>(allTypes);
//        return pageInfo;
//    }

    @Transactional
    @Override
    public int updateType(Type type) {
//        Type t = typeMapper.getType(id);
//        if (t == null) {
//            throw new NotFoundException("不存在该类型");
//        }
//        BeanUtils.copyProperties(type, t);       //将type里的值复制到t中
//        return typeMapper.saveType(t.getId());               //会根据t中的id进行更新
        return typeMapper.updateType(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {

        typeMapper.deleteType(id);
    }
}
