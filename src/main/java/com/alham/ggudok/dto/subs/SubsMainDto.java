package com.alham.ggudok.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 메인 Dto
 */
@Data
@NoArgsConstructor
public class SubsMainDto {

    private List<SubsDto> items = new ArrayList<>();

}
