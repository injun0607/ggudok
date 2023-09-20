package com.alham.ggudok.controller.subs.member;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private Long memberId;
    private String password;
    private String phoneNumber;

}
