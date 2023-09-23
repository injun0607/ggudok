package com.alham.ggudok.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

import java.io.IOException;


public class CustomAuthenticationFailureHandler extends ForwardAuthenticationFailureHandler {

    private final String forwardUrl = "/login_fail";


    public CustomAuthenticationFailureHandler(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
    }
}
