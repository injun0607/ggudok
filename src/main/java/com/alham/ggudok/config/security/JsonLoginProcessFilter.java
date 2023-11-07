package com.alham.ggudok.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Map;


public class JsonLoginProcessFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CONTENT_TYPE = "application/json";
    private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "loginId";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final String DEFAULT_FILTER_PROCESSES_URL = "/login";
    private final ObjectMapper objectMapper;


    public JsonLoginProcessFilter(ObjectMapper objectMapper) {
        super(DEFAULT_FILTER_PROCESSES_URL);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        Map<String, String> parameter = objectMapper.readValue(request.getInputStream(), Map.class);
        String loginId = parameter.get(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = parameter.get(SPRING_SECURITY_FORM_PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginId, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
