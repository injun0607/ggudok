package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 회원이 가지고있는 전체 subsList
 */
@Data
@NoArgsConstructor
public class MemberHaveSubsDto {

    //총요금
    private int totalAvg;

    private List<MemberHaveSubsWithCatDto> items;




}
