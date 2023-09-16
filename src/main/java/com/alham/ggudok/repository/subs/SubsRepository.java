package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.subs.Subs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubsRepository extends JpaRepository<Subs,Long> {

    @Query("select s from Subs s join fetch s.subsRelTags st join fetch st.tag t")
    Optional<Subs> findSubsByIdWithTag(Long subsId);


}
