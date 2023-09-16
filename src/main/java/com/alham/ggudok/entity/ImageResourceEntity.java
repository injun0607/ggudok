package com.alham.ggudok.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class ImageResourceEntity extends BaseTimeEntity{

    private String icon;
    private String image;


}
