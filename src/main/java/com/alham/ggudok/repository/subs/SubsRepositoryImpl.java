package com.alham.ggudok.repository.subs;


import com.alham.ggudok.entity.subs.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alham.ggudok.entity.subs.QSubs.*;
import static com.alham.ggudok.entity.subs.QSubsContent.*;
import static com.alham.ggudok.entity.subs.QSubsRank.*;

public class SubsRepositoryImpl implements SubsRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public SubsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SubsRank> findSubsByIdWithAllContent(Long subsId) {
        return queryFactory.selectFrom(subsRank)
                .join(subsRank.contents, subsContent).fetchJoin()
                .where(subsRank.subs.subsId.eq(subsId))
                .fetch();
    }

}
