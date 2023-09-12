package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.tempadmin.dto.subs.CategoryRegisterDto;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class TempAdminController {

    private final AdminSubsService adminSubsService;


    @PostMapping("/register/category")
    public String registerCategory(CategoryRegisterDto categoryRegisterDto) {
        adminSubsService.createCategory(categoryRegisterDto.getCategoryName());

        return "redirect:/admin/subs/register";
    }

    @GetMapping("/register/category")
    public String registerCategory(Model model) {
        model.addAttribute("form", new CategoryRegisterDto());
        return "admin/subs/categoryForm";
    }

    @GetMapping("subs/register")
    public String showSubs(Model model) {
        List<Subs> subsList = adminSubsService.findSubs();
        model.addAttribute("subsList", subsList);
        return "admin/subs/subsList";
    }


}
