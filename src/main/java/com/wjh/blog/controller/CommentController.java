package com.wjh.blog.controller;

import com.wjh.blog.entity.Comment;
import com.wjh.blog.entity.User;
import com.wjh.blog.entity.Visitor;
import com.wjh.blog.service.BlogService;
import com.wjh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")    //SpringBoot取值方法
    private String avatar;

    //    查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlogId();
        //set Blog
        comment.setBlog(blogService.getDetailedBlog(blogId));
        User user = (User) session.getAttribute("user");
        Visitor visitor = (Visitor) session.getAttribute("visitor");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(2);
        }

        if (visitor != null) {
            comment.setAvatar(visitor.getAvatar());
            comment.setAdminComment(1);
        }

        if (user == null && visitor == null) {
            comment.setAvatar(avatar);
            comment.setAdminComment(0);
        }

        if (comment.getParentComment().getId() != null) {
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
