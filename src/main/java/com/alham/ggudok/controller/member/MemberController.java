package com.alham.ggudok.controller.member;

import com.alham.ggudok.controller.subs.member.MemberRegisterDto;
import com.alham.ggudok.controller.subs.member.MemberUpdateDto;
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

    /*
    이메일 인증하기
    이메일 인증 버튼누른후 -> 인증번호 입력
     */
    @PostMapping("/emailCheck")
    private boolean checkEmail(MemberRegisterDto registerDto) {
        String checkEmail = registerDto.getLoginId();
        if (!memberService.checkEmail(checkEmail).equals("fail")) {
            return true;
        } else{
            return false;
        }


    }


    @PostMapping("/update")
        private String updateMember(MemberUpdateDto updateDto) {
            memberService.updateMember(updateDto);
        return null;
    }

//    @PostMapping("/add/reltag")
//    public void




}
