package com.alham.ggudok.controller.member;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/register")
    private String registerMember(MemberRegisterDto registerDto) {
        boolean isSuccess = memberService.registerMember(registerDto);
        if (isSuccess) {
            return "성공";
        } else {
            return "실패";
        }

    }
}
