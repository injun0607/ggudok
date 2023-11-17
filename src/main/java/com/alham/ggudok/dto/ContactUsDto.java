package com.alham.ggudok.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactUsDto {

    private String submitName;
    private String submitEmail;
    private String title;
    private String contents;

}
