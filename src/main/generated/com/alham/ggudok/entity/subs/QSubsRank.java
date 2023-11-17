package com.alham.ggudok.entity.subs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubsRank is a Querydsl query type for SubsRank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubsRank extends EntityPathBase<SubsRank> {

    private static final long serialVersionUID = -1712606944L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubsRank subsRank = new QSubsRank("subsRank");

    public final ListPath<SubsContent, QSubsContent> contents = this.<SubsContent, QSubsContent>createList("contents", SubsContent.class, QSubsContent.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> rankId = createNumber("rankId", Long.class);

    public final EnumPath<RankLevel> rankLevel = createEnum("rankLevel", RankLevel.class);

    public final StringPath rankName = createString("rankName");

    public final QSubs subs;

    public QSubsRank(String variable) {
        this(SubsRank.class, forVariable(variable), INITS);
    }

    public QSubsRank(Path<? extends SubsRank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubsRank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubsRank(PathMetadata metadata, PathInits inits) {
        this(SubsRank.class, metadata, inits);
    }

    public QSubsRank(Class<? extends SubsRank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subs = inits.isInitialized("subs") ? new QSubs(forProperty("subs"), inits.get("subs")) : null;
    }

}

