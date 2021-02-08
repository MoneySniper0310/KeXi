package com.wjh.blog.service.impl;

//import com.wjh.blog.NotFoundException;

import com.wjh.blog.entity.Tag;
import com.wjh.blog.mapper.TagMapper;
import com.wjh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;


    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagMapper.listTag();
    }

    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getTagByString(String text) {
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long aLong : longs) {
            tags.add(tagMapper.getTag(aLong));
        }
        return tags;
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteTag(id);
    }
}
