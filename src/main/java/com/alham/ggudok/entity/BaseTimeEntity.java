package com.alham.ggudok.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
