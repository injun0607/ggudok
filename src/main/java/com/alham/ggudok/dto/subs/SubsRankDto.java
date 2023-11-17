package com.alham.ggudok.dto.subs;

import com.alham.ggudok.entity.subs.RankLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Data
@NoArgsConstructor
public class SubsRankDto {

    private String rankName;

    private int price;

    private RankLevel rankLevel;
}
