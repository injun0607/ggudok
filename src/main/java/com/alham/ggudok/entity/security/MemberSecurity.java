package com.alham.ggudok.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MemberSecurity {
    @Id
    @GeneratedValue
    private Long securityId;

    private String loginId;

    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateLoginId(String loginId) {
        this.loginId = loginId;
    }
}
