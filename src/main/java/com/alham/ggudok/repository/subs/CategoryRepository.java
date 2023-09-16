package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.subs.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c left join fetch c.subsList where c.categoryEng = :categoryEng")
    public Optional<Category> findCateByEngWithSubs(@Param("categoryEng") String categoryEng);
}
