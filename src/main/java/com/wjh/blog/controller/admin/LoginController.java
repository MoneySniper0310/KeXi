package com.wjh.blog.controller.admin;


import com.wjh.blog.entity.User;
import com.wjh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("toAdminIndex")
    public String toAdminIndex() {
        return "admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.findByUsernameAndPassword(username,password);
        //System.out.println(user);    //  null
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);            //密码置null后user放入session，传到页面
//            System.out.println(user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";    //redirect方式相当于“response.sendRedirect()”，功能完全一样。如果要把参数传到下一个请求可是使用路径传参。
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
