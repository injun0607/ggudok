package com.alham.ggudok.dto.subs;


import com.alham.ggudok.dto.TagDto;
import com.alham.ggudok.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * subs객체 변환 Dto
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SubsDto {

    private Long id;

    private String name;

    private String icon;

    private String image;

    private String category;

    private String info;

    private int ratingAvg = 3;

    List<SubsRankDto> ranks = new ArrayList<>();

    private List<TagDto> tags = new ArrayList<>();


    public void setTags(List<Tag> tagList) {
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDto tagDto = new TagDto();
            tagDto.setTagId(tag.getTagId());
            tagDto.setTagName(tag.getTagName());

            tagDtoList.add(tagDto);
        }

        this.tags = tagDtoList;

    }



}
