package com.wjh.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjh.blog.entity.Type;
import com.wjh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //列表页
    @GetMapping("/types")
    public String list(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Type> allType = typeService.listType();
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }


    //去新增页面
    @GetMapping("/types/input")
    public String input() {

        return "admin/types-input";
    }

    @PostMapping("/types")
    public String saveType(Type type, RedirectAttributes attributes, BindingResult result) {
        Type tmpType = typeService.getTypeByName(type.getName());
        if (tmpType != null) {
//            result.rejectValue("name","不能重复添加分类");
            attributes.addFlashAttribute("message", "不能添加重复的类");
            return "redirect:admin/types/input";
        }else {
            typeService.saveType(type);
            return "redirect:/admin/types";
        }

    }

    //删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }


}