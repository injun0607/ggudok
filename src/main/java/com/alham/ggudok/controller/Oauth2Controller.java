package com.alham.ggudok.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Controller
public class Oauth2Controller {

//    @GetMapping("/")
//    public String loginTest(Model model, HttpSession httpSession, Principal principal) {
//
//        String memberName = "";
////        Member member = (Member) httpSession.getAttribute("user");
////
////        if (member != null) {
////            memberName = member.getMemberName();
////        }
////        model.addAttribute("name", memberName);
//
//        return "index";
//
//    }

}
