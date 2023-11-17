package com.alham.ggudok.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EventPageDto {
    private List<EventSubsDto> eventSubs;
}

