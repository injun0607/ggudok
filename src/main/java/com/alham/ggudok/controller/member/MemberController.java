package com.alham.ggudok.controller.member;

import com.alham.ggudok.controller.subs.member.MemberRegisterDto;
import com.alham.ggudok.controller.subs.member.MemberUpdateDto;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/register")
    private boolean registerMember(@RequestBody MemberRegisterDto registerDto) {
        boolean isSuccess = memberService.registerMember(registerDto);
        if (isSuccess) {
            return true;
        } else {
            return false;
        }
    }

    /*
    이메일 인증하기
    이메일 인증 버튼누른후 -> 인증번호 입력
     */
    @PostMapping("/emailCheck")
    private Map<String, String> checkEmail(@RequestBody MemberRegisterDto registerDto) {
        String checkEmail = registerDto.getLoginId();
        String certCode = memberService.checkEmail(checkEmail);
        Map<String, String> map = new HashMap<>();
        map.put("certCode", certCode);
        return map;


    }

    @GetMapping("/memberInfo")
    private String viewMemberInfo(Principal principal) {
        MemberDto memberDto = (MemberDto) principal;
        String loginId = memberDto.getLoginId();
        memberService.viewMemberInfo(loginId);
        return null;

    }


    @PostMapping("/update")
    private String updateMember(MemberUpdateDto updateDto) {

        memberService.updateMember(updateDto);
        return null;
    }

//    @PostMapping("/add/reltag")
//    public void




}
