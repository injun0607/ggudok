package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberHaveSubsDetail {
    private String subsName;

    private List<String> content;

    private int price;

}
