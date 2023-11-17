package com.alham.ggudok.tempadmin.dto.subs.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRegisterDto {
    private Long categoryId;
    private String categoryName;
    private String categoryEng;
    private String categoryImage;

}
