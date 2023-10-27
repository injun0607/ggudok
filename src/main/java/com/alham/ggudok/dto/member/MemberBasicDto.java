package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지 접속시 기본으로 보여주는 DTO
 */
@Data
@NoArgsConstructor
public class MemberBasicDto {

    private Long memberId;
    private String memberName;
    private String profileImage;
}
