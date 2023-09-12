package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.entity.subs.RankLevel;
import lombok.Data;

@Data
public class AdminSubsRankDto {

    private String rankName;
    private int price;
    private RankLevel rankLevel;

    public AdminSubsRankDto(String rankName, int price, RankLevel rankLevel) {
        this.rankName = rankName;
        this.price = price;
        this.rankLevel = rankLevel;
    }
}
