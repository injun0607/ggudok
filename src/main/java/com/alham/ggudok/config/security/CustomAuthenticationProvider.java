package com.alham.ggudok.config.security;

import com.alham.ggudok.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomLoadUserByUsername customLoadUserByUsername;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberDto memberDto = (MemberDto) customLoadUserByUsername.loadUserByUsername(authentication.getName());

        String reqPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(reqPassword, memberDto.getPassword())) {
            throw new BadCredentialsException("Not Found User");
        }

        return new UsernamePasswordAuthenticationToken(memberDto,null,memberDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
