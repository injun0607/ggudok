package com.alham.ggudok.controller;


import com.alham.ggudok.controller.session.SessionMemberDto;
import com.alham.ggudok.controller.subs.member.MemberLoginDto;
import com.alham.ggudok.dto.LoginDto;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alham.ggudok.controller.session.SessionConst.SESSION_MEMBER;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;


    @GetMapping("/")
    public String main() {
        //event페이지
        //알고리즘 맞춤 서비스
        //기본 추천 서비스(ex. 나이 남성)
        return "준비중";

    }

//    @PostMapping("/login")
//    public LoginDto memberLogin(MemberLoginDto memberLoginDto, HttpSession session) {
//        Member loginMember = memberService.memberLoginCheck(memberLoginDto);
//        LoginDto loginDto = new LoginDto();
//        if(loginMember != null) {
//
//            MemberDto memberDto = new MemberDto();
//            memberDto.setMemberName(loginMember.getMemberName());
//            memberDto.setLoginId(loginMember.getLoginId());
//
//            session.setAttribute(SESSION_MEMBER, memberDto);
//
//            loginDto.setSessionId(session.getId());
//            loginDto.setMemberDto(memberDto);
//
//            return loginDto;
//        } else {
//            return loginDto;
//        }
//    }

    @GetMapping("/logoutSuccess")
    public String memberLogout() {
        return "logout";
    }

    @GetMapping("/seohee")
    public String seohee() {

        return "서히야 안녕";
    }


    /**
     *
     * 세션체크 맵핑
     */
    @GetMapping("/getSession")
    public SessionMemberDto getSession(HttpSession session) {
        return (SessionMemberDto) session.getAttribute(SESSION_MEMBER);

    }

}
