package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.dto.TagDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminSubsDetailDto {

    private Long subsId;
    private Long categoryId;
    private String categoryName;
    private String subsName;
    private String subsImage;
    private List<TagDto> tagList;
    private List<AdminSubsRankDto> subsRankList;



}
