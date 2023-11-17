package com.alham.ggudok.dto.subs;

import com.alham.ggudok.dto.member.MemberSubsDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SubsMainDetailDto {

    private MemberSubsDto memberInfo;
    private SubsDetailDto itemDetail;
    private List<SubsDto> similarItems = new ArrayList<>();

}
