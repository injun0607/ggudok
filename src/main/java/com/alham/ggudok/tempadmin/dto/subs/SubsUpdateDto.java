package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.dto.TagDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
public class SubsUpdateDto {

    @NotNull
    private Long categoryId;
    @NotNull
    private String subsName;
    private String subsImage;
    private String subsInfo;
    private List<TagDto> tagList;
    private List<AdminSubsRankDto> subsRankList;


}
