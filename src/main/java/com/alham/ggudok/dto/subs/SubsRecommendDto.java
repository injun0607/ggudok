package com.alham.ggudok.dto.subs;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SubsRecommendDto {
    private Long subsId;
    private Integer score;


    @QueryProjection
    public SubsRecommendDto(Long subsId, Integer score) {
        this.subsId = subsId;
        this.score = score;
    }
}
