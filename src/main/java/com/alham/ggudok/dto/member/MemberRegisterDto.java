package com.alham.ggudok.dto.member;

import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Role;
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

    private Role role = Role.GUEST;


}
