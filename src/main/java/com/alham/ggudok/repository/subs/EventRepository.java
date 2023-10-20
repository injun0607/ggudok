package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.subs.EventSubs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<EventSubs,Long > {

    @Query("select es from EventSubs es left join fetch es.subs s left join fetch s.category c")
    List<EventSubs> findAllWithSubs();

    @Query("select es from EventSubs es left join fetch es.subs s left join fetch s.category c where es.eventId =:eventId")
    EventSubs findByIdWithSubs(@Param("eventId") Long eventId);

    @Query("select es from EventSubs es left join fetch es.subs s left join fetch s.category c where es.isValid = true")
    List<EventSubs> findAllWithSubsValid();
}
