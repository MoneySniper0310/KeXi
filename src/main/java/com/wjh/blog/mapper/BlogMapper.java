package com.wjh.blog.mapper;


import com.wjh.blog.dto.*;
import com.wjh.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BlogMapper {

    Blog getBlogById(Long id);

    UpdateBlog getUpdateBlogById(Long id);

    List<Blog> listBlog();
    List<BlogQuery> getAllBlogQuery();
    List<BlogQuery> searchByTitleOrTypeOrRecommend(SearchBlog searchBlog);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getRecommendedBlog();

    //分页页面根据typeId来选取
    List<FirstPageBlog> getByTypeId(Long id);

    //分页页面根据tagId来选取
    List<FirstPageBlog> getByTagId(Long tagId);

    //全局搜索
    List<FirstPageBlog> getSearchFirstPageBlog(@Param(value = "query") String query);

    int saveBlog(Blog blog);
    int saveBlogAndTag(BlogAndTag blogAndTag);

    int updateBlog(UpdateBlog updateBlog);

    //获取详细的blog
    DetailedBlog getDetailedBlog(Long id);

    int deleteBlog(Long id);

    int deleteBlogAndTag(Long blogId);

    List<String> findGroupYear();

    List<Blog> findByYear(String year);

    List<Blog> countBlog();

    void updateBlogViews(Blog tmpBlog);
}
