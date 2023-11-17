package com.alham.ggudok.dto.member;

import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Role;
import lombok.Data;

@Data
public class MemberUpdateDto {

    private Gender gender;
    private int age;
    private String password = "";
    private String newPassword = "";
    private String newPasswordCheck = "";
    private String phoneNumber = "";
    private String memberImg = "";
    private Role role;

}
