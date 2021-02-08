package com.wjh.blog.service;

import com.wjh.blog.dto.*;
import com.wjh.blog.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlogById(Long id);

    UpdateBlog getUpdateBlogById(Long id);

    List<Blog> listBlog();
    List<BlogQuery> getAllBlog();

    //修改recommend,因为recommend从前台接收只能接收字符串，但数据库中的Integer类型，所以转一下
    void transformRecommend(SearchBlog searchBlog);

    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);

    Map<String,List<Blog>> archiveBlog();

    int saveBlog(Blog blog);

    int updateBlog(UpdateBlog updateBlog);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getRecommendedBlog();

    //分页页面根据typeId来选取
    List<FirstPageBlog> getByTypeId(Long id);

    List<FirstPageBlog> getSearchFirstPageBlog(String query);

    List<FirstPageBlog> getByTagId(Long tagId);

    int deleteBlog(Long id);

    DetailedBlog getDetailedBlog(Long id);

    Object countBlog();

}
