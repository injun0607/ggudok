package com.alham.ggudok.controller.subs.member;

import com.alham.ggudok.entity.member.Gender;
import lombok.Data;

@Data
public class MemberRegisterDto {

    private String memberName;
    //emailId
    private String loginId;
    private String password;
    private String passwordCheck;
    private Gender gender;

    private int age;
    private String phoneNumber;


}
