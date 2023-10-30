package com.alham.ggudok.tempadmin.dto.subs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminSubsListDto {

    private List<AdminSubsDto> subsList;

}
