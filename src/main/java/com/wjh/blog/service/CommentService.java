package com.wjh.blog.service;

import com.wjh.blog.entity.Comment;

import java.util.List;

public interface CommentService {

//    List<Comment> getCommentsByBlogId(Long blogId);

    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);
}
