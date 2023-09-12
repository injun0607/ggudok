package com.alham.ggudok.controller;


import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private MemberService memberService;

    @GetMapping("/login")
    public String memberLogin(MemberLoginDto loginDto) {
        boolean isLogin = memberService.memberLoginCheck(loginDto);
        if (isLogin) {
            return "성공";
        } else{
            return "실패";
        }
    }

}
