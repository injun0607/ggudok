package com.alham.ggudok.tempadmin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagForm {

    private Long tagId;
    private String tagName;

    public TagForm(String tagName) {
        this.tagName = tagName;
    }
}
