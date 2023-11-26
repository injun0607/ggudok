package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.subs.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c left join fetch c.subsList where c.categoryEng = :categoryEng")
    Optional<Category> findCateByEngWithSubs(@Param("categoryEng") String categoryEng);

    @Query("SELECT c FROM Category c LEFT JOIN c.subsList sub GROUP BY c ORDER BY COUNT(sub) DESC")
    List<Category> findAllCategoryWithSubsCnt();



}
