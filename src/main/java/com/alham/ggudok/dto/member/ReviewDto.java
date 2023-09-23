package com.alham.ggudok.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {

    private String contents;
    private Long subsId;
    private String memberName;
    private String subsName;
    private int rating;



}
