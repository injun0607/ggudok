package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.dto.TagDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminSubsDto {

    private String CategoryName;
    private Long subsId;
    private String subsName;
    private String subsImage;
    private List<TagDto> tagList;

}
