package com.alham.ggudok.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class ImageResourceEntity extends BaseTimeEntity{

    private String icon;
    private String image;

    private String imageName;
    private String iconName;

    public void updateImage(String image) {
        this.image = image;
    }

    public void updateImageName(String imageName) {
        this.imageName = imageName;
    }

    public void updateIcon(String icon) {
        this.icon = icon;
    }

    public void updateIconName(String iconName) {
        this.iconName = iconName;
    }

}
