package com.wjh.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBlog {
    private String title;
    private Long typeId;
    //推荐符号从前端传过来是String类型  后面可以转为Integer类型recommend2
    private String recommend;
    private Integer recommend2;
}