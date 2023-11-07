package com.alham.ggudok.dto;

import com.alham.ggudok.config.security.MemberDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    String sessionId;

    MemberDto memberDto;
}
