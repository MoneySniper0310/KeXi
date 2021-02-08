package com.wjh.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjh.blog.dto.DetailedBlog;
import com.wjh.blog.dto.FirstPageBlog;
import com.wjh.blog.dto.RecommendBlog;
import com.wjh.blog.entity.Tag;
import com.wjh.blog.entity.Type;
import com.wjh.blog.service.BlogService;
import com.wjh.blog.service.TagService;
import com.wjh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class indexController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<FirstPageBlog> allFirstPageBlog = blogService.getAllFirstPageBlog();
        /*System.out.println("num:"+allFirstPageBlog.size());
        for (FirstPageBlog firstPageBlog : allFirstPageBlog) {
            System.out.println(firstPageBlog);
        }*/
        List<Type> allType = typeService.getAllType();
        /*System.out.println("num:" + allType.size());
        for (Type type : allType) {
            System.out.println(type);
        }*/
        List<Tag> allTag = tagService.getAllTag();
        List<RecommendBlog> recommendedBlog = blogService.getRecommendedBlog();
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendedBlogs", recommendedBlog);
        return "index";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query) {
        PageHelper.startPage(pageNum, 1000);
        List<FirstPageBlog> firstPageBlogs =blogService.getSearchFirstPageBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(firstPageBlogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
//        List<Comment> comments = commentService.listCommentByBlogId(id);
//        model.addAttribute("comments", comments);
        model.addAttribute("blog", detailedBlog);
        return "blog";
    }
}
