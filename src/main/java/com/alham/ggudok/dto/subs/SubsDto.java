package com.alham.ggudok.dto.subs;


import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.SubsRank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * subs객체 변환 Dto
 */
@Data
@NoArgsConstructor
public class SubsDto {

    private Long subsId;

    private String subsName;

    List<SubsRank> subsRanks = new ArrayList<>();

    private String category;

    private List<Tag> tags = new ArrayList<>();

}
