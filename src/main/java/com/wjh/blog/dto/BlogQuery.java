package com.wjh.blog.dto;

import com.wjh.blog.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {
    private Long id;
    private String title;
    private Date updateTime;
    private Integer recommend;
    private Long typeId;

    private Type type;
}