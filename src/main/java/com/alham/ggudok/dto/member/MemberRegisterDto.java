package com.alham.ggudok.dto.member;

import com.alham.ggudok.entity.member.Gender;
import lombok.Data;

@Data
public class MemberRegisterDto {

    private String memberName;
    private int age;
    private String loginId;
    private String password;
    private Gender gender;
    private String phoneNumber;


}
