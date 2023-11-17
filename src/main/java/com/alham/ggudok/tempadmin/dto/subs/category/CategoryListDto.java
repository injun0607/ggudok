package com.alham.ggudok.tempadmin.dto.subs.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryListDto {
     List<CategoryDto> categoryList;
}
