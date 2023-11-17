package com.alham.ggudok.dto.member;

import com.alham.ggudok.entity.member.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OAUTH2로그인시 필수 부분 업데이트 DTO
 */
@Data
@NoArgsConstructor
public class MemberOauthDto {

    private String memberName;
    private Gender gender;
    private int age;

}
