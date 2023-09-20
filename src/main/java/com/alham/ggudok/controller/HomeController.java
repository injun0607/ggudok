package com.alham.ggudok.controller;


import com.alham.ggudok.controller.subs.member.MemberLoginDto;
import com.alham.ggudok.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private MemberService memberService;

    @GetMapping("/")
    public String main() {
        //event페이지
        //알고리즘 맞춤 서비스
        //기본 추천 서비스(ex. 나이 남성)
        return "준비중";

    }

    @GetMapping("/login")
    public String memberLogin(MemberLoginDto loginDto) {
        boolean isLogin = memberService.memberLoginCheck(loginDto);
        if (isLogin) {
            return "성공";
        } else {
            return "실패";
        }
    }


    @GetMapping("/seohee")
    public String seohee() {
        return "서히야 안녕";
    }



}
