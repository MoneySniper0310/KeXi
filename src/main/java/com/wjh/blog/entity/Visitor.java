package com.wjh.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {

    private Long id;
    private String nickname;
    private String visitorName;
    private String password;
    private String email;
    private String avatar;

    private Date creatTime;

    private Date updateTime;
}
