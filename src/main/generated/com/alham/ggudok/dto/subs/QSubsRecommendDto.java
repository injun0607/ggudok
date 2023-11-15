package com.alham.ggudok.dto.subs;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.alham.ggudok.dto.subs.QSubsRecommendDto is a Querydsl Projection type for SubsRecommendDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSubsRecommendDto extends ConstructorExpression<SubsRecommendDto> {

    private static final long serialVersionUID = -644741583L;

    public QSubsRecommendDto(com.querydsl.core.types.Expression<Long> subsId, com.querydsl.core.types.Expression<Integer> score) {
        super(SubsRecommendDto.class, new Class<?>[]{long.class, int.class}, subsId, score);
    }

}

