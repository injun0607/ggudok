package com.alham.ggudok.dto;

import com.alham.ggudok.dto.member.MemberDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    String sessionId;

    MemberDto memberDto;
}
