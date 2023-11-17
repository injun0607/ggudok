package com.alham.ggudok.tempadmin.dto.subs;

import com.alham.ggudok.tempadmin.dto.subs.category.CategoryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EventRegisterShowForm {

    private List<CategoryDto> categoryList = new ArrayList<>();

    private List<AdminSubsDto> subsList = new ArrayList<>();


}
