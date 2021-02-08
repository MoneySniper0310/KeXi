package com.wjh.blog.mapper;

import com.wjh.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TagMapper {

    int saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> listTag();

    List<Tag> getAllTag();

    Tag getTagByName(String name);

    int updateTag(Tag tag);

    void deleteTag(Long id);
}
