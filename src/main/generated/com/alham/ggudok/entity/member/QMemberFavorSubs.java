package com.alham.ggudok.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberFavorSubs is a Querydsl query type for MemberFavorSubs
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberFavorSubs extends EntityPathBase<MemberFavorSubs> {

    private static final long serialVersionUID = 1872393405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberFavorSubs memberFavorSubs = new QMemberFavorSubs("memberFavorSubs");

    public final QMember member;

    public final NumberPath<Long> mfsId = createNumber("mfsId", Long.class);

    public final com.alham.ggudok.entity.subs.QSubs subs;

    public QMemberFavorSubs(String variable) {
        this(MemberFavorSubs.class, forVariable(variable), INITS);
    }

    public QMemberFavorSubs(Path<? extends MemberFavorSubs> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberFavorSubs(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberFavorSubs(PathMetadata metadata, PathInits inits) {
        this(MemberFavorSubs.class, metadata, inits);
    }

    public QMemberFavorSubs(Class<? extends MemberFavorSubs> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.subs = inits.isInitialized("subs") ? new com.alham.ggudok.entity.subs.QSubs(forProperty("subs"), inits.get("subs")) : null;
    }

}

