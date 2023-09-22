package com.alham.ggudok.config.security;

import com.alham.ggudok.controller.session.SessionConst;
import com.alham.ggudok.controller.session.SessionMemberDto;
import com.alham.ggudok.dto.member.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();
        session.setAttribute(SessionConst.SESSION_MEMBER,new SessionMemberDto(memberDto.getMemberName(), memberDto.getLoginId()));
    }
}
