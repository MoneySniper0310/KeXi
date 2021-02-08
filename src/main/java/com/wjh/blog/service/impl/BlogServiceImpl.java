package com.wjh.blog.service.impl;

import com.wjh.blog.NotFoundException;
import com.wjh.blog.dto.*;
import com.wjh.blog.entity.Blog;
import com.wjh.blog.entity.Tag;
import com.wjh.blog.mapper.BlogMapper;
import com.wjh.blog.service.BlogService;
import com.wjh.blog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public Blog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public UpdateBlog getUpdateBlogById(Long id) {
        return blogMapper.getUpdateBlogById(id);
    }

    @Transactional
    @Override
    public List<Blog> listBlog() {
        return blogMapper.listBlog();
    }

    @Transactional
    @Override
    public List<BlogQuery> getAllBlog() {
        List<BlogQuery> allBlogQuery = blogMapper.getAllBlogQuery();
        return allBlogQuery;
    }

    @Transactional
    @Override
    public void transformRecommend(SearchBlog searchBlog) {
        if (!"".equals(searchBlog.getRecommend()) && null != searchBlog.getRecommend()) {
            searchBlog.setRecommend2(1);
        }
    }

    @Transactional
    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogMapper.searchByTitleOrTypeOrRecommend(searchBlog);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        int tmp = blogMapper.saveBlog(blog);
//        Blog tmpBlog = blogMapper.getByTypeId();
        //将标签的数据存到t_blog_tags表中   相当于建立一个中间表来映射blog和tag之间的关系
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(tag.getId(),blog.getId());
            blogMapper.saveBlogAndTag(blogAndTag);
        }
        return tmp;
    }

    @Transactional
    @Override
    public int updateBlog(UpdateBlog updateBlog) {
        updateBlog.setUpdateTime(new Date());
        return blogMapper.updateBlog(updateBlog);
    }

    @Transactional
    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        return blogMapper.getAllFirstPageBlog();
    }

    @Transactional
    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        List<RecommendBlog> allRecommendBlog = blogMapper.getRecommendedBlog();
        List<RecommendBlog> allRecommendedBlog = new ArrayList<>();
        int i = 1;
        for (RecommendBlog recommendBlog : allRecommendBlog) {
            if (recommendBlog.isRecommend() == true && i <= 3) {
                allRecommendedBlog.add(recommendBlog);
                i++;
            }
        }
        return allRecommendedBlog;
    }

    @Transactional
    @Override
    public List<FirstPageBlog> getByTypeId(Long id) {
        return blogMapper.getByTypeId(id);
    }


    @Transactional
    @Override
    public List<FirstPageBlog> getSearchFirstPageBlog(String query) {
        return blogMapper.getSearchFirstPageBlog(query);
    }

    @Transactional
    @Override
    public List<FirstPageBlog> getByTagId(Long tagId) {
        return blogMapper.getByTagId(tagId);
    }

//    @Override
//    public DetailedBlog getDetailedBlog(Long id) {
//        return blogMapper.getDetailedBlog(id);
//    }


    @Transactional
    @Override
    public int deleteBlog(Long id) {
        blogMapper.deleteBlogAndTag(id);
        blogMapper.deleteBlog(id);
        return 1;
    }

    @Transactional
    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        Blog tmpBlog = blogMapper.getBlogById(id);
        if (tmpBlog != null) {
            int pre = tmpBlog.getViews();
            int cur = pre + 1;
            tmpBlog.setViews(cur);
            blogMapper.updateBlogViews(tmpBlog);
        }
        DetailedBlog detailedBlog = blogMapper.getDetailedBlog(id);
        if (detailedBlog == null) {
//            System.out.println("1111111111111");
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return detailedBlog;
    }

    @Override
    public Object countBlog() {
        List<Blog> blogList= blogMapper.countBlog();
        return blogList.size();
    }
}
