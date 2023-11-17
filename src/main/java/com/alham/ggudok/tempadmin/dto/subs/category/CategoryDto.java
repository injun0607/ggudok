package com.alham.ggudok.tempadmin.dto.subs.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String categoryEng;
    private String categoryImage;

}
