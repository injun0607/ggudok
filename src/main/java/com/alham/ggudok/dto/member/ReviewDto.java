package com.alham.ggudok.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String contents;
    private String memberName;
    private Long subsId;
    private String subsName;
    private int rating;



}
