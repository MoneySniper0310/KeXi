package com.wjh.blog.controller;

import com.wjh.blog.entity.Message;
import com.wjh.blog.entity.User;
import com.wjh.blog.entity.Visitor;
import com.wjh.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: 留言页面控制器
 * @Date: Created in 10:57 2020/4/16
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${message.avatar}")
    private String avatar;

    @GetMapping("/message")
    public String message() {
        return "message";
    }

//    查询留言
    @GetMapping("/messagecomment")
    public String messages(Model model) {
        List<Message> messages = messageService.listMessage();
        model.addAttribute("messages", messages);
        return "message::messageList";
    }

//    新增留言
    @PostMapping("/message")
    public String post(Message message, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Visitor visitor = (Visitor) session.getAttribute("visitor");
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(2);
        }

        if (visitor != null) {
            message.setAvatar(visitor.getAvatar());
            message.setAdminMessage(1);
        }

        if (user == null && visitor == null) {
            message.setAvatar(avatar);
            message.setAdminMessage(0);
        }
        //设置头像
//        if (visitor != null) {
//            message.setAvatar(visitor.getAvatar());
//            message.setAdminMessage(1);
//        } else {
//            message.setAvatar(avatar);
//        }
        if (message.getParentMessage().getId() != null) {
            message.setParentMessageId(message.getParentMessage().getId());
        }
        messageService.saveMessage(message);
        List<Message> messages = messageService.listMessage();
        model.addAttribute("messages", messages);
        return "message::messageList";
    }

//    删除留言
    @GetMapping("/messages/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes, Model model){
        messageService.deleteMessage(id);
        return "redirect:/message";
    }

}