package com.alham.ggudok.entity.subs;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long categoryId;

    private String categoryName;

    @OneToMany(mappedBy = "category",cascade = ALL)
    private List<Subs> subsList = new ArrayList<>();

}
