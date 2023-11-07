package com.alham.ggudok.config.security;

import com.alham.ggudok.config.security.auth.CustomOAuth2MemberService;
import com.alham.ggudok.config.security.jwt.JwtService;
import com.alham.ggudok.repository.security.MemberSecurityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceCustom loginService;
    private final JwtService jwtService;
    private final MemberSecurityRepository memberSecurityRepository;
    private final ObjectMapper objectMapper;

    private final CustomOAuth2MemberService customOAuth2MemberService;

    /*
    1. 네이버 로그인 연결
    2. 카카오 로그인 연결
    3. 로그인시 폼 로그인이 아닌 requestBody를 받는 방식으로 변경
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll();

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonLoginProcessFilter.class);

        http.oauth2Login().userInfoEndpoint().userService(customOAuth2MemberService);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     *
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        AuthenticationProviderCustom provider = new AuthenticationProviderCustom(loginService,passwordEncoder());
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, memberSecurityRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public CustomAuthenticationFailureHandler loginFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */
    @Bean
    public JsonLoginProcessFilter customJsonUsernamePasswordAuthenticationFilter() {
        JsonLoginProcessFilter jsonLoginProcessFilter
                = new JsonLoginProcessFilter(objectMapper);
        jsonLoginProcessFilter.setAuthenticationManager(authenticationManager());
        jsonLoginProcessFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        jsonLoginProcessFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonLoginProcessFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, memberSecurityRepository,loginService);
        return jwtAuthenticationFilter;
    }



}
