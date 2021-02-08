package com.wjh.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjh.blog.dto.BlogQuery;
import com.wjh.blog.dto.SearchBlog;
import com.wjh.blog.dto.UpdateBlog;
import com.wjh.blog.entity.Blog;
import com.wjh.blog.entity.Tag;
import com.wjh.blog.entity.Type;
import com.wjh.blog.entity.User;
import com.wjh.blog.service.BlogService;
import com.wjh.blog.service.TagService;
import com.wjh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")      //Controller负责处理的，根目录下的URL ，/admin/** 下的所有路径都会被Controller所拦截
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/blogs")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<BlogQuery> allBlog = blogService.getAllBlog();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        return "admin/blogs";
    }

    //删除
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog, Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //将recommend转换一下  String转为Integer
        blogService.transformRecommend(searchBlog);
        //动态sql可以解决
        List<BlogQuery> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageHelper.startPage(pageNum, 5);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogBySearch);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        model.addAttribute("message", "查询成功");
        return "admin/blogs";        //:: blogList设置片段，前端对应此处的值进行重新渲染，其他不变
    }

    //去新增页面
    @GetMapping("/blogs/input")
    public String toAdd(Model model) {
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    //新增
    @PostMapping("/blogs")
    public String add(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));       //当前登录的用户对象
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        blogService.saveBlog(blog);
        attributes.addFlashAttribute("message", "新增成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/input")
    public String toUpdate(@PathVariable Long id, Model model) {
        UpdateBlog updateBlog = blogService.getUpdateBlogById(id);
        List<Type> allType = typeService.listType();
        List<Tag> allTag = tagService.listTag();
        model.addAttribute("blog", updateBlog);
        model.addAttribute("types", allType);
        model.addAttribute("tags", allTag);
        return "admin/blogs-update";         //默认Template文件夹下
    }

    @PostMapping("/blogs/update")
    public String update(UpdateBlog updateBlog, RedirectAttributes attributes) {
        blogService.updateBlog(updateBlog);
        attributes.addFlashAttribute("message","修改成功");
        return "redirect:/admin/blogs";
    }
}
