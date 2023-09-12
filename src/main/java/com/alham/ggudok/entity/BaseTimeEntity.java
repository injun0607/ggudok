package com.alham.ggudok.entity;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseTimeEntity {
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
