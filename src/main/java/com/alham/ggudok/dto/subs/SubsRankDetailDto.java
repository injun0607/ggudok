package com.alham.ggudok.dto.subs;

import com.alham.ggudok.entity.subs.RankLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SubsRankDetailDto {

    private String rankName;

    private int price;

    private RankLevel rankLevel;

    private List<String> content;
}
