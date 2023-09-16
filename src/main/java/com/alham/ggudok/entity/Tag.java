package com.alham.ggudok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long tagId;

    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
