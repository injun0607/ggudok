package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 회원이 가지고 있는 subs의 상세 정보
 */
@Data
@NoArgsConstructor
public class MemberHaveSubsDetail {

    private Long subsId;

    private String subsName;

    private List<String> content;

    private int price;

}
