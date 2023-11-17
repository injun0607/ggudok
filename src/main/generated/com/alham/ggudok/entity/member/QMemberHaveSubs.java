package com.alham.ggudok.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberHaveSubs is a Querydsl query type for MemberHaveSubs
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberHaveSubs extends EntityPathBase<MemberHaveSubs> {

    private static final long serialVersionUID = 1873325839L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberHaveSubs memberHaveSubs = new QMemberHaveSubs("memberHaveSubs");

    public final QMember member;

    public final NumberPath<Long> mhsId = createNumber("mhsId", Long.class);

    public final EnumPath<com.alham.ggudok.entity.subs.RankLevel> rankLevel = createEnum("rankLevel", com.alham.ggudok.entity.subs.RankLevel.class);

    public final com.alham.ggudok.entity.subs.QSubs subs;

    public QMemberHaveSubs(String variable) {
        this(MemberHaveSubs.class, forVariable(variable), INITS);
    }

    public QMemberHaveSubs(Path<? extends MemberHaveSubs> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberHaveSubs(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberHaveSubs(PathMetadata metadata, PathInits inits) {
        this(MemberHaveSubs.class, metadata, inits);
    }

    public QMemberHaveSubs(Class<? extends MemberHaveSubs> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.subs = inits.isInitialized("subs") ? new com.alham.ggudok.entity.subs.QSubs(forProperty("subs"), inits.get("subs")) : null;
    }

}

