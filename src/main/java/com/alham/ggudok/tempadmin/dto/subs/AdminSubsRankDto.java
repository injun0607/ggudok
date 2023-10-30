package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.entity.subs.RankLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminSubsRankDto {

    private Long rankId;

    @NotNull
    private String rankName;
    @NotNull
    private int price;
    @NotNull
    private RankLevel rankLevel;

    private List<SubsContentForm> contentList;

    public AdminSubsRankDto(String rankName, int price, RankLevel rankLevel) {
        this.rankName = rankName;
        this.price = price;
        this.rankLevel = rankLevel;
    }


}
