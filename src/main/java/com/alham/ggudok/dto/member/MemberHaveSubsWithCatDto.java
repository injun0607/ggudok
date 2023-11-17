package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 멤버가 가지고 있는 subs와 category 정보 DTO
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "categoryName")
public class MemberHaveSubsWithCatDto {

    private String categoryName;

    private String categoryEng;

    private int totalPrice;

    List<MemberHaveSubsDetail> subsList;
}
