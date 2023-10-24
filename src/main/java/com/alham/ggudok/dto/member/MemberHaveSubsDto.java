package com.alham.ggudok.dto.member;

import com.alham.ggudok.entity.subs.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MemberHaveSubsDto {

    //총요금
    private int totalAvg;

    private List<MemberHaveSubsWithCatDto> items;




}
