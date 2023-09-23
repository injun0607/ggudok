package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * member와 subs 정보가 연관이 있을때 사용하는 dto
 */
@Data
@NoArgsConstructor
public class MemberSubsDto {

    private boolean subsLike;
    private ReviewDto review;
}
