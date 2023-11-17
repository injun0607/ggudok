package com.alham.ggudok.dto.subs;

import com.alham.ggudok.entity.subs.RankLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubsBuyDto {

    private Long subsId;
    private RankLevel rankLevel;

}
