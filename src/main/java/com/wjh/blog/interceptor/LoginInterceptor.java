package com.wjh.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override     //预处理拦截
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");     //重定向到/admin下
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
