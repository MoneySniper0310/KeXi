package com.wjh.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjh.blog.entity.Tag;
import com.wjh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    //列表页
    @GetMapping("/tags")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Tag> allTag = tagService.listTag();
        //得到分页结果对象
        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }


    @GetMapping("/tags/input")
    public String input() {

        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String saveTag(Tag tag, RedirectAttributes attributes) {
        Tag tmpTag = tagService.getTagByName(tag.getName());
        if (tmpTag != null) {
            attributes.addFlashAttribute("message","不能添加重复的类");
            return "redirect:/admin/tags/input";
        }else {
            int t = tagService.saveTag(tag);
            return "redirect:/admin/tags";
        }

    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-update";
    }

    @PostMapping("/tags/update")
    public String editPost(Tag tag) {
        tagService.updateTag(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }

}
