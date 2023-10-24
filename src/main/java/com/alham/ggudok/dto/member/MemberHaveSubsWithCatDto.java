package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "categoryName")
public class MemberHaveSubsWithCatDto {

    private String categoryName;

    private String categoryEng;

    private int totalPrice;

    List<MemberHaveSubsDetail> subsList;
}
