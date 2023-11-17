package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 멤버 로그인시 DTO
 */
@Data
@NoArgsConstructor
public class MemberLoginDto {

    private String loginId;
    private String password;

    public MemberLoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
