package com.wjh.blog.service;

import com.wjh.blog.entity.Tag;

import java.util.List;


public interface TagService {

    int saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> listTag();   //后台管理分页查询

    List<Tag> getAllTag();

    Tag getTagByName(String name);

    List<Tag> getTagByString(String text);

    int updateTag(Tag tag);     //传入id及更新后的参数

    void deleteTag(Long id);
}
