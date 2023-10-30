package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.dto.TagDto;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SubsRegisterViewForm {

    private List<CategoryDto> categoryList;
    private List<TagDto> tagList;
    RankLevel[] rankLevels = RankLevel.values();

}
