package com.alham.ggudok.tempadmin.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDetailFormDto {

    private Long subsId;

    private String subsName;

    private String categoryName;

    private String infoTag = "";

    private String info = "";

    private String eventImage = "";

    private String startDate;

    private String endDate;

    private boolean isValid;


}
