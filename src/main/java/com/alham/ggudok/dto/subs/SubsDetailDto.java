package com.alham.ggudok.dto.subs;

import com.alham.ggudok.dto.member.ReviewDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SubsDetailDto {

    private Long id;

    private String name;

    private String info;

    private String icon;

    private String image;

    private String category;

    private int ratingAvg;

    List<SubsRankDetailDto> ranks = new ArrayList<>();

    private List<Tag> tags = new ArrayList<>();

    private List<ReviewDto> reviews = new ArrayList<>();

}
