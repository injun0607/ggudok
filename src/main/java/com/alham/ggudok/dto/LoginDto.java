package com.alham.ggudok.dto;

import com.alham.ggudok.config.security.MemberDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인시 반환 DTO
 */
@Data
@NoArgsConstructor
public class LoginDto {

    String sessionId;

    MemberDto memberDto;
}
