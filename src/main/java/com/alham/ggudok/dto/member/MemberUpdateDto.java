package com.alham.ggudok.dto.member;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private Long memberId;
    private String password;
    private String phoneNumber;

}
