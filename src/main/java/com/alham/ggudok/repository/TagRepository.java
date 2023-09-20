package com.alham.ggudok.repository;

import com.alham.ggudok.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.tagName like :age%")
    Tag findByTagNameLikeAge(@Param("age") String age);

    Tag findTagByTagName(String tagName);



}
