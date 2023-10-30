package com.alham.ggudok.config.security;

import com.alham.ggudok.config.security.auth.CustomOAuth2MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private final CustomOAuth2MemberService customOAuth2MemberService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .anyRequest().permitAll()
                ).formLogin(login ->
                        login.loginProcessingUrl("/login")
                                .usernameParameter("loginId")
                                .passwordParameter("password")
                                .successHandler(customAuthenticationSuccessHandler)
                                .failureHandler(customAuthenticationFailureHandler)


                )
                .logout((logout) ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(((request, response, authentication) -> {
                                    HttpSession session = request.getSession();
                                    if (session != null) {
                                        session.invalidate();
                                    }
                                }
                                ))
                                .logoutSuccessUrl("http://192.168.45.96:8080/logoutSuccess")

                );
        http.oauth2Login().userInfoEndpoint().userService(customOAuth2MemberService);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();


        UserDetails admin = User.builder()
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder PasswordEncoder () {
        return new BCryptPasswordEncoder();
    }





}
