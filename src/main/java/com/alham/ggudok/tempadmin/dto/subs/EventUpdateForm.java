package com.alham.ggudok.tempadmin.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class EventUpdateForm {

    @NotNull
    private Long eventId;

    private String infoTag = "";

    private String info = "";

    private String eventImage = "";

    private String startDate;

    private String endDate;

    private boolean valid;

}
