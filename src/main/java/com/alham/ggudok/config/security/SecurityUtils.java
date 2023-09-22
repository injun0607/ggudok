package com.alham.ggudok.config.security;

import com.alham.ggudok.dto.member.MemberDto;

import java.security.Principal;

public class SecurityUtils {

    public static MemberDto transPrincipal(Principal principal) {
        if (principal != null) {
            return (MemberDto) principal;
        }else{
            return null;
        }
    }

}
