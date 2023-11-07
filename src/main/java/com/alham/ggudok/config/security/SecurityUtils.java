package com.alham.ggudok.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class SecurityUtils {

    public static MemberDto transPrincipal(Principal principal) {
        if (principal != null) {
            return (MemberDto) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        }else{
            return null;
        }
    }

}
