package com.wjh.blog.controller;


import com.wjh.blog.entity.Visitor;
import com.wjh.blog.service.VisitorService;
import com.wjh.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class VisitorLoginController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String visitorName,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model) {
        Visitor visitor = visitorService.findByVisitorNameAndPassword(visitorName,password);
        //System.out.println(user);    //  null
        if (visitor != null) {
            visitor.setPassword(null);

            model.addAttribute("visitor",visitor);

            session.setAttribute("visitor",visitor);            //密码置null后user放入session，传到页面
            return "successLogin";
        }else {
            attributes.addFlashAttribute("visitorMessage", "用户名或密码错误");
            return "redirect:/login";    //redirect方式相当于“response.sendRedirect()”，功能完全一样。如果要把参数传到下一个请求可是使用路径传参。
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.removeAttribute("visitor");

        return "redirect:/login";
    }

    //注册
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String visitorName,
                        @RequestParam String password,
                           @RequestParam String nickname,
                           @RequestParam String avatar,
                           @RequestParam String email,
                        RedirectAttributes attributes) {
        Visitor visitor = visitorService.findByVisitorName(visitorName);
        //System.out.println(user);    //  null
        if (visitor != null) {
            attributes.addFlashAttribute("visitorRegisterMessage", "用户已存在");

            return "redirect:/register";
        }else {
            Visitor newVisitor = new Visitor();
            newVisitor.setNickname(nickname);
            newVisitor.setAvatar(avatar);
//            newVisitor.setCreatTime(new Date());
            newVisitor.setEmail(email);
            newVisitor.setPassword(MD5Utils.code(password));
            newVisitor.setVisitorName(visitorName);
            visitorService.addVisitor(newVisitor);
//            attributes.addFlashAttribute("visitorRegisterMessage", "用户已存在");
            return "login";    //redirect方式相当于“response.sendRedirect()”，功能完全一样。如果要把参数传到下一个请求可是使用路径传参。
        }
    }

//    @PostMapping("/VisitorLogin")
//    public BaseResponse VisitorLogin(@RequestBody Visitor visitor, HttpServletResponse response){
//        Visitor LogVisitor =visitorService.findByVisitorNameAndPassword(visitor.getVisitorName(),visitor.getPassword());
//        if(LogVisitor == null){
//            return new BaseResponse(StatusCode.UserNotfound);
//        }
//        else {
//            if (!LogVisitor.getPassword().equals(visitor.getPassword())){
//                return new BaseResponse(StatusCode.PwError);
//            }else {
//                String token = tokenService.getToken(LogVisitor);
//                String username = LogVisitor.getNickname();
//                Long userid = LogVisitor.getId();
//                String email = LogVisitor.getEmail();
//                       /* Cookie coo  = new Cookie("token", token);
//                        coo.setMaxAge(60*60*24*7);
//                        response.addCookie(coo);*/
//                System.out.println(LogVisitor.toString());
//                System.out.println("登录成功");
//                response.addHeader("token", token);
//                BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
//                Visitor visitorInfo = new Visitor();
//                visitorInfo.setNickname(username);
//                visitorInfo.setEmail(email);
//                visitorInfo.setId(userid);
//                baseResponse.setData(visitorInfo);
//                return baseResponse;
//            }
//        }
//    }

}
