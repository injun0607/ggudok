package com.alham.ggudok.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventSubsDto {

    private Long subsId;

    private String subsName;

    private String infoTag;

    private String info;

    private String categoryName;

    private String startDate;

    private String endDate;

}
